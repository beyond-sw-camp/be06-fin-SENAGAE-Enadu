package com.example.common.Ranking.model.Entity;

import com.example.common.User.Model.Entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weekly_ranking")
public class WeeklyRanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delta_point")
    private Integer deltaPoint;

    private Integer rank;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
