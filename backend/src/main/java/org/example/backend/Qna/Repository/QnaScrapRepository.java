package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.QnaScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QnaScrapRepository extends JpaRepository<QnaScrap, Long> {
    Optional<QnaScrap> findByQnaBoard_EnableTrueAndQnaBoard_IdAndUser_Id(Long qnaBoardId, Long userId);

}
