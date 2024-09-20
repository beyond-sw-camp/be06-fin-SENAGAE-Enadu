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
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND);
        }
        if(customUserDetails.getUserId() == null){
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
        return new BaseResponse<>(errorArchiveService.detail(getErrorArchiveDetailReq, customUserDetails));
    }

    // 좋아요 체크 및 토글
    @GetMapping("/like")
    public BaseResponse<Long> checkLike(Long errorarchiveId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
        Long id = errorArchiveService.toggleErrorArchiveLikeOrHate(errorarchiveId, customUserDetails.getUserId(), true);
        return new BaseResponse<>(id);
    }

    // 싫어요 체크 및 토글
    @GetMapping("/hate")
    public BaseResponse<Long> checkHate(Long errorarchiveId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
        Long id = errorArchiveService.toggleErrorArchiveLikeOrHate(errorarchiveId, customUserDetails.getUserId(), false);
        return new BaseResponse<>(id);
    }

    // 아카이브 스크랩
    @GetMapping("/scrap")
    public BaseResponse<Boolean> checkScrap(Long errorarchiveId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Boolean isScrapped = errorArchiveService.checkErrorArchiveScrap(errorarchiveId, customUserDetails.getUserId());
        return new BaseResponse<>(isScrapped);
    }
}





