package com.dstz.sys.redis;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dstz.base.core.cache.ICache;
import com.dstz.sys.api.redis.IRedisService;

/**
 * Redis缓存实现
 */
public class RedisCache<T extends Object> implements ICache<T> {

    private Logger logger = LoggerFactory.getLogger(RedisCache.class);

    @Resource
    private IRedisService redisService;

    @Override
    public synchronized void add(String key, T obj) {
        redisService.set(key, obj);
        logger.info("redis add " + key);
    }

    @Override
    public synchronized void add(String key, T obj, int timeout) {
        redisService.set(key, obj, timeout);
        logger.info("redis add " + key+" timeout "+ timeout);
    }

    @Override
    public synchronized void delByKey(String key) {
        redisService.del(key);
        logger.info("redis delByKey " + key);
    }

    @Override
    public void clearAll() {
        redisService.flushDB();
        logger.info("redis flushDB");
    }

    @Override
    public synchronized T getByKey(String key) {
        Object obj = redisService.getObject(key);
        return (T) obj;
    }

    @Override
    public boolean containKey(String key) {
        return redisService.exists(key);
    }
}
