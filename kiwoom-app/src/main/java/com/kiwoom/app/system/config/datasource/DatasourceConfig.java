package com.kiwoom.app.system.config.datasource;

import com.kiwoom.app.system.config.datasource.properties.MybatisProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatasourceConfig {

    private final MybatisProperties mybatisProperties;

    @Bean(name = "dbProperties")
    @ConfigurationProperties("spring.datasource")
    @Primary
    HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean(name = "dbSource")
    DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean(name="dbSqlSession")
    SqlSessionFactory sqlSessionFactory(@Qualifier("dbSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        sessionFactory.setMapperLocations(resolver.getResources(mybatisProperties.getMapperLocations()));
        sessionFactory.setConfigLocation(resolver.getResource(mybatisProperties.getConfigLocation()));
        return sessionFactory.getObject();
    }

    // JPA EntityManagerFactory 구성 (DataSource 공유)
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("dbSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.kiwoom.app") // 엔티티 위치
                .persistenceUnit("default")
                .build();
    }

    // 트랜잭션 매니저는 JPA 기준으로 통일 (MyBatis도 동일 커넥션 사용 가능)
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}