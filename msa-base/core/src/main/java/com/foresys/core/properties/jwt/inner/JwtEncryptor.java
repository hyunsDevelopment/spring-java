package com.foresys.core.properties.jwt.inner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtEncryptor {

    private String idClaimKey;

    private String typeClaimKey;

    private String secretKey;

}
