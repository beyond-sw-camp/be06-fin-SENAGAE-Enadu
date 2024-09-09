package org.example.backend.Chat.Model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomRes {
    private Long chatRoomId;
    private String recipientName;
    private String recipientProfile;
    private String lastMessage;
    private LocalDateTime lastMessageDay;
}
