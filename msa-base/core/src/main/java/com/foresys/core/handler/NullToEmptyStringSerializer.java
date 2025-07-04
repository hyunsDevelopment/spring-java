package com.foresys.core.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class NullToEmptyStringSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeString("");
	}
		
	@SuppressWarnings("unused")
	private static class EmptyStringSerializer extends JsonSerializer<Object> {
        public EmptyStringSerializer() {}

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException, JsonProcessingException {
            jsonGenerator.writeString("");
        }
    }
}
