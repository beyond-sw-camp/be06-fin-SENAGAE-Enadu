package org.example.backend.Point.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetRankingRes {
    private Integer point;
    private Integer rank;
    private String nickname;
    private String profileImg;
    private String grade;
    private Integer totalPage;
}
