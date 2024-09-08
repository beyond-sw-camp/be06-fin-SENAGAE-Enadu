package org.example.backend.Qna.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Answer.Model.Entity.Answer;
import org.example.backend.Answer.Model.Entity.AnswerComment;
import org.example.backend.Answer.Model.Entity.AnswerLike;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Qna.Repository.QuestionRepository;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.Qna.model.Entity.QnaLike;
import org.example.backend.Qna.model.Entity.QnaScrap;
import org.example.backend.Qna.model.Entity.Res.GetAnswerCommentDetailListRes;
import org.example.backend.Qna.model.Entity.Res.GetAnswerDetailListRes;
import org.example.backend.Qna.model.Entity.Res.GetQnaListRes;
import org.example.backend.Qna.model.Entity.Res.GetQuestionDetailRes;
import org.example.backend.Qna.model.Entity.req.CreateQuestionReq;
import org.example.backend.Qna.model.Entity.req.GetQnaListReq;
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

    public void saveQuestion(CreateQuestionReq req) {
        Optional<Category> category = categoryRepository.findById(req.getCategoryId());

        if (category.isPresent()) {
            QnaBoard qnaBoard = QnaBoard.builder()
                    .title(req.getTitle())
                    .content(req.getContent())
                    .category(category.get())
                    .build();

            qnaBoard.createdAt();
            questionRepository.save(qnaBoard);
        }

    }

    public List<GetQnaListRes> getQnaList(GetQnaListReq req) {
        Pageable pageable;
        // 최신순 or 좋아요가 많은 순으로 정렬 type 지정, 둘 다 아니면 에러
        if (req.getSort().equals("latest")) {
            pageable = PageRequest.of(req.getPage(), req.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        } else if (req.getSort().equals("like")) {
            pageable = PageRequest.of(req.getPage(), req.getSize(), Sort.by(Sort.Direction.DESC, "likeCnt"));
        } else {
            throw new InvalidQnaException(BaseResponseStatus.INVALID_SEARCH_TYPE);
        }
        // paging 처리 및 responseList 생성
        Page<QnaBoard> qnaBoardPage = questionRepository.findAll(pageable);
        List<GetQnaListRes> responseList = qnaBoardPage.getContent().stream().map
                        (qnaBoard -> GetQnaListRes.builder()
                                .id(qnaBoard.getId())
                                .title(qnaBoard.getTitle())
                                // 이렇게 하면 top이 아니라 차상위로 지정됨 수정 필요
                                .superCategory(qnaBoard.getCategory().getSuperCategory().getCategoryName())
                                .subCategory(qnaBoard.getCategory().getCategoryName())
                                .nickname("ASHD89")
                                .profileImage("example.img")
                                .grade("god")
//                        .nickname(qnaBoard.getUser().getNickname())
//                        .profileImage(qnaBoard.getUser().getProfileImg())
//                        .grade(qnaBoard.getUser().getGrade())
                                .likeCnt(qnaBoard.getLikeCount())
                                .answerCnt(qnaBoard.getAnswerCount())
                                .createdAt(qnaBoard.getCreatedAt())
                                .build())
                .collect(Collectors.toList());

        return responseList;
    }

    public GetQuestionDetailRes getQuestionDetail(Long qnaBoardId) {
        Optional<QnaBoard> qnaBoard = questionRepository.findById(qnaBoardId);
        GetQuestionDetailRes questionDetail;
        if (qnaBoard.isPresent()) {
            questionDetail = GetQuestionDetailRes.builder()
                    .title(qnaBoard.get().getTitle())
                    .content(qnaBoard.get().getContent())
                    .superCategory(qnaBoard.get().getCategory().getSuperCategory().getCategoryName())
                    .subCategory(qnaBoard.get().getCategory().getCategoryName())
                    .likeCnt(qnaBoard.get().getLikeCount())
                    .hateCnt(qnaBoard.get().getHateCount())
                    .checkLike(isQuestionLikeORHate(qnaBoard.get().getQnaLikeList()))
                    .checkHate(isQuestionLikeORHate(qnaBoard.get().getQnaLikeList()))
                    .checkScrap(isQuestionScarp(qnaBoard.get().getQnaScrapList()))
                    .nickname(qnaBoard.get().getUser().getNickname())
                    .grade(qnaBoard.get().getUser().getGrade())
                    .profileImage(qnaBoard.get().getUser().getProfileImg())
                    .createdAt(qnaBoard.get().getCreatedAt())
                    .answers(getAnswerDetails(qnaBoard.get().getAnswerList()))
                    .build();
        } else {
            throw new InvalidQnaException(BaseResponseStatus.QUESTION_NOT_FOUND);
        }
        return questionDetail;
    }

    public List<GetAnswerDetailListRes> getAnswerDetails(List<Answer> answers) {
        return answers.stream()
                .map(answer -> GetAnswerDetailListRes.builder()
                        .answer(answer.getContent())
                        .likeCnt(answer.getLikeCnt())
                        .hateCnt(answer.getHateCnt())
                        .checkLike(isAnswerLikeORHate(answer.getAnswerLikeList()))
                        .checkHate(isAnswerLikeORHate(answer.getAnswerLikeList()))
                        .nickname(answer.getUser().getNickname())
                        .grade(answer.getUser().getGrade())
                        .profileImage(answer.getUser().getProfileImg())
                        .createdAt(answer.getCreatedAt())
                        .comments(getanswerCommentDetails(answer.getAnswerCommentList()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<GetAnswerCommentDetailListRes> getanswerCommentDetails(List<AnswerComment> answerComments) {
        return answerComments.stream()
                .map(answerComment -> GetAnswerCommentDetailListRes.builder()
                        .superCommentId(answerComment.getAnswerComment().getId())
                        .answerComment(answerComment.getContent())
                        .nickname(answerComment.getUser().getNickname())
                        .grade(answerComment.getUser().getGrade())
                        .profileImage(answerComment.getUser().getProfileImg())
                        .createdAt(answerComment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 현재 접속한 사용자의 좋아요/싫어요/선택 X 상태를 알아내는 함수
    //userID-qnaBoardId를 활용해서 해당 사용자가 답변글에 어떤 상태를 표시했는지 나타내는 함수
    public Boolean isQuestionLikeORHate(List<QnaLike> qnaLikeList) {
        return true;
    }

    // userID-qnaBoardId를 활용해서 해당 사용자가 답변글에 어떤 상태를 표시했는지 나타내는 함수
    public Boolean isAnswerLikeORHate(List<AnswerLike> answerLikeList) {
        return true;
    }

    // 현재 접속한 사용자의 질문에 대한 스크랩 상태를 알아내는 함수
    private boolean isQuestionScarp(List<QnaScrap> qnaScrapList) {
        return true;
    }


}
