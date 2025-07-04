package com.foresys.app2.fulltext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FullTextInfo {

    Class<?> reqHeadClass();

    Class<?> resHeadClass() default Void.class;

    Class<?> resBodyClass() default Void.class;

    HeadValue[] headValues() default {};

}
