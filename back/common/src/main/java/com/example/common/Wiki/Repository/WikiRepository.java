package com.example.common.Wiki.Repository;

import com.example.common.Wiki.Model.Entity.Wiki;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WikiRepository extends JpaRepository<Wiki, Long> {

    Optional<Wiki> findByTitle(String title);

    Page<Wiki> findAllByCategoryId(Long categoryId, Pageable pageable); // 카테고리 검색

    // keyword만 검색
    Page<Wiki> findAllByTitleIsContainingIgnoreCase(String keyword, Pageable pageable); // t타입 검색

    Page<Wiki> findAllByLatestWikiContentIsContainingIgnoreCase(String keyword, Pageable pageable); // c타입 검색

    @Query("SELECT w FROM Wiki w " +
            "JOIN w.latestWiki lw " +
            "WHERE LOWER(w.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(lw.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    @EntityGraph(attributePaths = {"latestWiki", "category"})
    Page<Wiki> findAllByKeyword(String keyword, Pageable pageable); // tc타입 검색

    // keyword + category 검색
    Page<Wiki> findAllByTitleIsContainingIgnoreCaseAndCategoryId(String keyword, Long categoryId, Pageable pageable); // t타입

    Page<Wiki> findAllByLatestWikiContentIsContainingIgnoreCaseAndCategoryId(String keyword, Long categoryId, Pageable pageable); // c타입

    @Query("SELECT w FROM Wiki w " +
            "JOIN w.latestWiki lw " +
            "WHERE (LOWER(w.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(lw.content) LIKE LOWER(CONCAT('%', :keyword, '%')))" + "AND w.category.id = :categoryId")
    Page<Wiki> findAllByKeywordAndCategory(String keyword, Long categoryId, Pageable pageable);
}
