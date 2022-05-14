package net.sopod.soim.data.serialize;

import net.sopod.soim.common.util.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * JsonByteSerializer
 *
 * @author tmy
 * @date 2022-03-28 11:05
 */
public class JacksonByteSerializer implements ByteSerializer{

    private static final Logger logger = LoggerFactory.getLogger(JacksonByteSerializer.class);

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        String json = new String(data, StandardCharsets.UTF_8);
        T obj = Jackson.json().deserialize(json, clazz);
        if (obj == null) {
            logger.error("json数据解析失败:{}", json);
            throw new IllegalStateException("json数据解析失败");
        }
        return obj;
    }

    @Override
    public byte[] serialize(Object pojo) throws Exception {
        String json = Jackson.json().serialize(pojo);
        return json.getBytes(StandardCharsets.UTF_8);
    }

}
