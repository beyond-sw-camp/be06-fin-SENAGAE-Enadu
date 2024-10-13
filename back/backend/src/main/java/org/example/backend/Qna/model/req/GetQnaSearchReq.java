package org.example.backend.Qna.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetQnaSearchReq {
    private String type = "tc";
    private String keyword;
    private Long categoryId;

    private String sort = "latest";
    private Integer page = 0;
    private Integer size = 12;
    private String resolved;
}