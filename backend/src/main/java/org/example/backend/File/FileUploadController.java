package org.example.backend.File;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final CloudFileUploadService cloudFileUploadService;

    @PostMapping("/img/upload")
    public BaseResponse<String> uploadImage(@RequestParam("imgFile") MultipartFile imgFile) throws IOException {
        String imgUrl = cloudFileUploadService.uploadImg(imgFile);
        return new BaseResponse<>(imgUrl);
    }

}
