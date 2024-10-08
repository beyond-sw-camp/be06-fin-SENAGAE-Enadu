package com.example.common.Qna.Repository;

import com.example.common.Qna.model.Entity.QnaBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaRepositoryCustom {
    public Page<QnaBoard> findByKeyword(String keyword, boolean useSuperCategory, Long categoryId, String type, Pageable pageable);
}