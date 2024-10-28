package org.example.backend.Qna.Service;

import com.example.common.Qna.model.Resolved;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.common.Category.Model.Entity.Category;
import com.example.common.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Exception.custom.InvalidUserException;
import com.example.common.Qna.Repository.*;
import com.example.common.Qna.model.Entity.*;
import org.example.backend.Qna.model.Res.*;
import org.example.backend.Qna.model.req.*;
import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QuestionRepository questionRepository;
    private final QnaRepositoryCustomImpl qnaRepositoryCustom;
    private final AnswerRepository answerRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final QnaLikeRepository qnaLikeRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final QnaScrapRepository qnaScrapRepository;
    private final AnswerCommentRepository answerCommentRepository;

    @Transactional
    public Long saveQuestion(CreateQuestionReq createQuestionReq, Long userId) {
        Category category = categoryRepository.findById(createQuestionReq.getCategoryId())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.CATEGORY_INVALID_CATEGORY_DATA));
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

            QnaBoard qnaBoard = QnaBoard.builder()
                    .user(user)
                    .title(createQuestionReq.getTitle())
                    .content(createQuestionReq.getContent())
                    .category(category)
                    .build();

            questionRepository.save(qnaBoard);
            return qnaBoard.getId();
    }

    public List<GetQnaListRes> getQnaList(GetQnaListReq getQnaListReq) {
        Pageable pageable;
        // 최신순 or 좋아요가 많은 순으로 정렬 type 지정, 둘 다 아니면 에러
        if (getQnaListReq.getSort().equals("latest")) {
            pageable = PageRequest.of(getQnaListReq.getPage(), getQnaListReq.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        } else if (getQnaListReq.getSort().equals("like")) {
            pageable = PageRequest.of(getQnaListReq.getPage(), getQnaListReq.getSize(), Sort.by(Sort.Direction.DESC, "likeCount"));
        } else {
            throw new InvalidQnaException(BaseResponseStatus.QNA_INVALID_SEARCH_TYPE);
        }
        // paging 처리 및 responseList 생성
        Page<QnaBoard> qnaBoardPage = qnaRepositoryCustom.getQnaList(getQnaListReq.getResolved(), pageable);

        return qnaBoardPage.getContent().stream().map(qnaBoard -> GetQnaListRes.builder()
                        .id(qnaBoard.getId())
                        .title(qnaBoard.getTitle())
                        .superCategoryName(qnaBoard.getCategory().getSuperCategory() != null ?
                                qnaBoard.getCategory().getSuperCategory().getCategoryName() : null)
                        .subCategoryName(qnaBoard.getCategory() != null ?
                                qnaBoard.getCategory().getCategoryName() : null)
                        .nickname(qnaBoard.getUser().getNickname())
                        .profileImage(qnaBoard.getUser().getProfileImg())
                        .grade(qnaBoard.getUser().getGrade())
                        .likeCnt(qnaBoard.getLikeCount())
                        .answerCnt(qnaBoard.getAnswerCount())
                        .createdAt(qnaBoard.getCreatedAt())
                        .totalPage(qnaBoardPage.getTotalPages())
                        .build())
                .collect(Collectors.toList());
    }

    public GetQuestionDetailRes getQuestionDetail(Integer qnaBoardId, Long userId) {
        User user;
        if (userId != null){
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.USER_NOT_FOUND));
        }
        else {
            user = null;
        }
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(qnaBoardId.longValue())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));

        return GetQuestionDetailRes.builder()
                .id(qnaBoard.getId())
                .userId(qnaBoard.getUser().getId())
                .superCategoryId(qnaBoard.getCategory().getSuperCategory() != null ?
                        qnaBoard.getCategory().getSuperCategory().getId() : null)
                .subCategoryId(qnaBoard.getCategory().getId())
                .title(qnaBoard.getTitle())
                .content(qnaBoard.getContent())
                .superCategoryName(qnaBoard.getCategory().getSuperCategory() != null ?
                        qnaBoard.getCategory().getSuperCategory().getCategoryName() : null)
                .subCategoryName(qnaBoard.getCategory() != null ?
                        qnaBoard.getCategory().getCategoryName() : null)
                .likeCnt(qnaBoard.getLikeCount())
                .hateCnt(qnaBoard.getHateCount())
                .answerCnt(qnaBoard.getAnswerCount())
                .checkLikeOrHate(isQuestionLikeORHate(qnaBoard, user))
                .checkScrap(isQuestionScarp(qnaBoard, user))
                .nickname(qnaBoard.getUser().getNickname())
                .grade(qnaBoard.getUser().getGrade())
                .profileImage(qnaBoard.getUser().getProfileImg())
                .createdAt(qnaBoard.getCreatedAt())
                .answers(getAnswerDetails(qnaBoard.getAnswerList(), user))
                .build();
    }


    public GetQuestionEditDetailRes getQuestionEditDetail(Integer qnaBoardId, Long userId) {
        User user;
        if (userId != null){
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.USER_NOT_FOUND));
        }
        else {
            user = null;
        }

        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(qnaBoardId.longValue())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        if (user != qnaBoard.getUser()){
            throw new InvalidQnaException(BaseResponseStatus.QNA_NO_EDIT_PERMISSION);
        }
        // ai답변을 제외한 답변이 달려있는 경우
        if (qnaBoard.getAnswerCount() > 1 ||
                (qnaBoard.getAnswerCount() == 1 && qnaBoard.getAnswerList().get(0).getUser().getId() != 0)){
            throw new InvalidQnaException(BaseResponseStatus.QNA_ANSWERED_EDIT);
        }

        return GetQuestionEditDetailRes.builder()
                .id(qnaBoard.getId())
                .userId(qnaBoard.getUser().getId())
                .superCategoryId(qnaBoard.getCategory().getSuperCategory() != null ?
                        qnaBoard.getCategory().getSuperCategory().getId() : null)
                .subCategoryId(qnaBoard.getCategory().getId())
                .title(qnaBoard.getTitle())
                .content(qnaBoard.getContent())
                .superCategoryName(qnaBoard.getCategory().getSuperCategory() != null ?
                        qnaBoard.getCategory().getSuperCategory().getCategoryName() : null)
                .subCategoryName(qnaBoard.getCategory() != null ?
                        qnaBoard.getCategory().getCategoryName() : null)
                .build();
    }

    public List<GetAnswerDetailListRes> getAnswerDetails(List<Answer> answers, User user) {
        return answers.stream()
                .filter(answer -> answer.isEnable())
                .map(answer -> GetAnswerDetailListRes.builder()
                        .id(answer.getId())
                        .userId(answer.getUser().getId())
                        .answer(answer.getContent())
                        .likeCnt(answer.getLikeCount())
                        .hateCnt(answer.getHateCount())
                        .checkLikeOrHate(isAnswerLikeORHate(answer, user))
                        .checkAdopted(answer.isAdopted())
                        .nickname(answer.getUser().getNickname())
                        .grade(answer.getUser().getGrade())
                        .profileImage(answer.getUser().getProfileImg())
                        .createdAt(answer.getCreatedAt())
                        .comments(getAnswerCommentDetails(answer.getAnswerCommentList()))
                        .build())
                .collect(Collectors.toList());

    }

    public List<GetAnswerCommentDetailListRes> getAnswerCommentDetails(List<AnswerComment> answerComments) {
        return answerComments.stream()
                .map(answerComment -> GetAnswerCommentDetailListRes.builder()
                        .id(answerComment.getId())
                        .userId(answerComment.getUser().getId())
                        .superCommentId(answerComment.getAnswerComment() != null ?
                                answerComment.getAnswerComment().getId() : null)
                        .answerComment(answerComment.getContent())
                        .nickname(answerComment.getUser().getNickname())
                        .grade(answerComment.getUser().getGrade())
                        .profileImage(answerComment.getUser().getProfileImg())
                        .createdAt(answerComment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public GetQuestionStateRes getQuestionState(Long qnaBoardId, Long userId) {
        User user;
        if (userId != null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.USER_NOT_FOUND));
        } else {
            user = null;
        }
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(qnaBoardId.longValue())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));

        return GetQuestionStateRes.builder()
                .id(qnaBoard.getId())
                .userId(qnaBoard.getUser().getId())
                .likeCnt(qnaBoard.getLikeCount())
                .hateCnt(qnaBoard.getHateCount())
                .checkLikeOrHate(isQuestionLikeORHate(qnaBoard, user))
                .checkScrap(isQuestionScarp(qnaBoard, user))
                .build();
    }

    public GetAnswerStateRes getAnswerState(Long answerId, Long userId) {
        User user;
        if (userId != null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.USER_NOT_FOUND));
        } else {
            user = null;
        }
       Answer answer = answerRepository.findByIdAndEnableTrue(answerId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));

        System.out.print(isAnswerLikeORHate(answer, user));

        return GetAnswerStateRes.builder()
                .id(answer.getId())
                .userId(answer.getUser().getId())
                .likeCnt(answer.getLikeCount())
                .hateCnt(answer.getHateCount())
                .checkLikeOrHate(isAnswerLikeORHate(answer, user))
                .build();
    }

    @Transactional
    public GetQuestionStateRes checkQnaLike(Long qnaBoardId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        QnaLike qnaLike = QnaLike.builder()
                .qnaBoard(qnaBoard)
                .user(user)
                .state(true)
                .build();

        Optional<QnaLike> beforeLike = qnaLikeRepository.findByUserIdAndQnaBoardIdAndState(userId, qnaBoard.getId(),true);
        Optional<QnaLike> beforeHate = qnaLikeRepository.findByUserIdAndQnaBoardIdAndState(userId, qnaBoard.getId(), false);

        // 이전에 좋아요만 했을 경우
        if (beforeLike.isPresent() && beforeHate.isEmpty()) {
            qnaLikeRepository.delete(beforeLike.get());
            qnaBoard.decreaseLikeCount();
            questionRepository.save(qnaBoard);
        }
        // 이전에 싫어요만 했을 경우
        else if (beforeLike.isEmpty() && beforeHate.isPresent()) {
            beforeHate.get().changeState();
            qnaBoard.decreaseHateCount();
            qnaBoard.increaseLikeCount();
            qnaLikeRepository.save(beforeHate.get());
            questionRepository.save(qnaBoard);
        }
        // 둘 다 체크하는 것은 이전 검사로 불가능하므로, 둘 다 없는 경우
        else {
            qnaLikeRepository.save(qnaLike);
            qnaBoard.increaseLikeCount();
            questionRepository.save(qnaBoard);
        }

        return GetQuestionStateRes.builder()
                .id(qnaBoard.getId())
                .userId(qnaBoard.getUser().getId())
                .likeCnt(qnaBoard.getLikeCount())
                .hateCnt(qnaBoard.getHateCount())
                .checkLikeOrHate(isQuestionLikeORHate(qnaBoard, user))
                .checkScrap(isQuestionScarp(qnaBoard, user))
                .build();
    }

    @Transactional
    public GetQuestionStateRes checkQnaHate(Long qnaBoardId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        QnaLike qnaLike = QnaLike.builder()
                .qnaBoard(qnaBoard)
                .user(user)
                .state(false)
                .build();

        Optional<QnaLike> beforeLike = qnaLikeRepository.findByUserIdAndQnaBoardIdAndState(userId, qnaBoard.getId(), true);
        Optional<QnaLike> beforeHate = qnaLikeRepository.findByUserIdAndQnaBoardIdAndState(userId, qnaBoard.getId(), false);

        // 이전에 싫어요만 했을 경우
        if (beforeHate.isPresent() && beforeLike.isEmpty()) {
            qnaLikeRepository.delete(beforeHate.get());
            qnaBoard.decreaseHateCount();
            questionRepository.save(qnaBoard);
        }
        // 이전에 좋아요만 했을 경우
        else if (beforeHate.isEmpty() && beforeLike.isPresent()) {
            beforeLike.get().changeState();
            qnaBoard.decreaseLikeCount();
            qnaBoard.increaseHateCount();
            qnaLikeRepository.save(beforeLike.get());
            questionRepository.save(qnaBoard);
        }
        // 둘 다 체크하는 것은 이전 검사로 불가능하므로, 둘 다 없는 경우
        else {
            qnaLikeRepository.save(qnaLike);
            qnaBoard.increaseHateCount();
            questionRepository.save(qnaBoard);
        }
        return GetQuestionStateRes.builder()
                .id(qnaBoard.getId())
                .userId(qnaBoard.getUser().getId())
                .likeCnt(qnaBoard.getLikeCount())
                .hateCnt(qnaBoard.getHateCount())
                .checkLikeOrHate(isQuestionLikeORHate(qnaBoard, user))
                .checkScrap(isQuestionScarp(qnaBoard, user))
                .build();
    }

    @Transactional
    public GetAnswerStateRes checkAnswerLike(Long qnaBoardId, Long answerId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        Answer answer = qnaBoard.getAnswerList().stream()
                .filter(ans -> ans.getId().equals(answerId))
                .findFirst()
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND));

        AnswerLike answerLike = AnswerLike.builder()
                .answer(answer)
                .user(user)
                .state(true)
                .build();

        Optional<AnswerLike> beforeLike = answerLikeRepository.findByAnswerIdAndUserIdAndStateAndAnswerEnableTrue(answer.getId(), userId, true);
        Optional<AnswerLike> beforeHate = answerLikeRepository.findByAnswerIdAndUserIdAndStateAndAnswerEnableTrue(answer.getId(), userId, false);

        // 이전에 좋아요만 했을 경우
        if (beforeLike.isPresent() && beforeHate.isEmpty()) {
            answerLikeRepository.delete(beforeLike.get());
            answer.decreaseLikeCount();
            answerRepository.save(answer);
        }
        // 이전에 싫어요만 했을 경우
        else if (beforeLike.isEmpty() && beforeHate.isPresent()) {
            beforeHate.get().changeState();
            answer.decreaseHateCount();
            answer.increaseLikeCount();
            answerLikeRepository.save(beforeHate.get());
            answerRepository.save(answer);

        }
        // 둘 다 체크하는 것은 이전 검사로 불가능하므로, 둘 다 없는 경우
        else {
            answerLikeRepository.save(answerLike);
            answer.increaseLikeCount();
            answerRepository.save(answer);
        }

        Optional<Answer> newAns = answerRepository.findByIdAndEnableTrue(answerId);

        return GetAnswerStateRes.builder()
                .id(newAns.get().getId())
                .userId(newAns.get().getUser().getId())
                .likeCnt(newAns.get().getLikeCount())
                .hateCnt(newAns.get().getHateCount())
                .checkLikeOrHate(isAnswerLikeORHate(newAns.get(), user))
                .build();
    }

    @Transactional
    public GetAnswerStateRes checkAnswerHate(Long qnaBoardId, Long answerId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        Answer answer = qnaBoard.getAnswerList().stream()
                .filter(ans -> ans.getId().equals(answerId))
                .findFirst()
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND));

        AnswerLike answerLike = AnswerLike.builder()
                .answer(answer)
                .user(user)
                .state(false)
                .build();

        Optional<AnswerLike> beforeLike = answerLikeRepository.findByAnswerIdAndUserIdAndStateAndAnswerEnableTrue(answer.getId(), userId, true);
        Optional<AnswerLike> beforeHate = answerLikeRepository.findByAnswerIdAndUserIdAndStateAndAnswerEnableTrue(answer.getId(), userId, false);

        // 이전에 싫어요만 했을 경우
        if (beforeHate.isPresent() && beforeLike.isEmpty()) {
            answerLikeRepository.delete(beforeHate.get());
            answer.decreaseHateCount();
            answerRepository.save(answer);
        }
        // 이전에 좋아요만 했을 경우
        else if (beforeHate.isEmpty() && beforeLike.isPresent()) {
            beforeLike.get().changeState();
            answer.decreaseLikeCount();
            answer.increaseHateCount();
            answerLikeRepository.save(beforeLike.get());
            answerRepository.save(answer);
        }
        // 둘 다 체크하는 것은 이전 검사로 불가능하므로, 둘 다 없는 경우
        else {
            answerLikeRepository.save(answerLike);
            answer.increaseHateCount();
            answerRepository.save(answer);
        }

        Optional<Answer> newAns = answerRepository.findByIdAndEnableTrue(answerId);

        return GetAnswerStateRes.builder()
                .id(newAns.get().getId())
                .userId(newAns.get().getUser().getId())
                .likeCnt(newAns.get().getLikeCount())
                .hateCnt(newAns.get().getHateCount())
                .checkLikeOrHate(isAnswerLikeORHate(newAns.get(), user))
                .build();
    }

    @Transactional
    public GetQuestionStateRes checkQnaScrap(Long qnaBoardId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        QnaScrap qnaScrap = QnaScrap.builder()
                .qnaBoard(qnaBoard)
                .user(user)
                .build();

        Optional<QnaScrap> beforeScrap = qnaScrapRepository.findByQnaBoardEnableTrueAndQnaBoardIdAndUserId(qnaBoardId, userId);
        if (beforeScrap.isPresent()) {
            qnaScrapRepository.delete(beforeScrap.get());
        } else {
            qnaScrapRepository.save(qnaScrap);
        }
        return GetQuestionStateRes.builder()
                .id(qnaBoard.getId())
                .userId(qnaBoard.getUser().getId())
                .likeCnt(qnaBoard.getLikeCount())
                .hateCnt(qnaBoard.getHateCount())
                .checkLikeOrHate(isQuestionLikeORHate(qnaBoard, user))
                .checkScrap(isQuestionScarp(qnaBoard, user))
                .build();
    }

    // 현재 접속한 사용자의 좋아요/싫어요/선택 X 상태를 알아내는 함수
    //userID-qnaBoardId를 활용해서 해당 사용자가 답변글에 어떤 상태를 표시했는지 나타내는 함수
    public Boolean isQuestionLikeORHate(QnaBoard qnaBoard, User user) {
        if (user == null){
            return null;
        }
        Optional<Boolean> state = qnaLikeRepository.findState(qnaBoard.getId(), user.getId());
        return state.orElse(null);
    }
    // userID-qnaBoardId를 활용해서 해당 사용자가 답변글에 어떤 상태를 표시했는지 나타내는 함수
    public Boolean isAnswerLikeORHate(Answer answer, User user) {
        if (user == null){
            return null;
        }
        Optional<Boolean> state = answerLikeRepository.findState(answer.getId(), user.getId());
        return state.orElse(null);
    }
    // 현재 접속한 사용자의 질문에 대한 스크랩 상태를 알아내는 함수
    private Boolean isQuestionScarp(QnaBoard qnaBoard, User user) {
        if (user == null){
            return null;
        }
        Optional<QnaScrap> qnaScrap = qnaScrapRepository.findByQnaBoardEnableTrueAndQnaBoardIdAndUserId(qnaBoard.getId(), user.getId());
        if (qnaScrap.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Long saveAnswer(CreateAnswerReq createAnswerReq, Long userId) {
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(createAnswerReq.getQnaBoardId())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        Answer answer = Answer.builder()
                .user(user)
                .qnaBoard(qnaBoard)
                .content(createAnswerReq.getContent())
                .build();

        answerRepository.save(answer);
        qnaBoard.increaseAnswerCount();
        questionRepository.save(qnaBoard);
        return answer.getId();
    }

    @Transactional
    public Long adoptedAnswer(Long qnaBoardId, Long answerId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        Answer answer = answerRepository.findByIdAndEnableTrue(answerId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND));

        if (user != qnaBoard.getUser()){
            throw new InvalidQnaException(BaseResponseStatus.QNA_NOT_QUESTIONER);
        }

        if (answerRepository.countAdopted(qnaBoardId).equals(0)) {
            answer.adoptedAnswer(true);
            qnaBoard.adopted(answerId);
            qnaBoard.changeResolved(Resolved.RESOLVED);
            answerRepository.save(answer);
            questionRepository.save(qnaBoard);
            return answer.getId();
        } else {
            throw new InvalidQnaException(BaseResponseStatus.QNA_ALREADY_ADOPTED);
        }
    }

    @Transactional
    public Long saveComment(CreateCommentReq createCommentReq, Long userId) {
        Optional<AnswerComment> superComment = Optional.empty();
        if (createCommentReq.getSuperCommentId() != null) {
            superComment = answerCommentRepository.findById(createCommentReq.getSuperCommentId());
            if (superComment.isEmpty()) {
                throw new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND);
            }
        }

        Answer answer = answerRepository.findByIdAndEnableTrue(createCommentReq.getAnswerId())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        AnswerComment answerComment = AnswerComment.builder()
                .user(user)
                .answer(answer)
                .content(createCommentReq.getContent())
                .answerComment(superComment.orElse(null))
                .build();

        answerCommentRepository.save(answerComment);
        answer.increaseCommentCount();
        answerRepository.save(answer);
        return answerComment.getId();
    }

    @Transactional
    public Long editQuestion(EditQuestionReq editQuestionReq, Long userId) {
        Category category = categoryRepository.findById(editQuestionReq.getCategoryId())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.CATEGORY_INVALID_CATEGORY_DATA));
        QnaBoard qnaBoard = questionRepository.findByIdAndEnableTrue(editQuestionReq.getId())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        if (user != qnaBoard.getUser()){
            throw new InvalidQnaException(BaseResponseStatus.QNA_NO_EDIT_PERMISSION);
        }

        if(qnaBoard.getAnswerList().isEmpty()){
            qnaBoard.updateTitle(editQuestionReq.getTitle());
            qnaBoard.updateContent(editQuestionReq.getContent());
            qnaBoard.updateCategory(category);
        }
        else {
            throw new InvalidQnaException(BaseResponseStatus.QNA_ANSWERED_EDIT);
        }

        questionRepository.save(qnaBoard);
        return qnaBoard.getId();
    }

    @Transactional
    public Long editAnswer(EditAnswerReq editAnswerReq, Long userId) {
        Answer answer  = answerRepository.findByIdAndEnableTrue(editAnswerReq.getId())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND));

        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        if (user != answer.getUser()){
            throw new InvalidQnaException(BaseResponseStatus.QNA_NO_EDIT_PERMISSION);
        }

        if(!answer.isAdopted()) {
            answer.updateContent(editAnswerReq.getContent());
        }
        else {
            throw new InvalidQnaException(BaseResponseStatus.QNA_ADOPTED_EDIT);
        }
        answerRepository.save(answer);
        return answer.getId();
    }

    @Transactional
    public Long disableQuestion(Long qnaBoardId, Long userId) {
        QnaBoard qnaBoard  = questionRepository.findByIdAndEnableTrue(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));

        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        if (user != qnaBoard.getUser()){
            throw new InvalidQnaException(BaseResponseStatus.QNA_NO_EDIT_PERMISSION);
        }

        qnaBoard.disable();
        questionRepository.save(qnaBoard);
        return qnaBoard.getId();
    }

    @Transactional
    public Long disableAnswer(Long qnaBoardId, Long answerId, Long userId) {
        QnaBoard qnaBoard  = questionRepository.findByIdAndEnableTrue(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        Answer answer  = answerRepository.findByIdAndEnableTrue(answerId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND));

        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        if (user != answer.getUser()){
            throw new InvalidQnaException(BaseResponseStatus.QNA_NO_EDIT_PERMISSION);
        }

        qnaBoard.decreaseAnswerCount();
        questionRepository.save(qnaBoard);
        answer.disable();
        answerRepository.save(answer);


        return answer.getId();
    }
}
