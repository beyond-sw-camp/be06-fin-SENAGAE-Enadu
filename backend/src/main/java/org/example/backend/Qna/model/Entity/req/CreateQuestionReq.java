package org.example.backend.Qna.model.Entity.req;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private Long categoryId;
}

