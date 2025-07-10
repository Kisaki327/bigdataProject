package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

@Component
public class RedisCacheService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 获取缓存（单对象），不存在则调用 dbFetcher 获取并写入缓存
     */
    public <T> T getOrCache(String key, Class<T> clazz, Supplier<T> dbFetcher, Duration ttl) {
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return safeConvert(cached, clazz);
        }

        T result = dbFetcher.get();
        if (result != null) {
            redisTemplate.opsForValue().set(key, result, ttl);
        }
        return result;
    }

    /**
     * 获取缓存（List），不存在则调用 dbFetcher 获取并写入缓存
     * 使用 opsForValue 统一存储List，避免 Redis List 结构兼容问题
     */
    public <T> List<T> getOrCacheList(String key, Class<T> clazz, Supplier<List<T>> dbFetcher, Duration ttl) {
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            try {
                if (cached instanceof JSONArray) {
                    return ((JSONArray) cached).toList(clazz);
                } else if (cached instanceof String) {
                    return JSONArray.parseArray((String) cached, clazz);
                } else {
                    return JSONArray.parseArray(JSONObject.toJSONString(cached), clazz);
                }
            } catch (Exception e) {
                throw new RuntimeException("Redis 缓存 List 解析失败: " + e.getMessage(), e);
            }
        }

        List<T> list = dbFetcher.get();
        if (list != null && !list.isEmpty()) {
            redisTemplate.opsForValue().set(key, list, ttl);
        }
        return list;
    }

    /**
     * 设置单个缓存值
     */
    public void set(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    /**
     * 获取单个缓存值
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? safeConvert(value, clazz) : null;
    }

    /**
     * 删除缓存
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 判断缓存是否存在
     */
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 安全类型转换工具（支持 fastjson2 的 JSONObject/JSONArray 自动转换）
     */
    private <T> T safeConvert(Object obj, Class<T> clazz) {
        if (obj == null) return null;

        try {
            if (clazz.isInstance(obj)) {
                return clazz.cast(obj);
            } else if (obj instanceof JSONObject) {
                return ((JSONObject) obj).to(clazz);
            } else if (obj instanceof JSONArray) {
                // 一般不会缓存单个JSONArray，List用getOrCacheList处理
                throw new ClassCastException("Unexpected JSONArray when casting to " + clazz.getName());
            } else {
                return JSONObject.parseObject(obj.toString(), clazz);
            }
        } catch (Exception e) {
            throw new RuntimeException("Redis 缓存对象转换失败: " + e.getMessage(), e);
        }
    }
}
