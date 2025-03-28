package com.example.common.ErrorArchive.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.common.User.Model.Entity.User;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ErrorLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "error_archive_id", nullable = false)
    private ErrorArchive errorArchive;


    @Column(nullable = false)
    private boolean state;

    public void updateState(boolean state){
        this.state = state;
    }
}
