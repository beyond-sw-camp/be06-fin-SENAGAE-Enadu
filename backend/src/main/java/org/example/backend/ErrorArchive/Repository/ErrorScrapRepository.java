package org.example.backend.ErrorArchive.Repository;

import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Entity.ErrorScrap;
import org.example.backend.User.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ErrorScrapRepository extends JpaRepository<ErrorScrap, Long> {
    Optional<ErrorScrap> findByUserAndErrorArchive(User user, ErrorArchive errorArchive);

}
