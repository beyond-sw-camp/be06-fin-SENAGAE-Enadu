package org.example.backend.Chat.Model.Req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class MessageReq {
    private Long chatRoomId;
    private String message;
    private String sendTime;
    private Long senderId;
}
