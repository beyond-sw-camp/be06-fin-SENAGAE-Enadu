package org.example.backend.Qna.model.Entity.Res;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetQuestionDetailRes {
    private String title;
    private String content;
    private String superCategory;
    private String subCategory;
    private Integer likeCnt;
    private Integer hateCnt;
    private boolean checkLike;
    private boolean checkHate;
    private boolean checkScrap;
    private String nickname;
    private String grade;
    private String profileImage;
    private String gradeImg;
    private LocalDateTime createdAt;
    private List<GetAnswerDetailListRes> answers;
}
