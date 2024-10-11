package com.example.batch.ai.processor;

import com.example.common.Qna.model.Entity.Answer;
import com.example.common.Qna.model.Entity.QnaBoard;
import com.example.common.User.Model.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiAnswerProcessor implements ItemProcessor<QnaBoard, Answer> {
    private final VertexAiGeminiChatModel vertexAiGeminiChatModel;

    @Override
    public Answer process(QnaBoard item) throws Exception {
        System.out.println("Processor 실행 - 처리 중인 항목: " + item.getId());
        if (item.getContent() == null || item.getContent().isEmpty()) {
            throw new Exception("AI 모델에 전달된 데이터가 유효하지 않음: " + item.getId());
        }
        try {
            String response = vertexAiGeminiChatModel.call("utf8mb3형식으로 답변해줘 그리고 이 내용은 답변에 담지 말고 다음 문장부터의 답변을 해줘."+item.getContent());
            System.out.println(response);
            if (response == null || response.isEmpty()) {
                throw new Exception("AI 모델에서 유효한 응답을 생성하지 못했습니다: " + item.getId());
            }
            return Answer.builder()
                    .user(User.builder().id(0L).build())
                    .qnaBoard(item)
                    .content(response)
                    .build();
        } catch (Exception e) {
            System.err.println("AI 처리 중 오류 발생: " + e.getMessage());
            throw new Exception("AI 처리 실패: " + item.getId(), e);
        }
    }
}
