package com.example.common.Qna.Repository;

import com.example.common.Qna.model.Entity.QnaScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnaScrapRepository extends JpaRepository<QnaScrap, Long> {
    Optional<QnaScrap> findByQnaBoardEnableTrueAndQnaBoardIdAndUserId(Long qnaBoardId, Long userId);

}
