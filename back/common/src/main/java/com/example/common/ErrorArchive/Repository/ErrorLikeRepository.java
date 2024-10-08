package com.example.common.ErrorArchive.Repository;

import com.example.common.ErrorArchive.Model.Entity.ErrorArchive;
import com.example.common.ErrorArchive.Model.Entity.ErrorLike;
import com.example.common.User.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ErrorLikeRepository extends JpaRepository<ErrorLike, Long> {
    Optional<ErrorLike> findByUserAndErrorArchiveAndState(User user, ErrorArchive errorArchive,Boolean isLike );

    Optional<ErrorLike> findByErrorArchiveAndUser(ErrorArchive errorArchive, User user);



}
