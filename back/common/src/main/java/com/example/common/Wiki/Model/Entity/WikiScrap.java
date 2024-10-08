package com.example.common.Wiki.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.common.User.Model.Entity.User;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WikiScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //유저 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wiki_content_id")
    private WikiContent wikiContent;

}
