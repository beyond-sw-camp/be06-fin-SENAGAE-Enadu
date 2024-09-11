package org.example.backend.ErrorArchive.Repository;

import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorArchiveReository extends JpaRepository<ErrorArchive, Long> {
    // ErrorArchive 엔터티를 데이터베이스에서 categoryName
    // 어떤 엔터티를 쓸지, id의 타입


}
