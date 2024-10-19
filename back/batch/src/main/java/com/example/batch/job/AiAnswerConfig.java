package com.example.batch.job;

import com.example.batch.common.SleepChunkListener;
import com.example.batch.processor.HttpAiAnswerProcessor;
import com.example.batch.writer.AnswerWriter;
import com.example.batch.processor.AiAnswerProcessor;
import com.example.common.Qna.Repository.QuestionRepository;
import com.example.common.Qna.model.Entity.Answer;
import com.example.common.Qna.model.Entity.QnaBoard;
import java.util.List;
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
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class AiAnswerConfig {
    private final QuestionRepository questionRepository;
    private final AiAnswerProcessor answerProcessor;
    private final HttpAiAnswerProcessor httpAiAnswerProcessor;
    private final AnswerWriter answerWriter;
    private final SleepChunkListener sleepChunkListener;

    @Bean
    @StepScope
    public ItemReader<QnaBoard> bulkQuestionReader() {
        log.info("Reader 실행");
        List<QnaBoard> qnaBoardList = questionRepository.findAllByAnswerCountAndEnableTrue(0);
        log.info("총 {}개의 QnaBoard 데이터를 불러옴", qnaBoardList.size());
        return new ListItemReader<>(qnaBoardList);
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
                .reader(bulkQuestionReader())
                .processor(httpAiAnswerProcessor)
                .writer(answerWriter)
                .listener(sleepChunkListener)
                .build();
    }

}
