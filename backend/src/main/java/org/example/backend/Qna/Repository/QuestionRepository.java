package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.User.Model.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface QuestionRepository extends JpaRepository<QnaBoard, Long> {
    Page<QnaBoard> findByUserId(Long userId, Pageable pageable);
    Page<QnaBoard> findByUser_AnswerList_User_Id(Long id, Pageable pageable);
    Page<QnaBoard> findByQnaScrapList_User_Id(Long id, Pageable pageable);
}