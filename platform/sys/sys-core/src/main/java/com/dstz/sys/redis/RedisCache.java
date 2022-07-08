package com.dstz.sys.redis;

import com.dstz.base.api.exception.SerializeException;
import com.dstz.base.core.cache.ICache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis缓存实现
 *
 * @author jeff
 */
@SuppressWarnings("unchecked")
public class RedisCache<T extends Object> implements ICache<T> {

    private Logger logger = LoggerFactory.getLogger(RedisCache.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public synchronized void add(String key, T obj) {
        redisTemplate.boundValueOps(key).set(obj);
        logger.info("redis add " + key);
    }

    @Override
    public synchronized void add(String key, T obj, int timeout) {
        redisTemplate.boundValueOps(key).set(obj, timeout);
        logger.info("redis add " + key + " timeout " + timeout);
    }

    @Override
    public synchronized void delByKey(String key) {
        redisTemplate.delete(key);
        logger.info("redis delByKey " + key);
    }

    @Override
    public void clearAll() {
        redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
            redisConnection.flushDb();
            return null;
        });
        logger.info("redis flushDB");
    }

    @Override
    public synchronized T getByKey(String key) {
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (SerializeException e) {
            this.delByKey(key);
            logger.warn("获取缓存对象失败，反序列化失败，已经删除缓存：{}", key, e);
        }
        return null;
    }

    @Override
    public boolean containKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


    @Override
    public void add2Region(String region, String key, T obj) {
        this.add(key, obj);
    }

    @Override
    public T getByKey(String region, String key) {
        return this.getByKey(key);
    }

    @Override
    public void clearRegion(String region) {

    }

    @Override
    public void delByKey(String region, String key) {
        this.delByKey(key);
    }

    @Override
    public boolean containKey(String region, String key) {
        return this.containKey(key);
    }
}
