package org.example.backend.ErrorArchive.Repository;

import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Entity.ErrorLike;
import org.example.backend.User.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ErrorLikeRepository extends JpaRepository<ErrorLike, Long> {
    Optional<ErrorLike> findByUserAndErrorArchiveAndState(User user, ErrorArchive errorArchive,Boolean isLike );

    @Query("SELECT ql FROM ErrorLike  ql WHERE ql.errorArchive.id = :errorarchiveId AND ql.user.id = :userId AND ql.state = :state")
    Optional<ErrorLike> findLikeByErrorArchiveIdAndUserIdAndState(@Param("errorarchiveId")Long errorarchiveId, @Param("userId") Long userId, @Param("state")Boolean state);






}
