package com.example.batch.job;

import com.example.common.Ranking.Repository.RankRepository;
import com.example.common.Ranking.model.Entity.Ranking;
import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
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
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
public class DailyRankJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UserRepository beforeRepository;
    private final RankRepository afterRepository;

    public DailyRankJob(JobRepository jobRepository,
                        PlatformTransactionManager transactionManager,
                        UserRepository beforeRepository,
                        RankRepository afterRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.beforeRepository = beforeRepository;
        this.afterRepository = afterRepository;
    }

    Integer chunkSize = 0;


    @Bean
    @StepScope // 주기에 따라 새로운 빈 생성
    public ItemReader<User> userItemReader() {
        List<User> oldUsers =
                beforeRepository.findByEnableTrueAndIdNot(0L);
        chunkSize = oldUsers.size();
        return new ListItemReader<>(oldUsers);
    }


    public ItemProcessor<User, Ranking> rankProcessor() {
        return user -> {
            System.out.println("user.getEnable() = " + user.getEnable());
            Integer userRank = beforeRepository.countByPointGreaterThanAndEnableTrue(user.getPoint());
            System.out.println("userRank = " + userRank);
            return Ranking.builder()
                    .userId(user.getId())
                    .name(user.getNickname())
                    .point(user.getPoint())
                    .grade(user.getGrade())
                    .rank(userRank+1)
                    .build();
        };
    }

    @Bean
    public ItemWriter<Ranking> rankWriter() {
        if (!afterRepository.findAll().isEmpty()) {
            System.out.println("데이터 삭제 완");
            afterRepository.deleteAll();
        }
        return afterRepository::saveAll;
    }

    @Bean
    public Job dailyRankingCalculateJob(Step updateDailyRankStep) {
        return new JobBuilder("dailyRankingCalculateJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(updateDailyRankStep)
                .build();
    }

    @Bean
    public Step updateDailyRankStep() {
        return new StepBuilder("updateDailyRankStep", jobRepository)
                .<User, Ranking>chunk(chunkSize, transactionManager)
                .reader(userItemReader())
                .processor(rankProcessor())
                .writer(rankWriter())
                .transactionManager(transactionManager)
                .build();
    }
}