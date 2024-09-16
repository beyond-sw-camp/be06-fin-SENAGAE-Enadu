package org.example.backend.ErrorArchive.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GetErrorArchiveDetailRes {
    private Long id;
    private String nickname;
    private String title;
    private String content;
    private String superCategory;
    private String subCategory;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Integer likeCnt;
    private Integer hateCnt;
    private Boolean checkLike;
    private Boolean checkHate;
    private Boolean checkScrap;
    private String profileImg;
    private String grade;
    private String gradeImg;
}
