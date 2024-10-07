package org.example.backend.Wiki.Repository;

import org.example.backend.Wiki.Model.Entity.LatestWiki;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LatestWikiRepository extends JpaRepository<LatestWiki, Long> {
}
