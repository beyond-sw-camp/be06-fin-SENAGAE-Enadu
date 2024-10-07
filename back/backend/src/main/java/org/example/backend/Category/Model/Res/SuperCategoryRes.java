package org.example.backend.Category.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SuperCategoryRes {
    private Long id;
    private String categoryName;
}
