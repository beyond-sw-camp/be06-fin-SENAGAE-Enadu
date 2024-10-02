package org.example.backend.Point.Repository;

import org.example.backend.Point.Model.Entity.PointDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointDetail, Long> {
    Page<PointDetail> findAllByUserId(Long userId, Pageable pageable);
}
