package com.example.batch.ai.job;

import com.example.batch.ai.writer.AnswerWriter;
import com.example.batch.ai.processor.AiAnswerProcessor;
import com.example.common.Qna.Repository.QuestionRepository;
import com.example.common.Qna.model.Entity.Answer;
import com.example.common.Qna.model.Entity.QnaBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;


@Configuration
@RequiredArgsConstructor
public class AiAnswerConfig {
    private final QuestionRepository questionRepository;
    private final AiAnswerProcessor answerProcessor;
    private final AnswerWriter answerWriter;
    private final SleepChunkListener sleepChunkListener;

    @Bean
    public ItemReader<QnaBoard> questionReader() {
        System.out.println("reader 실행");
        return new RepositoryItemReaderBuilder<QnaBoard>()
                .repository(questionRepository)
                .methodName("findByAnswerCountAndEnableTrue")
                .arguments(0)
                .pageSize(5)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .name("qnaReader")
                .build();
    }

    @Bean
    public Job answerJob(JobRepository jobRepository, Step answerStep) {
        return new JobBuilder("answerJob", jobRepository)
                .start(answerStep)
                .build();
    }

    @Bean
    public Step answerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("answerStep", jobRepository)
                .<QnaBoard, Answer>chunk(5, transactionManager)
                .reader(questionReader())
                .processor(answerProcessor)
                .writer(answerWriter)
                .listener(sleepChunkListener)
                .build();
    }

}
