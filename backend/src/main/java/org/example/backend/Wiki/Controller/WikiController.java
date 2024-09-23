package org.example.backend.Wiki.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidWikiException;
import org.example.backend.File.Service.CloudFileUploadService;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.Wiki.Model.Req.*;
import org.example.backend.Wiki.Model.Res.*;
import org.example.backend.Wiki.Service.WikiService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/wiki")
public class WikiController {
    private final WikiService wikiService;
    private final CloudFileUploadService cloudFileUploadService;

    // 위키 등록
    @PostMapping
    public BaseResponse<WikiRegisterRes> register(
            @RequestPart WikiRegisterReq wikiRegisterReq,
            @RequestPart(required = false) MultipartFile thumbnail,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (wikiRegisterReq.getTitle().isEmpty()) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_TITLE_REGIST_FAIL);
        }
        if (wikiRegisterReq.getCategoryId() == null) {
            throw new InvalidWikiException(BaseResponseStatus.CATEGORY_NOT_FOUND_CATEGORY);
        }
        if (wikiRegisterReq.getContent().isEmpty()) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_CONTENT_REGIST_FAIL);
        }
        // 썸네일 등록 확인 로직
        String thumbnailUrl;
        if (thumbnail == null || thumbnail.isEmpty()) {
            thumbnailUrl = cloudFileUploadService.getBasicThumbnailUrl();
        } else {
            thumbnailUrl = cloudFileUploadService.uploadImg(thumbnail);
        }

        return new BaseResponse<>(wikiService.register(wikiRegisterReq, thumbnailUrl, customUserDetails));
    }

    // 위키 목록 조회
    @GetMapping("/list")
    public BaseResponse<List<WikiListRes>> list(Integer page,Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null || size == 0) {
            size =20;
        }
        List<WikiListRes> wikiList = wikiService.wikiList(page,size);
        return new BaseResponse<>(wikiList);
    }

    // 위키 상세 조회
    @GetMapping("/detail")
    public BaseResponse<GetWikiDetailRes> detail(Long id,
                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = null;
        String userGrade = "GUEST";

        if (customUserDetails != null) {
            userId = customUserDetails.getUserId();
            userGrade = customUserDetails.getGrade();
        }

        return new BaseResponse<>(wikiService.detail(id, userId));
    }

    // 위키 수정
    @PatchMapping
    public BaseResponse<GetWikiUpdateRes> update(@RequestPart GetWikiUpdateReq getWikiUpdateReq,
                                                 @RequestPart(required = false) MultipartFile thumbnail,
                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // 썸네일 등록 확인 로직
        String thumbnailUrl;
        if (thumbnail == null || thumbnail.isEmpty()) {
            thumbnailUrl = null;
        } else {
            thumbnailUrl = cloudFileUploadService.uploadImg(thumbnail);
        }

        return new BaseResponse<>(wikiService.update(getWikiUpdateReq, thumbnailUrl, customUserDetails.getUserId()));

    }

    // 위키 이전버전 상세 조회
    @GetMapping("/version/detail")
    public BaseResponse<GetWikiVersionDetailRes> versionDetail(Long wikiContentId,
                                                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = (customUserDetails != null) ? customUserDetails.getUserId() : null;

        return new BaseResponse<>(wikiService.versionDetail(wikiContentId, userId));
    }
    // 위키 (이전버전) 목록 조회
    @GetMapping("/version/list")
    public BaseResponse<List<GetWikiVersionListRes>> versionList(GetWikiVersionListReq getWikiVersionListReq) {
        if (getWikiVersionListReq.getPage() == null) {
            getWikiVersionListReq.setPage(0);
        }
        if (getWikiVersionListReq.getSize() == null || getWikiVersionListReq.getSize() == 0) {
            getWikiVersionListReq.setSize(20);
        }
        List<GetWikiVersionListRes> wikiVersionList = wikiService.versionList(getWikiVersionListReq);
        return new BaseResponse<>(wikiVersionList);
    }

    // 위키 스크랩
    @PostMapping("/scrap")
    public BaseResponse<WikiScrapRes> scrap(WikiScrapReq wikiScrapReq,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return new BaseResponse<>(wikiService.scrap(wikiScrapReq, customUserDetails.getUserId()));
    }
}

