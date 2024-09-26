package org.example.backend.Qna.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditAnswerReq {
    private Long id;
    private String content;
}
