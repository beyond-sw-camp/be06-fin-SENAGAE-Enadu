package org.example.backend.Chat.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomRes {
    private Long chatRoomId;
    private String recipientNickname;
    private String recipientProfile;
    private Long recipientId;
    private String lastMessage;
    private LocalDateTime lastMessageDay;
}
