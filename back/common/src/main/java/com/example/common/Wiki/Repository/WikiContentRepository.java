package com.example.common.Wiki.Repository;

import com.example.common.Wiki.Model.Entity.WikiContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WikiContentRepository extends JpaRepository<WikiContent, Long> {

    Optional<WikiContent> findByWikiIdAndVersion(Long wikiId, Integer version);
    Page<WikiContent> findAllByWikiId(Long wikiId, Pageable pageable);
//    Page<WikiContent> findByUserId(Long id, Pageable pageable);
//    Page<WikiContent> findByWikiScrapListUserId(Long id, Pageable pageable);

    @Query("SELECT wc FROM WikiContent wc " +
            "JOIN FETCH wc.wiki w " +
            "JOIN FETCH w.category c " +
            "WHERE wc.user.id = :userId")
    Page<WikiContent> findByUserIdWithFetch(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT DISTINCT wc FROM WikiContent wc " +
            "JOIN FETCH wc.wiki w " +
            "JOIN FETCH w.category c " +
            "JOIN wc.wikiScrapList wsl " +
            "WHERE wsl.user.id = :userId")
    Page<WikiContent> findByWikiScrapListUserIdWithWikiAndCategory(@Param("userId") Long userId, Pageable pageable);
}
