package com.example.common.ErrorArchive.Repository;

import com.example.common.ErrorArchive.Model.Entity.ErrorArchive;
import com.example.common.ErrorArchive.Model.Entity.ErrorScrap;
import com.example.common.User.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ErrorScrapRepository extends JpaRepository<ErrorScrap, Long> {
    Optional<ErrorScrap> findByUserAndErrorArchive(User user, ErrorArchive errorArchive);




}
