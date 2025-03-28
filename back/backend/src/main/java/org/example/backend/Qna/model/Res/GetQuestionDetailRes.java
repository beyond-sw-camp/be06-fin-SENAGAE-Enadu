package org.example.backend.Qna.model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetQuestionDetailRes {
    private Long id;
    private Long userId;
    private Long superCategoryId;
    private Long subCategoryId;
    private String title;
    private String content;
    private String superCategoryName;
    private String subCategoryName;
    private Integer likeCnt;
    private Integer hateCnt;
    private Integer answerCnt;
    private Boolean checkLikeOrHate;
    private Boolean checkScrap;
    private String nickname;
    private String grade;
    private String profileImage;
    private String gradeImg;
    private LocalDateTime createdAt;
    private List<GetAnswerDetailListRes> answers;
}
