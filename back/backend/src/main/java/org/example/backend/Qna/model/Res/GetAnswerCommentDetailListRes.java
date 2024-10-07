package org.example.backend.Qna.model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetAnswerCommentDetailListRes {
    private Long id;
    private Long userId;
    private Long superCommentId;
    private String answerComment;
    private String nickname;
    private String grade;
    private String profileImage;
    private String gradeImg;
    private LocalDateTime createdAt;
}
