package org.example.backend.Qna.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetQnaListReq {
    private String sort;
    private Integer page;
    private Integer size;
    private String resolved;
}
