package com.foresys.app1.system.jasypt;

import com.foresys.core.properties.jasypt.JasyptProperties;
import com.foresys.core.provider.jasypt.JasyptConfigProvider;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JasyptConfig {

    @Bean("jasyptProperties")
    @ConfigurationProperties("jasypt.encryptor")
    @Primary
    JasyptProperties jasyptProperties() {
        return new JasyptProperties();
    }

    @Bean("jasyptEncryptorAES")
    @Primary
    public StringEncryptor encryptorAES(@Qualifier("jasyptProperties") JasyptProperties jasyptProperties) {
        return new JasyptConfigProvider().encryptorAES(jasyptProperties);
    }

}
