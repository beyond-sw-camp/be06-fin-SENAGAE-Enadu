package org.example.backend.Wiki.Repository;

import org.example.backend.Wiki.Model.Entity.WikiContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiContentRepository extends JpaRepository<WikiContent, Long> {
}
