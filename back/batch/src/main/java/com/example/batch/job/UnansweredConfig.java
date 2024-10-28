package com.example.batch.job;

import com.example.common.Qna.Repository.QuestionRepository;
import com.example.common.Qna.model.Entity.QnaBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.common.Qna.model.Resolved.ENDED;
import static com.example.common.Qna.model.Resolved.UNSOLVED;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UnansweredConfig {
    private final QuestionRepository questionRepository;

    @Bean
    @StepScope
    public ItemReader<QnaBoard> unansweredQuestionReader() {
        log.info("Reader 실행");
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);
        List<QnaBoard> qnaBoardList = questionRepository.findByEnableTrueAndCreatedAtBeforeAndResolvedAndAnswerCountAndAnswerListUserId(twoWeeksAgo, UNSOLVED, 1, 0L);
        log.info("불러온 QnaBoard 수: {}", qnaBoardList.size());
        return new ListItemReader<>(qnaBoardList);
    }

    @Bean
    public ItemProcessor<QnaBoard, QnaBoard> unansweredProcessor() {
        return item -> {
            log.info("Processor 실행 - 처리 중인 항목: {}", item.getId());
            item.changeResolved(ENDED);
            return item;
        };
    }

    @Bean
    public ItemWriter<QnaBoard> unansweredQuestionWriter() {
        return items -> {
            for (QnaBoard item : items) {
                log.info("Writer 처리 중인 항목: {}", item.getId());
                questionRepository.save(item);
            }
        };
    }

    @Bean
    public Job unansweredJob(JobRepository jobRepository, Step unansweredStep) {
        return new JobBuilder("unansweredJob", jobRepository)
                .start(unansweredStep)
                .build();
    }

    @Bean
    public Step unansweredStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("unansweredStep", jobRepository)
                .<QnaBoard, QnaBoard>chunk(10, transactionManager)
                .reader(unansweredQuestionReader())
                .processor(unansweredProcessor())
                .writer(unansweredQuestionWriter())
                .build();
    }
}