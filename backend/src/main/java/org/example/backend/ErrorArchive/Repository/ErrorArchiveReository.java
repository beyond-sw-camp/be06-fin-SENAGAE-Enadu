package org.example.backend.ErrorArchive.Repository;

import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Entity.ErrorLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ErrorArchiveReository extends JpaRepository<ErrorArchive, Long> {
    // ErrorArchive 엔터티를 데이터베이스에서 categoryName
    // 어떤 엔터티를 쓸지, id의 타입
    @Query("SELECT el FROM ErrorLike el WHERE el.errorArchive.id = :errorArchiveId AND el.user.id = :userId AND el.state = :state")
    Optional<ErrorLike> findLikeByErrorArchiveIdAndUserIdAndState(
            @Param("errorArchiveId") Long errorArchiveId,
            @Param("userId") Long userId,
            @Param("state") boolean state);




}
