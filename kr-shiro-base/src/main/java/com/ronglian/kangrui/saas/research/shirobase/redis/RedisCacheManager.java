package com.ronglian.kangrui.saas.research.shirobase.redis;

import com.ronglian.kangrui.saas.research.commonrbac.redis.RedisClient;
import com.ronglian.kangrui.saas.research.shirobase.config.UserAuthRealm;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RedisCacheManager implements CacheManager {
    
    @Autowired
    RedisClient redisService;
    
    private Map<String, Object> caches = new HashMap<>();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if(!caches.containsKey(name))
            caches.put(name, new RedisCache<K, V>(name,redisService));
        
        log.info("获取RedisCacheManager： {}", name);
        return (RedisCache<K, V>)caches.get(name);

    }
}
