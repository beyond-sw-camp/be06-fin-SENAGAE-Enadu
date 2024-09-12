package org.example.backend.Chat.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.backend.Chat.Model.Req.MessageReq;
import org.example.backend.Chat.Model.Res.MessageRes;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumerService {

    private static final String TOPIC_NAME = "chat";

    private final SimpMessageSendingOperations template;

    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = TOPIC_NAME, groupId = "group_1")
    public void listenMessage(String jsonMessage) {
        try {
            MessageReq messageReq = objectMapper.readValue(jsonMessage, MessageReq.class);
            MessageRes messageRes = MessageRes.builder()
                    .senderId(messageReq.getSenderId())
                    .sendTime(messageReq.getSendTime())
                    .message(messageReq.getMessage())
                    .build();
            // 웹 소켓 연결하고 있는 클라이언트들에게 메시지 전송
            template.convertAndSend("/sub/chatroom/" + messageReq.getChatRoomId(), messageRes);
        } catch (Exception e) {
            throw new RuntimeException("예외 발생 : " + e.getMessage());
        }
    }
}
