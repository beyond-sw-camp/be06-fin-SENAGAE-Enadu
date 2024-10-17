package com.example.batch.processor;

import com.example.common.Qna.model.Entity.Answer;
import com.example.common.Qna.model.Entity.QnaBoard;
import com.example.common.User.Model.Entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiAnswerProcessor implements ItemProcessor<QnaBoard, Answer> {
    private final VertexAiGeminiChatModel vertexAiGeminiChatModel;

    @Override
    public Answer process(QnaBoard item) throws Exception {
        log.info("Processor 실행 - 처리 중인 항목: {}", item.getId());

        if (item.getContent() == null || item.getContent().isEmpty()) {
            log.error("AI 모델에 전달된 데이터가 유효하지 않음: {}", item.getId());
            throw new Exception("AI 모델에 전달된 데이터가 유효하지 않음: " + item.getId());
        }

        try {
            String response = vertexAiGeminiChatModel.call("한국어로 답변해줘" + item.getContent());
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
}