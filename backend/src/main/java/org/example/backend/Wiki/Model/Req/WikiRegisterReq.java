package org.example.backend.Wiki.Model.Req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
@Getter
@Setter
public class WikiRegisterReq {

    @NotNull
    private String title;
    @NotNull
    private Long categoryId;
    @NotNull
    private String content;

}
