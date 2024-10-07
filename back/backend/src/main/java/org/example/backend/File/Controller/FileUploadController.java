package org.example.backend.File.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.File.Service.CloudFileUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final CloudFileUploadService cloudFileUploadService;

    @PostMapping("/img/upload")
    public BaseResponse<String> uploadImage(MultipartFile imgFile) {
        String imgUrl = cloudFileUploadService.uploadImg(imgFile);
        return new BaseResponse<>(imgUrl);
    }

}
