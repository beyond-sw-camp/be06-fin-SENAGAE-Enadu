package com.example.common;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class CommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }

    @PostConstruct
    public void init()
    {
        // JVM의 기본 시간대를 Asia/Seoul로 설정
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
