package org.example.backend.ErrorArchive.Model.Req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class RegisterErrorArchiveReq {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Long categoryId;

}
