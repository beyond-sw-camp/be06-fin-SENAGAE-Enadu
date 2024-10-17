package org.example.backend.Qna.model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetQuestionEditDetailRes {
    private Long id;
    private Long userId;
    private Long superCategoryId;
    private Long subCategoryId;
    private String title;
    private String content;
    private String superCategoryName;
    private String subCategoryName;
}
