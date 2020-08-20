package com.zkn.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zkn.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * json工具类
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/18
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T readValue(String str, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(str, valueType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new BaseException("json parse error");
        }
    }

    public static String transToString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new BaseException("json parse error");
        }
    }
}
