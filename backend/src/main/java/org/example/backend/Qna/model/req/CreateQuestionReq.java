package org.example.backend.Qna.model.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreateQuestionReq {
    private String title;
    private String content;
    @NotEmpty
    private Long categoryId;
}

