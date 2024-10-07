package org.example.backend.Chat.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Chat.Model.Req.StartChatReq;
import org.example.backend.Chat.Model.Res.GetChatMessageRes;
import org.example.backend.Chat.Model.Res.GetChatRoomRes;
import org.example.backend.Chat.Model.Res.StartChatRes;
import org.example.backend.Chat.Service.ChatService;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/chatRoomList")
    public BaseResponse<List<GetChatRoomRes>> chatRoomList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return new BaseResponse<>(chatService.getMyChatRoomList(customUserDetails.getUserId()));
    }

    @GetMapping("/messageList")
    public BaseResponse<List<GetChatMessageRes>> getChatMessageList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                    Long chatRoomId, Integer page, Integer size) {
        return new BaseResponse<>(chatService.getChatMessageList(customUserDetails.getUserId(), chatRoomId, page, size));
    }

    @PostMapping("/start")
    public BaseResponse<StartChatRes> createChatRoom(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody StartChatReq startChatReq){
        System.out.println(startChatReq.getNickname());
        return new BaseResponse<>(chatService.startChat(customUserDetails.getUserId(), startChatReq));
    }
}
