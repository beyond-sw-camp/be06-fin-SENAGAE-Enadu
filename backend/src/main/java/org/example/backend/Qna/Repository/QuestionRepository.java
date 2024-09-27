package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.QnaBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<QnaBoard, Long> {
    // type이 tc일 때 검색 쿼리문
    @Query("SELECT q FROM QnaBoard q WHERE (q.title LIKE CONCAT('%', :keyword, '%') OR q.content LIKE CONCAT('%', :keyword, '%')) AND :categoryId IS NULL OR q.category.id = :categoryId")
    Page<QnaBoard> findByKeywordAndCategoryId_TC(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);

    // type이 t일 때 검색 쿼리문
    @Query("SELECT q FROM QnaBoard q WHERE q.title LIKE CONCAT('%', :keyword, '%') AND :categoryId IS NULL OR q.category.id = :categoryId")
    Page<QnaBoard> findByKeywordAndCategoryId_T(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);

    // type이 c일때 검색 쿼리문
    @Query("SELECT q FROM QnaBoard q WHERE q.content LIKE CONCAT('%', :keyword, '%') AND :categoryId IS NULL OR q.category.id = :categoryId")
    Page<QnaBoard> findByKeywordAndCategoryId_C(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);

    Page<QnaBoard> findByUserIdAndEnableTrue(Long userId, Pageable pageable);
    Page<QnaBoard> findByUserAnswerListUserIdAndEnableTrue(Long id, Pageable pageable);
    Page<QnaBoard> findByQnaScrapListUserIdAndEnableTrue(Long id, Pageable pageable);
}