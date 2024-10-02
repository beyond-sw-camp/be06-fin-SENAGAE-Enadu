package org.example.backend.Wiki.Model.Req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetWikiUpdateReq {

    private Long id;
    private String content;
}
