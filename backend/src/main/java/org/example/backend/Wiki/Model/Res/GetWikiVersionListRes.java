package org.example.backend.Wiki.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GetWikiVersionListRes {

    private Long wikiContentId;
    private Integer version;
    private LocalDateTime createdAt;
    private String nickname;
    private Integer totalPages;
}