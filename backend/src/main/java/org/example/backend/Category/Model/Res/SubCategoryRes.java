package org.example.backend.Category.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubCategoryRes {
    private Long id;
    private String categoryName;
}
