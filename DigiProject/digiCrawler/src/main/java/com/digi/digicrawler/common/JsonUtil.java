package com.digi.digicrawler.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.Map;

public class JsonUtil {
    @SneakyThrows
    public static <T> T toClazz(ObjectMapper mapper,String value,Class<T>clazz)
    {
        return mapper.readValue(value,clazz);
    }

    @SneakyThrows
    public static Map toMap(ObjectMapper mapper, String value) {
        return mapper.readValue(value, Map.class);
    }
    @SneakyThrows
    public static String toJson(ObjectMapper mapper, Map<String, Object> map) {
        return mapper.writeValueAsString(map);
    }
    @SneakyThrows
    public static <T> String toJson(ObjectMapper mapper, T clazz) {
        return mapper.writeValueAsString(clazz);
    }
}
