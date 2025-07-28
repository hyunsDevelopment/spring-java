package com.kiwoom.app.system.config.datasource;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.stereotype.Component;

@Component
public class MybatisConfig extends MapperScannerConfigurer {

    public MybatisConfig() {
        super.setBasePackage("com.kiwoom");
        super.setAnnotationClass(Mapper.class);
        super.setSqlSessionFactoryBeanName("dbSqlSession");
    }
}
