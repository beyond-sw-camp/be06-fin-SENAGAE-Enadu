package org.example.backend.Chat.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.User.Model.Entity.User;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime sendTime = LocalDateTime.now();
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;
}
