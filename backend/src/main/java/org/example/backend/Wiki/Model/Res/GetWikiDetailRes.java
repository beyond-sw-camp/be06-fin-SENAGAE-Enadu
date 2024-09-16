package org.example.backend.Wiki.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetWikiDetailRes {

    private Long id;
    private String title;
    private String content;
    private String category;
    private Integer version;
    private Boolean checkScrap;
    private String userGrade;

}
