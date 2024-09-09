package org.example.backend.Wiki.Repository;

import org.example.backend.Wiki.Model.Entity.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WikiRepository extends JpaRepository<Wiki, Long> {
    Optional<Wiki> findByTitle(String title);
}
