package com.foresys.core.properties.jwt;

import com.foresys.core.properties.jwt.inner.JwtAccess;
import com.foresys.core.properties.jwt.inner.JwtEncryptor;
import com.foresys.core.properties.jwt.inner.JwtRefresh;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtProperties {

    private JwtEncryptor encryptor;

    private JwtAccess access;

    private JwtRefresh refresh;

}
