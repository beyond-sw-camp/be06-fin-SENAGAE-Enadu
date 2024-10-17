package org.example.backend.Qna.Controller;

import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Qna.Service.QnaSearchService;
import org.example.backend.Qna.Service.QnaService;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.Res.GetQuestionDetailRes;
import org.example.backend.Qna.model.Res.GetQuestionEditDetailRes;
import org.example.backend.Qna.model.Res.GetQuestionStateRes;
import org.example.backend.Qna.model.req.CreateQuestionReq;
import org.example.backend.Qna.model.req.EditQuestionReq;
import org.example.backend.Qna.model.req.GetQnaListReq;
import org.example.backend.Qna.model.req.GetQnaSearchReq;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qna")
public class QuestionController {
    private final QnaService qnaService;
    private final QnaSearchService qnaSearchService;

    public QuestionController(QnaService qnaService, @Qualifier("QnaElasticSearch") QnaSearchService qnaSearchService) {
        this.qnaService = qnaService;
        this.qnaSearchService = qnaSearchService;
    }

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
        } else {
            userId = null;
        }
        GetQuestionDetailRes questionDetailRes = qnaService.getQuestionDetail(qnaBoardId, userId);

        return new BaseResponse<>(questionDetailRes);
    }

    @GetMapping("/edit-detail")
    public BaseResponse<GetQuestionEditDetailRes> getQnaEditDetail(Integer qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId;
        if (customUserDetails != null) {
            userId = customUserDetails.getUserId();
        } else {
            userId = null;
        }
        GetQuestionEditDetailRes questionEditDetailRes = qnaService.getQuestionEditDetail(qnaBoardId, userId);

        return new BaseResponse<>(questionEditDetailRes);
    }

    //qna 질문 좋아요
    @PostMapping("/like")
    public BaseResponse<GetQuestionStateRes> checkQnaLike(@RequestBody Map<String, Long> qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        GetQuestionStateRes qnaStateRes = qnaService.checkQnaLike(qnaBoardId.get("qnaBoardId"), customUserDetails.getUserId());
        return new BaseResponse<>(qnaStateRes);
    }

    //qna 질문 싫어요
    @PostMapping("/hate")
    public BaseResponse<GetQuestionStateRes> checkQnaHate(@RequestBody Map<String, Long> qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        GetQuestionStateRes qnaStateRes = qnaService.checkQnaHate(qnaBoardId.get("qnaBoardId"), customUserDetails.getUserId());
        return new BaseResponse<>(qnaStateRes);
    }

    //qna 스크랩
    @PostMapping("/scrap")
    public BaseResponse<GetQuestionStateRes> checkScrap(@RequestBody Map<String, Long> qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        GetQuestionStateRes qnaStateRes = qnaService.checkQnaScrap(qnaBoardId.get("qnaBoardId"), customUserDetails.getUserId());
        return new BaseResponse<>(qnaStateRes);
    }

    @GetMapping("/state")
    public BaseResponse<GetQuestionStateRes> getQuestionState(Long qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        GetQuestionStateRes qnaStateRes = qnaService.getQuestionState(qnaBoardId, customUserDetails.getUserId());
        return new BaseResponse<>(qnaStateRes);
    }

    //qna 검색
    @GetMapping("/search")
    public BaseResponse<List<GetQnaListRes>> getQnaSearchDeprecated(GetQnaSearchReq getQnaSearchReq) {
        try {
            return new BaseResponse<>(qnaSearchService.getQnaSearch(getQnaSearchReq));
        } catch (IOException e) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_FAIL);
        }
    }

    @PatchMapping()
    public BaseResponse<Long> editQuestion(@RequestBody EditQuestionReq editQuestionReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.editQuestion(editQuestionReq, customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }

    @PatchMapping("/removal")
    public BaseResponse<Long> disableQuestion(@RequestBody Map<String, Long> qnaBoardId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = qnaService.disableQuestion(qnaBoardId.get("qnaBoardId"), customUserDetails.getUserId());
        return new BaseResponse<>(id);
    }
}
