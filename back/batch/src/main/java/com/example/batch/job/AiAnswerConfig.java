package com.example.batch.job;

import com.example.batch.common.SleepChunkListener;
import com.example.batch.writer.AnswerWriter;
import com.example.batch.processor.AiAnswerProcessor;
import com.example.common.Qna.Repository.QuestionRepository;
import com.example.common.Qna.model.Entity.Answer;
import com.example.common.Qna.model.Entity.QnaBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AiAnswerConfig {
    private final QuestionRepository questionRepository;
    private final AiAnswerProcessor answerProcessor;
    private final AnswerWriter answerWriter;
    private final SleepChunkListener sleepChunkListener;

    @Bean
    @StepScope
    public ItemReader<QnaBoard> questionReader() {
        System.out.println("reader 실행");
        return new RepositoryItemReaderBuilder<QnaBoard>()
                .repository(questionRepository)
                .methodName("findByAnswerCountAndEnableTrue")
                .arguments(0)
                .pageSize(2)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .name("qnaReader")
                .build();
    }

    @Bean
    public Job aiAnswerJob(JobRepository jobRepository, Step answerStep) {
        return new JobBuilder("aiAnswerJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(answerStep)
                .build();
    }


    @Bean
    public Step answerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("answerStep", jobRepository)
                .<QnaBoard, Answer>chunk(2, transactionManager)
                .reader(questionReader())
                .processor(answerProcessor)
                .writer(answerWriter)
                .listener(sleepChunkListener)
                .build();
    }

}
