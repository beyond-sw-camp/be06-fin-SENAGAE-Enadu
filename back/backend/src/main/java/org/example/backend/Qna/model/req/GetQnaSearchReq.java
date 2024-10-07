package org.example.backend.Qna.model.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class GetQnaSearchReq {
    private String type;
    private String keyword;
    private Long categoryId;

    private String sort;
    private Integer page;
    private Integer size;
}