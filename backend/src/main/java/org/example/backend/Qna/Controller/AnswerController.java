package org.example.backend.Qna.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Qna.Service.QnaService;
import org.example.backend.Qna.model.Res.GetAnswerStateRes;
import org.example.backend.Qna.model.Res.GetQuestionStateRes;
import org.example.backend.Qna.model.req.*;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.Res.GetQuestionDetailRes;
import org.example.backend.Qna.model.req.CreateAnswerReq;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ans")
public class AnswerController {
    private final QnaService qnaService;

    //answer 등록
    @PostMapping()
    public BaseResponse<Long> saveAnswer(@RequestBody CreateAnswerReq createAnswerReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.saveAnswer(createAnswerReq, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //qna 답변 좋아요
    @PostMapping("/like")
    public BaseResponse<GetAnswerStateRes> checkAnsLike(@RequestBody Map<String, Long> requestBody, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long qnaBoardId = requestBody.get("qnaBoardId");
        Long answerId = requestBody.get("answerId");

        GetAnswerStateRes answerStateRes = qnaService.checkAnswerLike(qnaBoardId, answerId, customUserDetails.getUserId());
        return new BaseResponse<>(answerStateRes);
    }

    //qna 답변 싫어요
    @PostMapping("/hate")
    public BaseResponse<GetAnswerStateRes> checkAnsHate(@RequestBody Map<String, Long> requestBody, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long qnaBoardId = requestBody.get("qnaBoardId");
        Long answerId = requestBody.get("answerId");

        GetAnswerStateRes answerStateRes = qnaService.checkAnswerHate(qnaBoardId, answerId, customUserDetails.getUserId());
        return new BaseResponse<>(answerStateRes);
    }

    @GetMapping("/state")
    public BaseResponse<GetAnswerStateRes> getAnswerState(Long answerId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        GetAnswerStateRes ansStateRes = qnaService.getAnswerState(answerId, customUserDetails.getUserId());
        return new BaseResponse<>(ansStateRes);
    }

    //qna 답변 채택
    @PostMapping("/adopted")
    public BaseResponse<Long> adoptedAnswer(@RequestBody Map<String, Long> requestBody, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long qnaBoardId = requestBody.get("qnaBoardId");
        Long answerId = requestBody.get("answerId");

        Long id = qnaService.adoptedAnswer(qnaBoardId, answerId, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //답변 댓글 등록
    @PostMapping("/comment")
    public BaseResponse<Long> saveComment(@RequestBody CreateCommentReq createCommentReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.saveComment(createCommentReq, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    @PatchMapping()
    public BaseResponse<Long> editAnswer(@RequestBody EditAnswerReq editAnswerReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.editAnswer(editAnswerReq, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    @PatchMapping("/removal")
    public BaseResponse<Long> disableAnswer(@RequestBody Map<String,Long> requestBody, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long qnaBoardId = requestBody.get("qnaBoardId");
        Long answerId = requestBody.get("answerId");

        Long id = qnaService.disableAnswer(qnaBoardId, answerId, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

}
