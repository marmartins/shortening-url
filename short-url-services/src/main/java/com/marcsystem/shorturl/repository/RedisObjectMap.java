package com.marcsystem.shorturl.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class RedisObjectMap {

    public static final String REDIS_KEY_SEPARATOR = "|";
    private String redisKey;

    private Map<String, String> values = new HashMap<>();

    public RedisObjectMap(String redisKey) {
        this.redisKey = redisKey;
    }

    public String put(String key, String value) {
        return values.put(key, value);
    }

    public String concatRedisKey(String value) {
        redisKey += REDIS_KEY_SEPARATOR + value;
        return redisKey;
    }
}
