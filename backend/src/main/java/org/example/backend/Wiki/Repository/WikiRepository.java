package org.example.backend.Wiki.Repository;

import org.example.backend.Wiki.Model.Entity.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiRepository extends JpaRepository<Wiki, Long> {
}
