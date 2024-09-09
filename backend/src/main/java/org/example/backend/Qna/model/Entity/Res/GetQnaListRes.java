package org.example.backend.Qna.model.Entity.Res;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetQnaListRes {
        private Long id;
        private String title;
        private String superCategory;
        private String subCategory;
        private String nickname;
        private String profileImage;
        private String grade;
        private Integer likeCnt;
        private Integer answerCnt;
        private LocalDateTime createdAt;
}
