package com.example.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    public static final String MAIN_DATASOURCE = "mainDataSource";
    public static final String BATCH_DATASOURCE = "batchDataSource";

    @Primary
    @Bean(name = MAIN_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.main")
    public DataSource mainDataSource() {
        return new DriverManagerDataSource();
    }

    @Bean(name = BATCH_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.batch")
    public DataSource batchDataSource() {
        return new DriverManagerDataSource();
    }
}