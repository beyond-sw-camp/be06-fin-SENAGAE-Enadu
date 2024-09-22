package org.example.backend.Qna.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Qna.Service.QnaService;
import org.example.backend.Qna.model.req.CreateAnswerReq;
import org.example.backend.Qna.model.req.CreateCommentReq;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<Long> checkAnsLike(@RequestParam Long qnaBoardId, @RequestParam Long answerId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.checkAnswerLike(qnaBoardId, answerId, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //qna 답변 싫어요
    @PostMapping("/hate")
    public BaseResponse<Long> checkAnsHate(@RequestParam Long qnaBoardId, @RequestParam Long answerId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.checkAnswerHate(qnaBoardId, answerId, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //qna 답변 채택
    @PostMapping("/adopted")
    public BaseResponse<Long> adoptedAnswer(@RequestParam Long qnaBoardId, @RequestParam Long answerId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.adoptedAnswer(qnaBoardId, answerId, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //답변 댓글 등록
    //answer 등록
    @PostMapping("/comment")
    public BaseResponse<Long> saveComment(@RequestBody CreateCommentReq createCommentReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.saveComment(createCommentReq, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }
}
