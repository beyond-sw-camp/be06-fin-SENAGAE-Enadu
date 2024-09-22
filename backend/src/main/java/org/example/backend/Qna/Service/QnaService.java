package org.example.backend.Qna.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Qna.Repository.AnswerLikeRepository;
import org.example.backend.Qna.Repository.QnaLikeRepository;
import org.example.backend.Qna.Repository.QnaScrapRepository;
import org.example.backend.Qna.Repository.QuestionRepository;
import org.example.backend.Qna.model.Entity.*;
import org.example.backend.Qna.model.Res.GetAnswerCommentDetailListRes;
import org.example.backend.Qna.model.Res.GetAnswerDetailListRes;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.Res.GetQuestionDetailRes;
import org.example.backend.Qna.model.req.CreateQuestionReq;
import org.example.backend.Qna.model.req.GetQnaListReq;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Repository.UserRepository;
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
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final QnaLikeRepository qnaLikeRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final QnaScrapRepository qnaScrapRepository;

    @Transactional
    public Long saveQuestion(CreateQuestionReq createQuestionReq, Long userId) {
        Optional<Category> category = categoryRepository.findById(createQuestionReq.getCategoryId());
        Optional<User> user = userRepository.findById(userId);

        if (category.isPresent() && user.isPresent()) {
            QnaBoard qnaBoard = QnaBoard.builder()
                    .user(user.get())
                    .title(createQuestionReq.getTitle())
                    .content(createQuestionReq.getContent())
                    .category(category.get())
                    .build();

            questionRepository.save(qnaBoard);
            return qnaBoard.getId();
        } else if (category.isEmpty()) {
            throw new InvalidQnaException(BaseResponseStatus.CATEGORY_INVALID_CATEGORY_DATA);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
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

        return qnaBoardPage.getContent().stream().map
                        (qnaBoard -> GetQnaListRes.builder()
                                .id(qnaBoard.getId())
                                .title(qnaBoard.getTitle())
                                .superCategoryName(qnaBoard.getCategory().getSuperCategory() != null ?
                                        qnaBoard.getCategory().getSuperCategory().getCategoryName() : null)
                                .subCategoryName(qnaBoard.getCategory() != null ?
                                        qnaBoard.getCategory().getCategoryName() : null)
                                .subCategoryName(qnaBoard.getCategory().getCategoryName())
                                .nickname(qnaBoard.getUser().getNickname())
                                .profileImage(qnaBoard.getUser().getProfileImg())
                                .grade(qnaBoard.getUser().getGrade())
                                .likeCnt(qnaBoard.getLikeCount())
                                .answerCnt(qnaBoard.getAnswerCount())
                                .createdAt(qnaBoard.getCreatedAt())
                                .build())
                .collect(Collectors.toList());
    }

    public GetQuestionDetailRes getQuestionDetail(Integer qnaBoardId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<QnaBoard> qnaBoard = questionRepository.findById(qnaBoardId.longValue());
        GetQuestionDetailRes questionDetail;
        if (qnaBoard.isPresent() && user.isPresent()) {
            questionDetail = GetQuestionDetailRes.builder()
                    .title(qnaBoard.get().getTitle())
                    .content(qnaBoard.get().getContent())
                    .superCategoryName(qnaBoard.get().getCategory().getSuperCategory() != null ?
                            qnaBoard.get().getCategory().getSuperCategory().getCategoryName() : null)
                    .subCategoryName(qnaBoard.get().getCategory() != null ?
                            qnaBoard.get().getCategory().getCategoryName() : null)
                    .likeCnt(qnaBoard.get().getLikeCount())
                    .hateCnt(qnaBoard.get().getHateCount())
                    .checkLikeOrHate(isQuestionLikeORHate(qnaBoard.get(), user.get()))
                    .checkScrap(isQuestionScarp(qnaBoard.get(), user.get()))
                    .nickname(qnaBoard.get().getUser().getNickname())
                    .grade(qnaBoard.get().getUser().getGrade())
                    .profileImage(qnaBoard.get().getUser().getProfileImg())
                    .createdAt(qnaBoard.get().getCreatedAt())
                    .answers(getAnswerDetails(qnaBoard.get().getAnswerList(), user.get()))
                    .build();
        } else {
            throw new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND);
        }
        return questionDetail;
    }

    public List<GetAnswerDetailListRes> getAnswerDetails(List<Answer> answers, User user) {
        return answers.stream()
                .map(answer -> GetAnswerDetailListRes.builder()
                        .id(answer.getId())
                        .answer(answer.getContent())
                        .likeCnt(answer.getLikeCount())
                        .hateCnt(answer.getHateCount())
                        .checkLikeOrHate(isAnswerLikeORHate(answer, user))
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
                                answerComment.getId() : null)
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
        Optional<QnaBoard> qnaBoard = questionRepository.findById(qnaBoardId);
        Optional<User> user = userRepository.findById(userId);

        if (qnaBoard.isPresent() && user.isPresent()) {
            QnaLike qnaLike = QnaLike.builder()
                    .qnaBoard(qnaBoard.get())
                    .user(user.get())
                    .state(true)
                    .build();

            Optional<QnaLike> beforeLike = qnaLikeRepository.findLikeByQnaBoardIdAndUserIdAndState(qnaBoard.get().getId(), userId, true);
            Optional<QnaLike> beforeHate = qnaLikeRepository.findLikeByQnaBoardIdAndUserIdAndState(qnaBoard.get().getId(), userId, false);
            //이전에 좋아요만 했을 경우
            if (beforeLike.isPresent() && beforeHate.isEmpty()) {
                qnaLikeRepository.delete(beforeLike.get());
                qnaBoard.get().decreaseLikeCount();
                return 0L;
            }
            //이전에 싫어요만 했을 경우
            else if (beforeLike.isEmpty() && beforeHate.isPresent()) {
                throw new InvalidQnaException(BaseResponseStatus.QNA_CONFLICT_LIKE_DISLIKE);
            }
            // 둘다 체크하는 거는 이전 검사로 불가능하므로, 둘다 없는 경우
            else {
                qnaLikeRepository.save(qnaLike);
                qnaBoard.get().increaseLikeCount();
                return qnaLike.getId();
            }

        } else if (qnaBoard.isEmpty()) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }

    @Transactional
    public Long checkQnaHate(Long qnaBoardId, Long userId) {
        Optional<QnaBoard> qnaBoard = questionRepository.findById(qnaBoardId);
        Optional<User> user = userRepository.findById(userId);

        if (qnaBoard.isPresent() && user.isPresent()) {
            QnaLike qnaLike = QnaLike.builder()
                    .qnaBoard(qnaBoard.get())
                    .user(user.get())
                    .state(false)
                    .build();

            Optional<QnaLike> beforeLike = qnaLikeRepository.findLikeByQnaBoardIdAndUserIdAndState(qnaBoard.get().getId(), userId, true);
            Optional<QnaLike> beforeHate = qnaLikeRepository.findLikeByQnaBoardIdAndUserIdAndState(qnaBoard.get().getId(), userId, false);
            //이전에 싫어요만 했을 경우
            if (beforeHate.isPresent() && beforeLike.isEmpty()) {
                qnaLikeRepository.delete(beforeHate.get());
                qnaBoard.get().decreaseHateCount();
                return 0L;
            }
            //이전에 좋아요만 했을 경우
            else if (beforeHate.isEmpty() && beforeLike.isPresent()) {
                throw new InvalidQnaException(BaseResponseStatus.QNA_CONFLICT_LIKE_DISLIKE);
            }
            // 둘다 체크하는 거는 이전 검사로 불가능하므로, 둘다 없는 경우
            else {
                qnaLikeRepository.save(qnaLike);
                qnaBoard.get().increaseHateCount();
                return qnaLike.getId();
            }

        } else if (qnaBoard.isEmpty()) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }

    @Transactional
    public Long checkAnswerLike(Long qnaBoardId, Long answerId, Long userId) {
        Optional<QnaBoard> qnaBoard = questionRepository.findById(qnaBoardId);
        Optional<User> user = userRepository.findById(userId);

        if (qnaBoard.isPresent() && user.isPresent()) {
            Answer answer = qnaBoard.get().getAnswerList().stream()
                    .filter(ans -> ans.getId().equals(answerId))
                    .findFirst()
                    .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND));

            AnswerLike answerLike = AnswerLike.builder()
                    .answer(answer)
                    .user(user.get())
                    .state(true)
                    .build();

            Optional<AnswerLike> beforeLike = answerLikeRepository.findLikeByQnaAnswerIdAndUserIdAndState(answer.getId(), userId, true);
            Optional<AnswerLike> beforeHate = answerLikeRepository.findLikeByQnaAnswerIdAndUserIdAndState(answer.getId(), userId, false);

            //이전에 좋아요만 했을 경우
            if (beforeLike.isPresent() && beforeHate.isEmpty()) {
                answerLikeRepository.delete(beforeLike.get());
                answer.decreaseLikeCount();
                return 0L;
            }
            //이전에 싫어요만 했을 경우
            else if (beforeLike.isEmpty() && beforeHate.isPresent()) {
                throw new InvalidQnaException(BaseResponseStatus.QNA_CONFLICT_LIKE_DISLIKE);
            }
            // 둘다 체크하는 거는 이전 검사로 불가능하므로, 둘다 없는 경우
            else {
                answerLikeRepository.save(answerLike);
                answer.increaseLikeCount();
                return answerLike.getId();
            }

        } else if (qnaBoard.isEmpty()) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }

    @Transactional
    public Long checkAnswerHate(Long qnaBoardId, Long answerId, Long userId) {
        Optional<QnaBoard> qnaBoard = questionRepository.findById(qnaBoardId);
        Optional<User> user = userRepository.findById(userId);

        if (qnaBoard.isPresent() && user.isPresent()) {
            Answer answer = qnaBoard.get().getAnswerList().stream()
                    .filter(ans -> ans.getId().equals(answerId))
                    .findFirst()
                    .orElseThrow(() -> new InvalidQnaException(BaseResponseStatus.QNA_ANSWER_NOT_FOUND));

            AnswerLike answerLike = AnswerLike.builder()
                    .answer(answer)
                    .user(user.get())
                    .state(false)
                    .build();

            Optional<AnswerLike> beforeLike = answerLikeRepository.findLikeByQnaAnswerIdAndUserIdAndState(answer.getId(), userId, true);
            Optional<AnswerLike> beforeHate = answerLikeRepository.findLikeByQnaAnswerIdAndUserIdAndState(answer.getId(), userId, false);

            //이전에 싫어요만 했을 경우
            if (beforeHate.isPresent() && beforeLike.isEmpty()) {
                answerLikeRepository.delete(beforeHate.get());
                answer.decreaseHateCount();
                return 0L;
            }
            //이전에 좋아요만 했을 경우
            else if (beforeHate.isEmpty() && beforeLike.isPresent()) {
                throw new InvalidQnaException(BaseResponseStatus.QNA_CONFLICT_LIKE_DISLIKE);
            }
            // 둘다 체크하는 거는 이전 검사로 불가능하므로, 둘다 없는 경우
            else {
                answerLikeRepository.save(answerLike);
                answer.increaseHateCount();
                return answerLike.getId();
            }

        } else if (qnaBoard.isEmpty()) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }

    @Transactional
    public Long checkQnaScrap(Long qnaBoardId, Long userId) {
        Optional<QnaBoard> qnaBoard = questionRepository.findById(qnaBoardId);
        Optional<User> user = userRepository.findById(userId);

        if (qnaBoard.isPresent() && user.isPresent()) {
            QnaScrap qnaScrap = QnaScrap.builder()
                    .qnaBoard(qnaBoard.get())
                    .user(user.get())
                    .build();
            Optional<QnaScrap> beforeScrap = qnaScrapRepository.findScrapByQnaBoardIdAndUserId(qnaScrap.getId(), userId);
            if (beforeScrap.isPresent()) {
                qnaScrapRepository.delete(beforeScrap.get());
                return 0L;
            } else {
                qnaScrapRepository.save(qnaScrap);
                return qnaScrap.getId();
            }

        } else if (qnaBoard.isEmpty()) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }


//    public Integer CntLikeOrHateInQna(Long qnaBoardId, boolean state) {
//        Optional<QnaBoard> qnaBoard = questionRepository.findById(qnaBoardId);
//        if (qnaBoard.isPresent()) {
//            Integer likeOrHateCnt = qnaLikeRepository.QnaLikeCountByQnaBoard(qnaBoard.get(), state);
//            return likeOrHateCnt;
//
//        } else {
//            throw new InvalidUserException(BaseResponseStatus.QNA_QUESTION_NOT_FOUND);
//        }
//    }


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
}
