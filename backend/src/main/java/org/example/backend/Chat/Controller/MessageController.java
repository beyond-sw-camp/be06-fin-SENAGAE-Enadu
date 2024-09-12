package org.example.backend.Chat.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.Chat.Model.Req.MessageReq;
import org.example.backend.Chat.Service.ChatService;
import org.example.backend.Chat.Service.KafkaProducerService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessageController {
    private final ChatService chatService;
    private final KafkaProducerService kafkaProducerService;

    @MessageMapping("/message/{chatRoomId}")
    public void sendMessage(MessageReq messageReq, @DestinationVariable("chatRoomId") Long chatRoomId) {
        chatService.saveChat(messageReq,chatRoomId); // 채팅 내역 저장
        kafkaProducerService.send(messageReq);
        //메세지 서비스 로직
    }
}