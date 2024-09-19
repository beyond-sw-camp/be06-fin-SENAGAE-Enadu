package org.example.backend.Wiki.Model.Req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetWikiVersionListReq {

    private Long id;
    private Integer page;
    private Integer size;

}
