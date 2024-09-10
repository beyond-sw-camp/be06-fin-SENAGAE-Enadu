package org.example.backend.Chat.Model.Res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatMessageListRes {
    private String recipientNickname;
    private Long recipientId;
    private List<ChatMessageRes> messageList;
}
