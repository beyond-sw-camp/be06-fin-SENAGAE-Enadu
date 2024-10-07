package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.QnaBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaRepositoryCustom {
    public Page<QnaBoard> findByKeyword(String keyword, boolean useSuperCategory, Long categoryId, String type, Pageable pageable);
}