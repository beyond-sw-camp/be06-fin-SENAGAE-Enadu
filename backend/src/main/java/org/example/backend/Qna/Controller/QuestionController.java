package org.example.backend.Qna.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Res.SubCategoryRes;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Qna.Service.QnaService;
import org.example.backend.Qna.model.Entity.Res.GetQnaListRes;
import org.example.backend.Qna.model.Entity.Res.GetQuestionDetailRes;
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
    public BaseResponse<Long> saveQuestion(@RequestBody CreateQuestionReq createQuestionReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.saveQuestion(createQuestionReq, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //qna 목록 조회
    @GetMapping("/list")
    public BaseResponse<List<GetQnaListRes>> getQnaList(GetQnaListReq getQnaListReq) {
        List<GetQnaListRes> qnaListRes = qnaService.getQnaList(getQnaListReq);
        return new BaseResponse<>(qnaListRes);

    }

    //qna 상세 조회
    @GetMapping("/detail")
    public BaseResponse<GetQuestionDetailRes> getQnaDetail(Integer qnaBoardId) {
        GetQuestionDetailRes questionDetailRes = qnaService.getQuestionDetail(qnaBoardId);
        return new BaseResponse<>(questionDetailRes);

    }
}
