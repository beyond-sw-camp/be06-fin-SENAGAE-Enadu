package org.example.backend.Qna.model.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetQnaListReq {
    private String sort;
    private Integer page;
    private Integer size;
}
