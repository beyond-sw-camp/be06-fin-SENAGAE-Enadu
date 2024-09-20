package org.example.backend.ErrorArchive.Repository;

import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Entity.ErrorScrap;
import org.example.backend.User.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ErrorScrapRepository extends JpaRepository<ErrorScrap, Long> {
    Optional<ErrorScrap> findByUserAndErrorArchive(User user, ErrorArchive errorArchive);

    @Query("SELECT qs FROM ErrorScrap qs WHERE qs.errorArchive.id = :errorarchiveId AND qs.user.id = :userId")
    Optional<ErrorScrap> findScrapByErrorArchiveIdAndUserId(@Param("errorarchiveId") Long errorarchiveId, @Param("userId") Long userId);


}
