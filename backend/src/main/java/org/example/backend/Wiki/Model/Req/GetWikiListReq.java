package org.example.backend.Wiki.Model.Req;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetWikiListReq {

    private Integer page;
    private Integer size;
}