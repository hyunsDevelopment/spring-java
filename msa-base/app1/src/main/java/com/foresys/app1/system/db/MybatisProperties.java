package com.foresys.app1.system.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("mybatis")
public class MybatisProperties {

    private String mapperLocations;

    private String configLocation;

    private String typeAliasesPackage;

}
