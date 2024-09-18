package org.example.backend.Wiki.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetWikiVersionDetailRes {

    private Long id;
    private String title;
    private String content;
    private String category;
    private Integer version;
    private Boolean checkScrap;
}
