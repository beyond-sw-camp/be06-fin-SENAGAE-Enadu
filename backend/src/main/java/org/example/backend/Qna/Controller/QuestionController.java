package org.example.backend.Qna.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Res.SubCategoryRes;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Qna.Service.QnaService;
import org.example.backend.Qna.model.Entity.Res.GetQnaListRes;
import org.example.backend.Qna.model.Entity.req.CreateQuestionReq;
import org.example.backend.Qna.model.Entity.req.GetQnaListReq;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {
    private final QnaService qnaService;

    //qna 등록
    @PostMapping()
    public BaseResponse<Void> saveQuestion(@RequestBody CreateQuestionReq createQuestionReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        qnaService.saveQuestion(createQuestionReq, customUserDetails);
        return new BaseResponse<>();
    }

    //qna 목록 조회
    @GetMapping("list")
    public BaseResponse<List<GetQnaListRes>> getQnaList(@RequestBody GetQnaListReq getQnaListReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<GetQnaListRes> qnaListRes = qnaService.getQnaList(getQnaListReq);
        return new BaseResponse<>(qnaListRes);

    }
}
