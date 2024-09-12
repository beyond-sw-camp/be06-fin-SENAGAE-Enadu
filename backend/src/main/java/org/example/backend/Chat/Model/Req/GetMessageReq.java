package org.example.backend.Chat.Model.Req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class GetMessageReq {
    private Long chatRoomId;
    private String message;
    private String sendTime;
    private Long senderId;
}
