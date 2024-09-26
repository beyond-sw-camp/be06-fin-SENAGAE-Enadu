package org.example.backend.Qna.model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetAnswerDetailListRes {
    private Long id;
    private String answer;
    private Integer likeCnt;
    private Integer hateCnt;
    private Boolean checkLikeOrHate;
    private Boolean checkAdopted;
    private String nickname;
    private String grade;
    private String profileImage;
    private String gradeImg;
    private LocalDateTime createdAt;
    private List<GetAnswerCommentDetailListRes> comments;
}
