package org.example.backend.Chat.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
            MessageRes messageRes = objectMapper.readValue(jsonMessage,
                    MessageRes.class);
            // 웹 소켓 연결하고 있는 클라이언트들에게 메시지 전송
            template.convertAndSend("/sub/chatroom/" + messageRes.getChatRoomId(), messageRes);
        } catch (Exception e) {
            throw new RuntimeException("예외 발생 : " + e.getMessage());
        }
    }
}
