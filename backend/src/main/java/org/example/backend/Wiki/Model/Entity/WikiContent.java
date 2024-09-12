package org.example.backend.Wiki.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.Qna.model.Entity.QnaScrap;
import org.example.backend.User.Model.Entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WikiContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NonNull
    private String content;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false, length = 255)
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wiki_id")
    private Wiki wiki;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wikiContent", fetch = FetchType.LAZY)
    private List<WikiScrap> wikiScrapList;
}
