package com.example.common.Wiki.Repository;

import com.example.common.Wiki.Model.Entity.LatestWiki;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LatestWikiRepository extends JpaRepository<LatestWiki, Long> {
}
