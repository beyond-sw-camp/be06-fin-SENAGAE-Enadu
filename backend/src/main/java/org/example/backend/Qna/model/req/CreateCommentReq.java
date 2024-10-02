package org.example.backend.Qna.model.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentReq {
    private Long answerId;
    private Long superCommentId;
    private String content;
}
