package com.example.batch.writer;

import com.example.common.Qna.Repository.AnswerRepository;
import com.example.common.Qna.Repository.QuestionRepository;
import com.example.common.Qna.model.Entity.Answer;
import com.example.common.Qna.model.Entity.QnaBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnswerWriter implements ItemWriter<Answer> {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Override
    public void write(Chunk<? extends Answer> chunk) throws Exception {
        log.info("Writer 실행 - 처리할 항목 수: {}", chunk.size());

        for (Answer answer : chunk) {
            log.info("Writer 처리 중인 항목: QnaBoard ID = {}", answer.getQnaBoard().getId());
            answerRepository.save(answer);

            QnaBoard qnaBoard = answer.getQnaBoard();
            if (qnaBoard != null) {
                qnaBoard.increaseAnswerCount();
                questionRepository.save(qnaBoard);
            }
        }
    }
}