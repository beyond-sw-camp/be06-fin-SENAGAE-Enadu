package org.example.backend.Wiki.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.File.Service.CloudFileUploadService;
import org.example.backend.Wiki.Model.Req.WikiRegisterReq;
import org.example.backend.Wiki.Model.Res.WikiRegisterRes;
import org.example.backend.Wiki.Service.WikiService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestPart MultipartFile thumbnail
    ) {
        //  ToDo : customUserDetails, 위키 등록 예외처리
//        if (req == null) {
//            throw new InvalidWikiException(Wiki_REGIST_FAIL);
//        }
        String thumbnailImgUrl = cloudFileUploadService.uploadImg(thumbnail); //thumbnail 경로를 thumbnailImgUrl 에 반환
        WikiRegisterRes wikiRegisterRes = wikiService.register(wikiRegisterReq, thumbnailImgUrl);
        return new BaseResponse<>(wikiRegisterRes);
    }
}
