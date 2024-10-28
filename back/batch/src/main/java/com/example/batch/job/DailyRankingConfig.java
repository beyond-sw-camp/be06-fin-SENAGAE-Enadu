package com.example.batch.job;

import com.example.common.Ranking.Repository.DailyRankingRepository;
import com.example.common.Ranking.model.Entity.DailyRanking;
import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DailyRankingConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UserRepository beforeRepository;
    private final DailyRankingRepository dailyRankingRepository;


    Integer chunkSize = 0;


    @Bean
    @StepScope // 주기에 따라 새로운 빈 생성
    public ItemReader<User> dailyItemReader() {
        List<User> oldUsers =
                beforeRepository.findByEnableTrueAndIdNot(0L, Sort.by(Sort.Direction.DESC, "point"));
        chunkSize = oldUsers.size();
        return new ListItemReader<>(oldUsers);
    }


    public ItemProcessor<User, DailyRanking> dailyRankProcessor() {
        return user -> {
            System.out.println("user.getEnable() = " + user.getEnable());
            Integer userRank = beforeRepository.countByPointGreaterThanAndEnableTrue(user.getPoint());
            System.out.println("userRank = " + userRank);
            return DailyRanking.builder()
                    .user(user)
                    .point(user.getPoint())
                    .rank(userRank+1)
                    .build();
        };
    }

    @Bean
    @StepScope
    public ItemWriter<DailyRanking> dailyRankWriter() {
        if (!dailyRankingRepository.findAll().isEmpty()) {
            System.out.println("일간 랭킹 데이터 삭제 완료");
            dailyRankingRepository.deleteAll();
        }
        return dailyRankingRepository::saveAll;
    }

    @Bean
    public Job dailyRankingJob(Step updateDailyRankStep) {
        return new JobBuilder("dailyRankingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(updateDailyRankStep)
                .build();
    }

    @Bean
    public Step updateDailyRankStep() {
        return new StepBuilder("updateDailyRankingStep", jobRepository)
                .<User, DailyRanking>chunk(chunkSize, transactionManager)
                .reader(dailyItemReader())
                .processor(dailyRankProcessor())
                .writer(dailyRankWriter())
                .transactionManager(transactionManager)
                .build();
    }
}