package com.kiwoom.app.system.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serial;

public class CustomObjectMapper extends ObjectMapper {

    @Serial
    private static final long serialVersionUID = 1L;

    public CustomObjectMapper() {
        getSerializerProvider().setNullValueSerializer(new NullToEmptyStringSerializer());
    }

}
