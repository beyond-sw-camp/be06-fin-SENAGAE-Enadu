package org.example.backend.Chat.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.backend.Chat.Model.Req.GetMessageReq;
import org.example.backend.Chat.Model.Res.GetMessageRes;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private static final String TOPIC_NAME = "chat";

    private final SimpMessageSendingOperations template;

    ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = TOPIC_NAME)
    public void listenMessage(String jsonMessage) {
        try {
            GetMessageReq getMessageReq = objectMapper.readValue(jsonMessage, GetMessageReq.class);
            GetMessageRes getMessageRes = GetMessageRes.builder()
                    .senderId(getMessageReq.getSenderId())
                    .sendTime(getMessageReq.getSendTime())
                    .message(getMessageReq.getMessage())
                    .build();
            // 웹 소켓 연결하고 있는 클라이언트들에게 메시지 전송
            template.convertAndSend("/sub/chatroom/" + getMessageReq.getChatRoomId(), getMessageRes);
        } catch (Exception e) {
            throw new RuntimeException("예외 발생 : " + e.getMessage());
        }
    }
}
