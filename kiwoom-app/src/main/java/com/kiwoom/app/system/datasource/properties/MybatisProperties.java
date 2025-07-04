package com.kiwoom.app.system.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "mybatis")
public class MybatisProperties {

    private String mapperLocations;
    private String configLocation;
    private String typeAliasesPackage;
}
