package org.example.backend.ErrorArchive.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ErrorArchiveRes {
    private Long id;
    private String nickname;
    private LocalDate createdAt;
    private String title;
    private String contents;
    private int likeCnt;
    private int hateCnt;
    private String superCategory;
    private String subCategory;
    private String profileImg;
    private String grade;
}
