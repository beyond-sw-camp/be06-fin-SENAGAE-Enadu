package com.example.common.Ranking.Repository;

import com.example.common.Ranking.model.Entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Ranking, Long> {
}
