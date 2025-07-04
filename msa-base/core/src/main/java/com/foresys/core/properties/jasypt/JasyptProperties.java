package com.foresys.core.properties.jasypt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
