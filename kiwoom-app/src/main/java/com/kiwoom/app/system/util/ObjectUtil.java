package com.kiwoom.app.system.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ObjectUtil {

    private final ObjectMapper objectMapper;

    @SuppressWarnings("rawtypes")
    public HashMap toHashMap(Object obj) {
        HashMap map = new HashMap<>();
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
            return map;
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }

    public <T> T toObject(String str, Class<T> clazz) {
        try {
            return objectMapper.readValue(str, clazz);
        } catch (Exception ignored) {
            return null;
        }
    }
}
