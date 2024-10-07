package org.example.backend.User.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GetUserQnaListRes {
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
    private int totalPage;
}
