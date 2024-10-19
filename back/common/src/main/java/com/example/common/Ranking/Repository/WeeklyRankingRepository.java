package com.example.common.Ranking.Repository;

import com.example.common.Ranking.model.Entity.WeeklyRanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyRankingRepository extends JpaRepository<WeeklyRanking, Long> {
    WeeklyRanking findByUserId(Long id);
}