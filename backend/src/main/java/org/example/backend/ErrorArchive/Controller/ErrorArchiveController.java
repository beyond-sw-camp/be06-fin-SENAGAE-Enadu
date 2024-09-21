package org.example.backend.ErrorArchive.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.ErrorArchive.Model.Req.GetErrorArchiveDetailReq;
import org.example.backend.ErrorArchive.Model.Req.ListErrorArchiveReq;
import org.example.backend.ErrorArchive.Model.Req.RegisterErrorArchiveReq;
import org.example.backend.ErrorArchive.Model.Res.GetErrorArchiveDetailRes;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.ErrorArchive.Model.Res.RegisterErrorArchiveRes;
import org.example.backend.ErrorArchive.Service.ErrorArchiveService;
import org.example.backend.Exception.custom.InvalidErrorBoardException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/errorarchive")
@RequiredArgsConstructor
public class ErrorArchiveController {

    private final ErrorArchiveService errorArchiveService;

    // 아카이브 등록
    @PostMapping()
    public BaseResponse<RegisterErrorArchiveRes> register(
            @RequestBody RegisterErrorArchiveReq registerErrorArchiveReq,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        RegisterErrorArchiveRes registerErrorArchiveRes = errorArchiveService.register(registerErrorArchiveReq, customUserDetails);
        return new BaseResponse<>(registerErrorArchiveRes);

    }

    // 아카이브 목록 조회
    @GetMapping("/list")
    public BaseResponse<List<ListErrorArchiveRes>> list(ListErrorArchiveReq listErrorArchiveReq) {
        if (listErrorArchiveReq.getPage() == null) {
            listErrorArchiveReq.setPage(0);
        }
        if (listErrorArchiveReq.getSize() == null || listErrorArchiveReq.getSize() == 0) {
            listErrorArchiveReq.setSize(20);
        }
        List<ListErrorArchiveRes> errorArchiveList = errorArchiveService.errorArchiveList(listErrorArchiveReq);
        return new BaseResponse<>(errorArchiveList);
    }

    // 아카이브 상세 조회
    @GetMapping("/detail")
    public BaseResponse<GetErrorArchiveDetailRes> detail(GetErrorArchiveDetailReq getErrorArchiveDetailReq,
                                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(getErrorArchiveDetailReq.getId() == null){
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND_DETAIL);
        }
        if(customUserDetails.getUserId() == null){
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
        return new BaseResponse<>(errorArchiveService.detail(getErrorArchiveDetailReq, customUserDetails));
    }

    // 좋아요 싫어요 토글
    @PostMapping("/like")
    public BaseResponse<Boolean> toggleLikeOrHate(@RequestParam Long errorarchiveId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam boolean isLike) {
        if(errorarchiveId == null){
            return new BaseResponse<>(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND_DETAIL);
        }
        if(customUserDetails == null || customUserDetails.getUserId() == null) {
            return new BaseResponse<>(BaseResponseStatus.USER_NOT_FOUND);
        }
        BaseResponse<Boolean> result = errorArchiveService.toggleErrorArchiveLikeOrHate(errorarchiveId, customUserDetails.getUserId(), isLike);
        if(result == null){
            return new BaseResponse<>(true,"요청이 성공하였습니다.",1000, null);
        }
        return result;
    }

    // 아카이브 스크랩
    @PostMapping("/scrap")
    public BaseResponse<Boolean> checkScrap(Long errorarchiveId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Boolean isScrapped = errorArchiveService.checkErrorArchiveScrap(errorarchiveId, customUserDetails.getUserId());
        return new BaseResponse<>(isScrapped);
    }
}





