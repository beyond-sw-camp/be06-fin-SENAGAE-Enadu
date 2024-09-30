package org.example.backend.Qna.model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetAnswerStateRes {
    private Long id;
    private Long userId;
    private Integer likeCnt;
    private Integer hateCnt;
    private Boolean checkLikeOrHate;
    private Boolean checkAdopted;
}
