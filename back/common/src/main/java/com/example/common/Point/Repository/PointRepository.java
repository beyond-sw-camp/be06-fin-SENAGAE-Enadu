package com.example.common.Point.Repository;

import com.example.common.Point.Model.Entity.PointDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointDetail, Long> {
    Page<PointDetail> findAllByUserId(Long userId, Pageable pageable);
}
