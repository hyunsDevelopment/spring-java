package com.foresys.app2.fulltext.annotation;

import com.foresys.app2.fulltext.type.PadType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FullTextField {

    /**
     * 필드 길이
     */
    int length() default 0;

    /**
     * 빈칸 채울 방향 설정
     */
    PadType pad() default PadType.RIGHT;

    /**
     * 빈칸 채울 값 설정
     */
    char padChar() default ' ';

    /**
     * 건수 필드인 경우 Target 필드명 설정
     */
    String tgtListFieldName() default "";

    /**
     * 전체 길이 필드 설정
     */
    boolean totLenField() default false;

    /**
     * Header 길이 필드 설정
     */
    boolean headLenField() default false;

    /**
     * Body 길이 필드 설정
     */
    boolean bodyLenField() default false;

    /**
     * 전체 길이에 포함이 안되는 필드 설정
     */
    boolean exceptLenField() default false;

    /**
     * 결과코드 필드 설정
     */
    boolean rsltCdField() default false;

}
