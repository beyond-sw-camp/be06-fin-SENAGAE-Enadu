package org.example.backend.Chat.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StartChatRes {
    private Long chatRoomId;
    private String recipientNickname;
    private String recipientProfile;
    private Long recipientId;
}
