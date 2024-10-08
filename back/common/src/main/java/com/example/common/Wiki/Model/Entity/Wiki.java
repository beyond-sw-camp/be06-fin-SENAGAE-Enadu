package com.example.common.Wiki.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import com.example.common.Category.Model.Entity.Category;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Wiki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wiki")
    private List<WikiContent> wikiContentList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_wiki_id")
    private LatestWiki latestWiki;

    @Column(nullable = false)
    @NotNull
    private String title;

    public void updateLatestWiki(LatestWiki latestWiki) {
        this.latestWiki = latestWiki;
    }
}
