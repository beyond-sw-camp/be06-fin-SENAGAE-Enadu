package org.example.backend.Wiki.Repository;

import org.example.backend.Wiki.Model.Entity.WikiContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WikiContentRepository extends JpaRepository<WikiContent, Long> {

    Optional<WikiContent> findByWikiIdAndVersion(Long wikiId, Integer version);
}
