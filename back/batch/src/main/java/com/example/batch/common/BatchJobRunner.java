package com.example.batch.common;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchJobRunner implements CommandLineRunner {
    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;
    @Value("${spring.batch.job.name}")
    private String jobName;

    @Override
    public void run(String... args) {
        System.out.println("배치 작업 시작");
        try {
            Job job = (Job) applicationContext.getBean(jobName);
            System.out.println(job);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
            System.out.println("배치 작업 성공");
        } catch (Exception e) {
            System.out.println("배치 작업 실패");
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}