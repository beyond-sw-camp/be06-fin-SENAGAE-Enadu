package com.example.common.Ranking.Repository;

import com.example.common.Ranking.model.Entity.DailyRanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRankingRepository extends JpaRepository<DailyRanking, Long> {
    DailyRanking findByUserId(Long id);
}