package com.foresys.core.provider.jasypt;

import com.foresys.core.properties.jasypt.JasyptProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class JasyptConfigProvider {

    public StringEncryptor encryptorAES(JasyptProperties jasyptProperties) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(jasyptProperties.getPassword());
        config.setAlgorithm(jasyptProperties.getAlgorithm());
        config.setKeyObtentionIterations(jasyptProperties.getKeyObtentionIterations());
        config.setPoolSize(jasyptProperties.getPoolSize());
        config.setProviderName(jasyptProperties.getProviderName());
        config.setSaltGeneratorClassName(jasyptProperties.getSaltGeneratorClassname());
        config.setIvGeneratorClassName(jasyptProperties.getIvGeneratorClassname());
        config.setStringOutputType(jasyptProperties.getStringOutputType());

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);

        return encryptor;
    }

}
