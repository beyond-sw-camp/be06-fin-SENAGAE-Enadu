package com.example.common.Chat.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import com.example.common.User.Model.Entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime sendTime;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;
}
