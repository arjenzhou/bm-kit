package com.arjenzhou.kit.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/26
 */
public class JSONUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * parse JSON to object
     *
     * @param json  the JSON string to parse
     * @param clazz target type
     * @param <T>   class of the type of result object
     * @return object parsed
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * convert object to JSON string
     *
     * @param t   source object
     * @param <T> type of source object
     * @return JSON string
     */
    public static <T> String toJSON(T t) {
        try {
            return OBJECT_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
