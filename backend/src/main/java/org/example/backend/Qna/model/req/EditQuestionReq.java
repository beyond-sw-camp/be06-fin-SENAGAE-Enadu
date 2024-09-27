package org.example.backend.Qna.model.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditQuestionReq {
    private Long id;
    private String title;
    private String content;
    @NotEmpty
    private Long categoryId;
}

