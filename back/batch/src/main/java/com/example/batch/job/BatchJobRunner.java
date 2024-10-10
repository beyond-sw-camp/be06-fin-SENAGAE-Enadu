package com.example.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchJobRunner implements CommandLineRunner {
    private final JobLauncher jobLauncher;
    private final Job dailyRankingCalculateJob;

    @Override
    public void run(String... args) {
        System.out.println("시작");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            System.out.println("성공");
        } catch (Exception e) {
            System.out.println("망했다");
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}