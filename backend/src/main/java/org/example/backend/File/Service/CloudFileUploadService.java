package org.example.backend.File.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudFileUploadService {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg", "gif", "webp", "bmp", "svg");
    private static final Long MAX_IMG_FILE_SIZE = 10 * 1024 * 1024L;

    private String makeFolder() {
        return "IMAGE/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) { // 빈 파일인지 검사
            throw new InvalidFileException(BaseResponseStatus.EMPTY_FILE);
        }

        if (file.getSize() > MAX_IMG_FILE_SIZE) { // 업로드한 파일의 크기 검사
            throw new InvalidFileException(BaseResponseStatus.EXCEED_MAX_SIZE);
        }

        validateFileType(file);
    }

    private static void validateFileType(MultipartFile file) { // 파일 타입 검사
        String fileContentType = file.getContentType();
        if (fileContentType == null || !fileContentType.startsWith("image/")) { // 파일의 MIME 타입을 검사
            throw new InvalidFileException(BaseResponseStatus.INVALID_FILE_TYPE);
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) { // 파일의 확장자가 유효한 확장자인지 검사
            throw new InvalidFileException(BaseResponseStatus.INVALID_FILE_TYPE);
        }

        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            throw new InvalidFileException(BaseResponseStatus.INVALID_FILE_TYPE);
        }
        String fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
        if (!IMAGE_EXTENSIONS.contains(fileExtension)) {
            throw new InvalidFileException(BaseResponseStatus.INVALID_FILE_TYPE);
        }

    }

    public String uploadImg(MultipartFile file) {
        validateFile(file);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        String uploadPath = makeFolder();
        String fileName = uploadPath + "/" + UUID.randomUUID();

        try {
            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new InvalidFileException(BaseResponseStatus.FAIL);
        }
        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    // 썸네일 기본이미지 반환
    private final static List<String> basicThumbnailList = Arrays.asList("https://jimmny.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/08/BasicImg3.png","https://jimmny.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/08/BasicImg2.png","https://jimmny.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/08/BasicImg1.png");


    public String getBasicThumbnailUrl() {
        int random = (int) (Math.random() * basicThumbnailList.size());
        return basicThumbnailList.get(random);
    }

}

