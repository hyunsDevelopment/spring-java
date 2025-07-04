package com.foresys.app2.fulltext.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface HeadValue {

    /**
     * Header 필드명 설정
     * @return
     */
    String name();

    /**
     * Header 필드값 설정
     * @return
     */
    String value();

}
