package com.example.common.Chat.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import com.example.common.User.Model.Entity.User;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private User user2;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom")
    private List<Chat> chatList;
}
