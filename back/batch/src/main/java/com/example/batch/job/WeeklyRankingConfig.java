package com.example.batch.job;

import com.example.common.Ranking.Repository.WeeklyRankingRepository;
import com.example.common.Ranking.model.Entity.WeeklyRanking;
import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WeeklyRankingConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UserRepository beforeRepository;
    private final WeeklyRankingRepository weeklyRankingRepository;

    int userRank = 0;
    int samePointRank = 0;
    int beforePoint = Integer.MAX_VALUE;

    Integer chunkSize = 0;

    @Bean
    @StepScope // 주기에 따라 새로운 빈 생성
    public ItemReader<User> weeklyItemReader() {
        List<User> oldUsers =
                beforeRepository.findByEnableTrueAndIdNot(0L, Sort.by(Sort.Direction.DESC, "weeklyPoint"));
        chunkSize = oldUsers.size();
        return new ListItemReader<>(oldUsers);
    }

    @Bean
    @StepScope
    public ItemProcessor<User, WeeklyRanking> weeklyRankProcessor(@Value("#{stepExecution}") StepExecution stepExecution) {
        return user -> {
            samePointRank = stepExecution.getExecutionContext().getInt("samePointRank", 0);
            userRank = findRank(user.getWeeklyPoint(), beforePoint, userRank);
            stepExecution.getExecutionContext().putInt("samePointRank", samePointRank);
            beforePoint = user.getWeeklyPoint();
            System.out.println("userRank = " + userRank);

            return WeeklyRanking.builder()
                    .user(user)
                    .deltaPoint(user.getWeeklyPoint())
                    .rank(userRank)
                    .build();
        };
    }

    @Bean
    @StepScope
    public ItemWriter<WeeklyRanking> weeklyRankWriter() {
        if (!weeklyRankingRepository.findAll().isEmpty()) {
            weeklyRankingRepository.deleteAll();
        }
        return weeklyRankingRepository::saveAll;
    }

    @Bean
    public Job weeklyRankingJob(Step updateWeeklyRankStep) {
        return new JobBuilder("weeklyRankingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(updateWeeklyRankStep)
                .build();
    }

    @Bean
    public Step updateWeeklyRankStep() {
        return new StepBuilder("updateWeeklyRankingStep", jobRepository)
                .<User, WeeklyRanking>chunk(chunkSize, transactionManager)
                .reader(weeklyItemReader())
                .processor(weeklyRankProcessor(null))
                .writer(weeklyRankWriter())
                .transactionManager(transactionManager)
                .build();
    }

    public int findRank(int weeklyPoint, int beforePoint, int userRank) {
        if (beforePoint != weeklyPoint && samePointRank == 0) {

        } else if (beforePoint == weeklyPoint) {
            userRank -= 1;
            samePointRank += 1;
            System.out.println("samePointRank = " + samePointRank);
        } else if ((beforePoint != weeklyPoint) && samePointRank != 0) {
            userRank = userRank + samePointRank;
            samePointRank = 0;
        }
        return userRank + 1;
    }
}