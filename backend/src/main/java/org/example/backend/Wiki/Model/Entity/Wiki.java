package org.example.backend.Wiki.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Wiki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wiki")
    private List<WikiContent> wikiContentList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_wiki_id")
    private LatestWiki latestWiki;

    @Column(nullable = false)
    private String title;
}
