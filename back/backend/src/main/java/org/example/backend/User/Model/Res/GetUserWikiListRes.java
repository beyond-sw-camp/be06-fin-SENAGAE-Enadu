package org.example.backend.User.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetUserWikiListRes {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String thumbnail;
    private LocalDateTime createdAt;
    private Integer totalPage;
}
