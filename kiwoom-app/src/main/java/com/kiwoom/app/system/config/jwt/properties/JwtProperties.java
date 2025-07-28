package com.kiwoom.app.system.config.jwt.properties;

import com.kiwoom.app.system.config.jwt.properties.inner.JwtAccess;
import com.kiwoom.app.system.config.jwt.properties.inner.JwtEncryptor;
import com.kiwoom.app.system.config.jwt.properties.inner.JwtRefresh;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private JwtEncryptor encryptor;

    private JwtAccess access;

    private JwtRefresh refresh;

    private String[] excludePaths;

}
