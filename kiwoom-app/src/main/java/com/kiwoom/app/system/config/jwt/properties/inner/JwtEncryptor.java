package com.kiwoom.app.system.config.jwt.properties.inner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtEncryptor {

    private String idClaimKey;

    private String typeClaimKey;

    private String secretKey;

}
