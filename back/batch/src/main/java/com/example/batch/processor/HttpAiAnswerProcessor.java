package com.example.batch.processor;

import com.example.common.Qna.model.Entity.Answer;
import com.example.common.Qna.model.Entity.QnaBoard;
import com.example.common.User.Model.Entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpAiAnswerProcessor implements ItemProcessor<QnaBoard, Answer> {

    private final RestTemplate restTemplate;

    @Value("${ai.api.endpoint}")
    private String aiEndpoint;

    @Value("${ai.api.key}")
    private String apiKey;

    @Override
    public Answer process(QnaBoard item) throws Exception {
        log.info("Processor 실행 - 처리 중인 항목: {}", item.getId());

        if (item.getContent() == null || item.getContent().isEmpty()) {
            log.error("AI 모델에 전달된 데이터가 유효하지 않음: {}", item.getId());
            throw new Exception("AI 모델에 전달된 데이터가 유효하지 않음: " + item.getId());
        }

        try {
            String response = callAiModel(item.getContent());
            log.info("AI 응답: {}", response);

            if (response == null || response.isEmpty()) {
                log.error("AI 모델에서 유효한 응답을 생성하지 못했습니다: {}", item.getId());
                throw new Exception("AI 모델에서 유효한 응답을 생성하지 못했습니다: " + item.getId());
            }

            return Answer.builder()
                    .user(User.builder().id(0L).build())
                    .qnaBoard(item)
                    .content(response)
                    .build();
        } catch (Exception e) {
            log.error("AI 처리 중 오류 발생: {}", e.getMessage(), e);
            throw new Exception("AI 처리 실패: " + item.getId(), e);
        }
    }

    private String callAiModel(String content) throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, String>> contentParts = new ArrayList<>();
        contentParts.add(Map.of("text", "한국어로 답변해줘 " + content));
        Map<String, Object> contents = Map.of("parts", contentParts);
        requestBody.put("contents", List.of(contents));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        String urlWithApiKey = aiEndpoint + "?key=" + apiKey;

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                urlWithApiKey,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseEntity.getBody());
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode result = candidates.get(0).path("content");
                JsonNode partsNode = result.path("parts");
                if (partsNode.isArray() && partsNode.size() > 0) {
                    String text = partsNode.get(0).path("text").asText();
                    return text;
                }
            }
            return null;
        } else {
            return null;
        }
    }
}