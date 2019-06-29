package com.ronglian.kangrui.saas.research.shirobase.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.ronglian.kangrui.saas.research.commonrbac.redis.RedisClient;
import com.ronglian.kangrui.saas.research.commonrbac.utils.Constants;
import com.ronglian.kangrui.saas.research.commonrbac.utils.SerializeUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {
    private String name;

    private RedisClient redisService;

    public RedisCache(String name, RedisClient redisService) {
        this.name = name;
        this.redisService = redisService;
    }

    @Override
    public V get(K key) throws CacheException {
        log.info("获取缓存: {}", key);
        byte[] value = redisService.get(key.toString());
        return (V) SerializeUtil.deserialize(value);
    }

    /**
     * 将权限信息加入缓存中
     */
    @Override
    public V put(K key, V value) throws CacheException {
        log.info("写入缓存: {}", key);
        redisService.set(key.toString(), SerializeUtil.serialize(value), Constants.DEFAULT_EXPIRE);
        return value;
    }

    /**
     * 将权限信息从缓存中删除
     */
    @Override
    public V remove(K key) throws CacheException {
        log.info("删除缓存: {}", key);
        
        V v = this.get(key);
        redisService.delete(key.toString());

        return v;
    }

    @Override
    public void clear() throws CacheException {
        //Constants.REDIS_KEY 为缓存前缀
        //Constants.SESSIONID 为业务会话前缀
        //此处仅清除缓存
        Set<byte[]> keys = redisService.keys("*");
        log.info("清空缓存");
        for (byte[] key : keys) {
            redisService.delete(new String(key));
        }
    }

    @Override
    public int size() {
        log.info("获取Redis缓存数量");
        return redisService.keys("*").size();
    }

    @Override
    public Set<K> keys() {
        log.info("获取Redis所有Key");
        return (Set<K>) redisService.keys("*");
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        
        Set<byte[]> keys = (Set<byte[]>) keys();
        
        for (byte[] key : keys) {
            String k = new String(key);
            values.add(this.get( (K)k ));
        }
        return values;
    }

}
