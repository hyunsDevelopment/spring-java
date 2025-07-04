package com.example.batch_sample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.batch_sample.entity.UserInfo;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<UserInfo> reader() {
        return new FlatFileItemReaderBuilder<UserInfo>()
            .name("userItemReader")
            .resource(new ClassPathResource("input/users.csv"))
            .delimited()
            .names("id", "name")
            .targetType(UserInfo.class)
            .linesToSkip(1)
            .build();
    }

    @Bean
    public ItemProcessor<UserInfo, UserInfo> processor() {
        return user -> {
            user.setName(user.getName().toUpperCase());
            return user;
        };
    }

    @Bean
    public JpaItemWriter<UserInfo> writer() {
        JpaItemWriter<UserInfo> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step step() {
        return new StepBuilder("step1", jobRepository)
            .<UserInfo, UserInfo>chunk(10, transactionManager)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("userImportJob", jobRepository)
            .start(step())
            .build();
    }
}
