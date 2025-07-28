package com.kiwoom.app.system.config.objectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwoom.app.system.serializer.NullToEmptyStringSerializer;

import java.io.Serial;

public class CustomObjectMapper extends ObjectMapper {

    @Serial
    private static final long serialVersionUID = 1L;

    public CustomObjectMapper() {
        getSerializerProvider().setNullValueSerializer(new NullToEmptyStringSerializer());
    }

}
