package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.QnaBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QnaBoard, Long> {
    Optional<QnaBoard> findByIdAndEnableTrue(Long id);

    Page<QnaBoard> findByEnableTrue(Pageable pageable);


    // type이 tc일 때 검색 쿼리문
    @Query("SELECT q FROM QnaBoard q WHERE (q.title LIKE CONCAT('%', :keyword, '%') OR q.content LIKE CONCAT('%', :keyword, '%')) AND (:categoryId IS NULL OR q.category.id = :categoryId) AND q.enable = true")
    Page<QnaBoard> findByTC(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);

    // type이 t일 때 검색 쿼리문
    @Query("SELECT q FROM QnaBoard q WHERE q.title LIKE CONCAT('%', :keyword, '%') AND (:categoryId IS NULL OR q.category.id = :categoryId) AND q.enable = true")
    Page<QnaBoard> findByT(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);

    // type이 c일때 검색 쿼리문
    @Query("SELECT q FROM QnaBoard q WHERE q.content LIKE CONCAT('%', :keyword, '%') AND (:categoryId IS NULL OR q.category.id = :categoryId) AND q.enable = true")
    Page<QnaBoard> findByC(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);

    Page<QnaBoard> findByUserId(Long userId, Pageable pageable);

    Page<QnaBoard> findByUser_AnswerList_User_Id(Long id, Pageable pageable);

    Page<QnaBoard> findByQnaScrapList_User_Id(Long id, Pageable pageable);
}