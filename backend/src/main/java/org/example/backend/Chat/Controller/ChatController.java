package org.example.backend.Chat.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Chat.Model.Res.ChatMessageListRes;
import org.example.backend.Chat.Model.Res.ChatRoomRes;
import org.example.backend.Chat.Service.ChatService;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/chatRoomList")
    public BaseResponse<List<ChatRoomRes>> chatRoomList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return new BaseResponse<>(chatService.getMyChatRoomList(customUserDetails.getUserId()));
    }

    @GetMapping("/messageList")
    public BaseResponse<ChatMessageListRes> getChatMessageList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                     Long chatRoomId, Integer page, Integer size) {
        return new BaseResponse<>(chatService.getChatMessageList(customUserDetails.getUserId(), chatRoomId, page, size));

    }

}
