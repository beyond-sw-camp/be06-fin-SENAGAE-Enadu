package org.example.backend.Qna.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Qna.Service.BasicQnaSearchService;
import org.example.backend.Qna.Service.QnaService;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.Res.GetQuestionDetailRes;
import org.example.backend.Qna.model.req.CreateQuestionReq;
import org.example.backend.Qna.model.req.GetQnaListReq;
import org.example.backend.Qna.model.req.GetQnaSearchReq;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {
    private final QnaService qnaService;
    private final BasicQnaSearchService basicQnaSearchService;

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
    public BaseResponse<GetQuestionDetailRes> getQnaDetail(Integer qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        GetQuestionDetailRes questionDetailRes = qnaService.getQuestionDetail(qnaBoardId, customUserDetails.getUserId());
        return new BaseResponse<>(questionDetailRes);

    }

    //qna 질문 좋아요
    @PostMapping("/like")
    public BaseResponse<Long> checkQnaLike(@RequestParam Long qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.checkQnaLike(qnaBoardId, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //qna 질문 싫어요
    @PostMapping("/hate")
    public BaseResponse<Long> checkQnaHate(@RequestParam Long qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.checkQnaHate(qnaBoardId, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //qna 스크랩
    @PostMapping("/scrap")
    public BaseResponse<Long> checkScrap(@RequestParam Long qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.checkQnaScrap(qnaBoardId, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //qna 검색
    @GetMapping("/search")
    public BaseResponse<List<GetQnaListRes>> getQnaSearch(GetQnaSearchReq getQnaSearchReq) {
        List<GetQnaListRes> qnaListRes = basicQnaSearchService.getQnaSearch(getQnaSearchReq);
        return new BaseResponse<>(qnaListRes);

    }

}
