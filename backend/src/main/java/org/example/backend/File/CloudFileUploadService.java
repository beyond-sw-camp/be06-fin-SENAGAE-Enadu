package org.example.backend.File;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudFileUploadService {
    private final AmazonS3 s3client;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg", "gif", "webp", "bmp", "svg");
    private static final Long MAX_IMG_FILE_SIZE = 20 * 1024 * 1024L;

    private String makeFolder() {
        return "IMAGE/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    private Boolean validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) { // 빈 파일인지 검사
            return false;
        }

        if (file.getSize() > MAX_IMG_FILE_SIZE) { // 업로드한 파일의 크기 검사
            return false;
        }

        String fileContentType = file.getContentType();
        if (fileContentType == null || !fileContentType.startsWith("image/")) { // 파일의 MIME 타입을 검사
            return false;
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) { // 파일의 확장자가 유효한 확장자인지 검사
            return false;
        }

        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return false;
        }
        String fileExtension = fileName.substring(dotIndex + 1);
        return IMAGE_EXTENSIONS.contains(fileExtension);

    }

    public String uploadImg(MultipartFile file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        if (!validateFile(file)) { // todo 예외처리
            return "";
        }
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        String uploadPath = makeFolder();
        String fileName = uploadPath + "/" + UUID.randomUUID();
        amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }
}
