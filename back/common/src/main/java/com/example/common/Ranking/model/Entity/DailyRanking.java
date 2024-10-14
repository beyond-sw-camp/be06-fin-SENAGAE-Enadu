package com.example.common.Ranking.model.Entity;

import com.example.common.User.Model.Entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "daily_ranking")
public class DailyRanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rank;

    private Integer point;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
