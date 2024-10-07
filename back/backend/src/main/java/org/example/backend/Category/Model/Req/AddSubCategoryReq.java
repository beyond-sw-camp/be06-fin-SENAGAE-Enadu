package org.example.backend.Category.Model.Req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSubCategoryReq {
    private Long superCategoryId;
    private String categoryName;
}
