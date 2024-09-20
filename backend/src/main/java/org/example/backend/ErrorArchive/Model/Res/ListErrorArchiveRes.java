package org.example.backend.ErrorArchive.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ListErrorArchiveRes {
    private Long id;
    private String title;
    private String content;
    private String superCategory;
    private String subCategory;
    private int likeCnt;
    private String grade;
    private String createdAt;
    private String nickname;
    private String profileImg;
    private Integer totalPage;
}
