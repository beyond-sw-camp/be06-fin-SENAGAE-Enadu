package org.example.backend.Chat.Model.Res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class MessageRes {
    private Long chatRoomId;
    private Long senderId;
    private String message;
    private String sendTime;
}
