package com.foresys.app1.system.db;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.stereotype.Component;

@Component
public class MybatisConfig extends MapperScannerConfigurer {

    public MybatisConfig() {
        super.setBasePackage("com.foresys");
        super.setAnnotationClass(Mapper.class);
        super.setSqlSessionFactoryBeanName("dbSqlSession");
    }

}
