package com.zk.shiro.common.utils;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.lang.String.valueOf;
import static java.util.concurrent.TimeUnit.*;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {

    public static final String shiro_session_key = "spring:session:shiro:";

    private static final Gson gson = new Gson();

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, SECONDS);
        }
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, SECONDS);
        }
        return value == null ? null : fromJson(value.toString(), clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, SECONDS);
        }
        return value == null ? null : value.toString();
    }

    public Long getExpire(String key) {
        Long value=redisTemplate.getExpire(key);
        return value == null ? 0l : value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public Set<String> getKeys(String name) {
        return redisTemplate.keys(name);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return valueOf(object);
        }
        return gson.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }


    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }
}
