package org.example.backend.Qna.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Res.SubCategoryRes;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Qna.Service.QnaService;
import org.example.backend.Qna.model.Entity.req.CreateQuestionReq;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {
    private final QnaService qnaService;

    //qna 등록
    @PostMapping()
    public BaseResponse<Void> saveQuestion(@RequestBody CreateQuestionReq req) {
        qnaService.saveQuestion(req);
        return new BaseResponse<>();
    }
}
