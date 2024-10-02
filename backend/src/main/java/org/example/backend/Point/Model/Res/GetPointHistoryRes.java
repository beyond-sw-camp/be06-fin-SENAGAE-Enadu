package org.example.backend.Point.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GetPointHistoryRes {
    private Integer point;
    private Boolean state; // true이면 적립, false 이면 차감
    private String description; // 적립 이유
    private LocalDateTime createdAt;
    private Integer totalPage;

}
