package org.example.backend.Wiki.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.User.Model.Entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class WikiContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NonNull
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

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
