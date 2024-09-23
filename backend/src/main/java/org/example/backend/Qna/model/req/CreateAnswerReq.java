package org.example.backend.Qna.model.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreateAnswerReq {
    private Long qnaBoardId;
    private String content;
}
