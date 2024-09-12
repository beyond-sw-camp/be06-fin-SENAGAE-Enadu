package org.example.backend.Chat.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageRes {
    private String message;
    private String sendTime;
    private Long senderId;
}
