package org.example.backend.ErrorArchive.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetErrorArchiveEditDetailRes {
    private Long id;
    private Long userId;
    private Long superCategoryId;
    private Long subCategoryId;
    private String title;
    private String content;
    private String superCategoryName;
    private String subCategoryName;
}
