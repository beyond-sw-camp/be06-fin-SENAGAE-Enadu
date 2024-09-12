package org.example.backend.Qna.model.Res;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAnswerCommentDetailListRes {
    private Long superCommentId;
    private String answerComment;
    private String nickname;
    private String grade;
    private String profileImage;
    private String gradeImg;
    private LocalDateTime createdAt;
}
