package com.example.batch_sample.job;

import com.example.batch_sample.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MemberJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job memberJob(Step memberStep) {
        return new JobBuilder("memberJob", jobRepository)
                .start(memberStep)
                .build();
    }

    @Bean
    public Step memberStep(FlatFileItemReader<Member> reader,
                           ItemProcessor<Member, Member> processor,
                           ItemWriter<Member> writer) {

        return new StepBuilder("memberStep", jobRepository)
                .<Member, Member>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<Member> reader() {
        return new FlatFileItemReaderBuilder<Member>()
                .name("memberReader")
                .resource(new ClassPathResource("input/members.csv"))
                .delimited()
                .names("id", "name", "email")
                .targetType(Member.class)
                .build();
    }

    @Bean
    public ItemProcessor<Member, Member> processor() {
        return member -> {
            member.setName(member.getName().toUpperCase());
            return member;
        };
    }

    @Bean
    public ItemWriter<Member> writer() {
        return items -> items.forEach(i -> log.info(">> {}", i));
    }
}
