package com.kiwoom.app.system.config.jwt.properties.inner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAccess {

    private long timeOut;

    private String namePrefix;

}
