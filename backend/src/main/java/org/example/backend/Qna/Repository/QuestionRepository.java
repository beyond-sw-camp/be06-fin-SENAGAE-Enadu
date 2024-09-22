package org.example.backend.Qna.Repository;

import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QnaBoard, Long> {
}