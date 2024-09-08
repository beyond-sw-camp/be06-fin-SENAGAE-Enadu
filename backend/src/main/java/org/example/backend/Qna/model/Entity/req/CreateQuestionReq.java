package org.example.backend.Qna.model.Entity.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionReq {
    private String title;
    private String content;
    private Long categoryId;
}

