package com.example.common.Wiki.Repository;

import com.example.common.Wiki.Model.Entity.WikiScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WikiScrapRepository extends JpaRepository<WikiScrap, Long> {
    Optional<WikiScrap> findByUserIdAndWikiContentId(Long userId, Long wikiContentId);
}
