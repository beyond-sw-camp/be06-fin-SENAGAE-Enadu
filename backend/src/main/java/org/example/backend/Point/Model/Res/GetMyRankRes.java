package org.example.backend.Point.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMyRankRes {
    private Integer point;
    private Integer rank;
}
