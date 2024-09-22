package org.example.backend.Qna.model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetQnaListRes {
    private Long id;
    private String title;
    private String superCategoryName;
    private String subCategoryName;
    private String nickname;
    private String profileImage;
    private String grade;
    private Integer likeCnt;
    private Integer answerCnt;
    private LocalDateTime createdAt;
}
