package org.example.backend.Wiki.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class WikiListRes {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String thumbnail;
    private LocalDateTime createdAt;
    private Integer totalPages;
}