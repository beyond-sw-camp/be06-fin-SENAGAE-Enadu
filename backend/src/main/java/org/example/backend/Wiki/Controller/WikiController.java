package org.example.backend.Wiki.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidWikiException;
import org.example.backend.File.Service.CloudFileUploadService;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.Wiki.Model.Req.GetWikiListReq;
import org.example.backend.Wiki.Model.Req.WikiRegisterReq;
import org.example.backend.Wiki.Model.Res.WikiListRes;
import org.example.backend.Wiki.Model.Res.WikiRegisterRes;
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
    public BaseResponse<List<WikiListRes>> list(GetWikiListReq getWikiListReq) {
        if (getWikiListReq.getPage() == null) {
            getWikiListReq.setPage(0);
        }
        if (getWikiListReq.getSize() == null || getWikiListReq.getSize() == 0) {
            getWikiListReq.setSize(20);

        }
        List<WikiListRes> wikiList = wikiService.wikiList(getWikiListReq);
        return new BaseResponse<>(wikiList);
    }
}
