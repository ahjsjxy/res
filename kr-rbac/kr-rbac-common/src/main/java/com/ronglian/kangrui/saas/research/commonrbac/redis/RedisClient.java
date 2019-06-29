package com.ronglian.kangrui.saas.research.commonrbac.redis;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisClient {

    @Autowired
    private StringRedisTemplate redisTemplate;


    public Set<byte[]> keys(String key) {
        return redisTemplate.execute((RedisConnection connection) ->
                connection.keys(key.getBytes())
        );
    }

    public byte[] get(String key) {

        return redisTemplate.execute((RedisConnection connection) ->
                connection.get(redisTemplate.getStringSerializer().serialize(key))
        );
    }

    public boolean set(String key, byte[] value) {
        return redisTemplate.execute((RedisConnection connection) -> {
            connection.set(redisTemplate.getStringSerializer().serialize(key), value);
            return true;
        });
    }

    public boolean set(String key, byte[] value, long seconds) {
        return redisTemplate.execute((RedisConnection connection) -> {
            connection.setEx(
                    redisTemplate.getStringSerializer().serialize(key), seconds,
                    value
            );
            return true;
        });
    }


    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean expire(String key, long seconds) {
        return redisTemplate.execute((RedisConnection connection) ->
                connection.expire(redisTemplate.getStringSerializer().serialize(key), seconds)
        );
    }

    public long ttl(String key) {
        return redisTemplate.execute((RedisConnection connection) ->
                connection.ttl(redisTemplate.getStringSerializer().serialize(key))
        );
    }

}
