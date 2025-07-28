package com.kiwoom.app.system.config.jasypt.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "jasypt.encryptor")
public class JasyptProperties {

    private String password;
    private String algorithm;
    private int keyObtentionIterations;
    private int poolSize;
    private String providerName;
    private String saltGeneratorClassname;
    private String ivGeneratorClassname;
    private String stringOutputType;
    private String bean;
}
