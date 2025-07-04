package com.sample.sequence.util;

public class RedisKeyUtil {

    public static String getSequenceKey(String name, String dateString) {
        return "seq:" + name + ":" + dateString;
    }

}
