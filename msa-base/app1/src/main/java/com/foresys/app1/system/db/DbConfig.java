package com.foresys.app1.system.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DbConfig {

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

    @Bean
    DataSourceTransactionManager transactionManager(@Qualifier("dbSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
