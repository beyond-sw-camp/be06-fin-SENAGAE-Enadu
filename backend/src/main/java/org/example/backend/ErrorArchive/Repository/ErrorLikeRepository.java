package org.example.backend.ErrorArchive.Repository;

import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Entity.ErrorLike;
import org.example.backend.User.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ErrorLikeRepository extends JpaRepository<ErrorLike, Long> {
    Optional<ErrorLike> findByUserAndErrorArchiveAndState(User user, ErrorArchive errorArchive,Boolean isLike );
}
