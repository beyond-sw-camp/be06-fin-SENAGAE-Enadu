package org.example.backend.Chat.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageRes {
    private String message;
    private LocalDateTime sendTime;
    private Long senderId;
}
