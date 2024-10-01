package org.example.backend.Qna.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Qna.Service.BasicQnaSearchService;
import org.example.backend.Qna.Service.QnaService;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.Res.GetQuestionDetailRes;
import org.example.backend.Qna.model.Res.GetQuestionStateRes;
import org.example.backend.Qna.model.req.CreateQuestionReq;
import org.example.backend.Qna.model.req.EditQuestionReq;
import org.example.backend.Qna.model.req.GetQnaListReq;
import org.example.backend.Qna.model.req.GetQnaSearchReq;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Long userId;
        if (customUserDetails != null) {
            userId = customUserDetails.getUserId();
        }
        else {
            userId = null;
        }
        GetQuestionDetailRes questionDetailRes = qnaService.getQuestionDetail(qnaBoardId, userId);

        return new BaseResponse<>(questionDetailRes);
    }

    //qna 질문 좋아요
    @PostMapping("/like")
    public BaseResponse<Long> checkQnaLike(@RequestBody Map<String,Long> qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.checkQnaLike(qnaBoardId.get("qnaBoardId"), customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //qna 질문 싫어요
    @PostMapping("/hate")
    public BaseResponse<Long> checkQnaHate(@RequestBody Map<String,Long> qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.checkQnaHate(qnaBoardId.get("qnaBoardId"), customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    //qna 스크랩
    @PostMapping("/scrap")
    public BaseResponse<Long> checkScrap(@RequestBody Map<String,Long> qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.checkQnaScrap(qnaBoardId.get("qnaBoardId"), customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    @GetMapping("/state")
    public BaseResponse<GetQuestionStateRes> getQuestionState(Long qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        GetQuestionStateRes qnaStateRes = qnaService.getQuestionState(qnaBoardId, customUserDetails.getUserId());
        return new BaseResponse<>(qnaStateRes);
    }

    //qna 검색
    @GetMapping("/search")
    public BaseResponse<List<GetQnaListRes>> getQnaSearch(GetQnaSearchReq getQnaSearchReq) {
        List<GetQnaListRes> qnaListRes = basicQnaSearchService.getQnaSearch(getQnaSearchReq);
        return new BaseResponse<>(qnaListRes);
    }

    @PatchMapping()
    public BaseResponse<Long> editQuestion(@RequestBody EditQuestionReq editQuestionReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.editQuestion(editQuestionReq, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    @PatchMapping("/removal")
    public BaseResponse<Long> disableQuestion(@RequestBody Map<String,Long> qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.disableQuestion(qnaBoardId.get("qnaBoardId"), customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }
}
