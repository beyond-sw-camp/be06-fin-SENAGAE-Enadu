package org.example.backend.Point.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPointRankRes {
    private Integer point;
    private Integer rank;
    private String nickname;
    private String profileImg;
    private String grade;
    private String gradeImg;
    private Integer totalPage;
}
