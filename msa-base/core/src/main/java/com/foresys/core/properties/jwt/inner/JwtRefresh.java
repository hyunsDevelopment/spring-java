package com.foresys.core.properties.jwt.inner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefresh {

    private long timeOut;

    private String namePrefix;

}
