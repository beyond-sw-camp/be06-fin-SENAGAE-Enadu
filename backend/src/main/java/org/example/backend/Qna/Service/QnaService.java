package org.example.backend.Qna.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Qna.Repository.*;
import org.example.backend.Qna.model.Entity.*;
import org.example.backend.Qna.model.Res.GetAnswerCommentDetailListRes;
import org.example.backend.Qna.model.Res.GetAnswerDetailListRes;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.Res.GetQuestionDetailRes;
import org.example.backend.Qna.model.req.*;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QuestionRepository questionRepository;
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
        Page<QnaBoard> qnaBoardPage = questionRepository.findAll(pageable);

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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.USER_NOT_FOUND));
        QnaBoard qnaBoard = questionRepository.findById(qnaBoardId.longValue())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));

        return GetQuestionDetailRes.builder()
                .title(qnaBoard.getTitle())
                .content(qnaBoard.getContent())
                .superCategoryName(qnaBoard.getCategory().getSuperCategory() != null ?
                        qnaBoard.getCategory().getSuperCategory().getCategoryName() : null)
                .subCategoryName(qnaBoard.getCategory() != null ?
                        qnaBoard.getCategory().getCategoryName() : null)
                .likeCnt(qnaBoard.getLikeCount())
                .hateCnt(qnaBoard.getHateCount())
                .checkLikeOrHate(isQuestionLikeORHate(qnaBoard, user))
                .checkScrap(isQuestionScarp(qnaBoard, user))
                .nickname(qnaBoard.getUser().getNickname())
                .grade(qnaBoard.getUser().getGrade())
                .profileImage(qnaBoard.getUser().getProfileImg())
                .createdAt(qnaBoard.getCreatedAt())
                .answers(getAnswerDetails(qnaBoard.getAnswerList(), user))
                .build();
    }

    public List<GetAnswerDetailListRes> getAnswerDetails(List<Answer> answers, User user) {
        return answers.stream()
                .map(answer -> GetAnswerDetailListRes.builder()
                        .id(answer.getId())
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

    @Transactional
    public Long checkQnaLike(Long qnaBoardId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findById(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        QnaLike qnaLike = QnaLike.builder()
                .qnaBoard(qnaBoard)
                .user(user)
                .state(true)
                .build();

        Optional<QnaLike> beforeLike = qnaLikeRepository.findLikeByQnaBoardIdAndUserIdAndState(qnaBoard.getId(), userId, true);
        Optional<QnaLike> beforeHate = qnaLikeRepository.findLikeByQnaBoardIdAndUserIdAndState(qnaBoard.getId(), userId, false);

        // 이전에 좋아요만 했을 경우
        if (beforeLike.isPresent() && beforeHate.isEmpty()) {
            qnaLikeRepository.delete(beforeLike.get());
            qnaBoard.decreaseLikeCount();
            return 0L;
        }
        // 이전에 싫어요만 했을 경우
        else if (beforeLike.isEmpty() && beforeHate.isPresent()) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_CONFLICT_LIKE_DISLIKE);
        }
        // 둘 다 체크하는 것은 이전 검사로 불가능하므로, 둘 다 없는 경우
        else {
            qnaLikeRepository.save(qnaLike);
            qnaBoard.increaseLikeCount();
            return qnaLike.getId();
        }
    }

    @Transactional
    public Long checkQnaHate(Long qnaBoardId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findById(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        QnaLike qnaLike = QnaLike.builder()
                .qnaBoard(qnaBoard)
                .user(user)
                .state(false)
                .build();

        Optional<QnaLike> beforeLike = qnaLikeRepository.findLikeByQnaBoardIdAndUserIdAndState(qnaBoard.getId(), userId, true);
        Optional<QnaLike> beforeHate = qnaLikeRepository.findLikeByQnaBoardIdAndUserIdAndState(qnaBoard.getId(), userId, false);

        // 이전에 싫어요만 했을 경우
        if (beforeHate.isPresent() && beforeLike.isEmpty()) {
            qnaLikeRepository.delete(beforeHate.get());
            qnaBoard.decreaseHateCount();
            return 0L;
        }
        // 이전에 좋아요만 했을 경우
        else if (beforeHate.isEmpty() && beforeLike.isPresent()) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_CONFLICT_LIKE_DISLIKE);
        }
        // 둘 다 체크하는 것은 이전 검사로 불가능하므로, 둘 다 없는 경우
        else {
            qnaLikeRepository.save(qnaLike);
            qnaBoard.increaseHateCount();
            return qnaLike.getId();
        }
    }

    @Transactional
    public Long checkAnswerLike(Long qnaBoardId, Long answerId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findById(qnaBoardId)
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

        Optional<AnswerLike> beforeLike = answerLikeRepository.findLikeByQnaAnswerIdAndUserIdAndState(answer.getId(), userId, true);
        Optional<AnswerLike> beforeHate = answerLikeRepository.findLikeByQnaAnswerIdAndUserIdAndState(answer.getId(), userId, false);

        // 이전에 좋아요만 했을 경우
        if (beforeLike.isPresent() && beforeHate.isEmpty()) {
            answerLikeRepository.delete(beforeLike.get());
            answer.decreaseLikeCount();
            return 0L;
        }
        // 이전에 싫어요만 했을 경우
        else if (beforeLike.isEmpty() && beforeHate.isPresent()) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_CONFLICT_LIKE_DISLIKE);
        }
        // 둘 다 체크하는 것은 이전 검사로 불가능하므로, 둘 다 없는 경우
        else {
            answerLikeRepository.save(answerLike);
            answer.increaseLikeCount();
            return answerLike.getId();
        }
    }

    @Transactional
    public Long checkAnswerHate(Long qnaBoardId, Long answerId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findById(qnaBoardId)
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

        Optional<AnswerLike> beforeLike = answerLikeRepository.findLikeByQnaAnswerIdAndUserIdAndState(answer.getId(), userId, true);
        Optional<AnswerLike> beforeHate = answerLikeRepository.findLikeByQnaAnswerIdAndUserIdAndState(answer.getId(), userId, false);

        // 이전에 싫어요만 했을 경우
        if (beforeHate.isPresent() && beforeLike.isEmpty()) {
            answerLikeRepository.delete(beforeHate.get());
            answer.decreaseHateCount();
            return 0L;
        }
        // 이전에 좋아요만 했을 경우
        else if (beforeHate.isEmpty() && beforeLike.isPresent()) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_CONFLICT_LIKE_DISLIKE);
        }
        // 둘 다 체크하는 것은 이전 검사로 불가능하므로, 둘 다 없는 경우
        else {
            answerLikeRepository.save(answerLike);
            answer.increaseHateCount();
            return answerLike.getId();
        }
    }

    @Transactional
    public Long checkQnaScrap(Long qnaBoardId, Long userId) {
        QnaBoard qnaBoard = questionRepository.findById(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        QnaScrap qnaScrap = QnaScrap.builder()
                .qnaBoard(qnaBoard)
                .user(user)
                .build();

        Optional<QnaScrap> beforeScrap = qnaScrapRepository.findScrapByQnaBoardIdAndUserId(qnaBoardId, userId);
        if (beforeScrap.isPresent()) {
            qnaScrapRepository.delete(beforeScrap.get());
            return 0L;
        } else {
            qnaScrapRepository.save(qnaScrap);
            return qnaScrap.getId();
        }
    }

    // 현재 접속한 사용자의 좋아요/싫어요/선택 X 상태를 알아내는 함수
    //userID-qnaBoardId를 활용해서 해당 사용자가 답변글에 어떤 상태를 표시했는지 나타내는 함수
    public Boolean isQuestionLikeORHate(QnaBoard qnaBoard, User user) {
        Optional<Boolean> state = qnaLikeRepository.findStateByQnaBoardIdAndUserId(qnaBoard.getId(), user.getId());
        return state.orElse(null);
    }
    // userID-qnaBoardId를 활용해서 해당 사용자가 답변글에 어떤 상태를 표시했는지 나타내는 함수
    public Boolean isAnswerLikeORHate(Answer answer, User user) {
        Optional<Boolean> state = answerLikeRepository.findStateByAnswerIdAndUserId(answer.getId(), user.getId());
        return state.orElse(null);
    }
    // 현재 접속한 사용자의 질문에 대한 스크랩 상태를 알아내는 함수
    private boolean isQuestionScarp(QnaBoard qnaBoard, User user) {
        Optional<QnaScrap> qnaScrap = qnaScrapRepository.findScrapByQnaBoardIdAndUserId(qnaBoard.getId(), user.getId());
        if (qnaScrap.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Long saveAnswer(CreateAnswerReq createAnswerReq, Long userId) {
        QnaBoard qnaBoard = questionRepository.findById(createAnswerReq.getQnaBoardId())
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
        return answer.getId();
    }

    @Transactional
    public Long adoptedAnswer(Long qnaBoardId, Long answerId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        QnaBoard qnaBoard = questionRepository.findById(qnaBoardId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND));

        if (answerRepository.countAdoptedAnswersByQuestionId(qnaBoardId).equals(0)) {
            answer.adoptedAnswer(true);
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

        Answer answer = answerRepository.findById(createCommentReq.getAnswerId())
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
        return answerComment.getId();
    }

    @Transactional
    public Long editQuestion(EditQuestionReq editQuestionReq, Long userId) {
        Category category = categoryRepository.findById(editQuestionReq.getCategoryId())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.CATEGORY_INVALID_CATEGORY_DATA));
        QnaBoard qnaBoard = questionRepository.findById(editQuestionReq.getId())
                .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        //후에 권한 처리

        qnaBoard.updateTitle(editQuestionReq.getTitle());
        qnaBoard.updateContent(editQuestionReq.getContent());
        qnaBoard.updateCategory(category);

        questionRepository.save(qnaBoard);
        return qnaBoard.getId();
    }
}
