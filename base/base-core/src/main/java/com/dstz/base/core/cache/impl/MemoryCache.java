package com.dstz.base.core.cache.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dstz.base.core.cache.ICache;

/**
 * 内存的cache实现。
 */
public class MemoryCache<T> implements ICache<T> {
    private Logger logger = LoggerFactory.getLogger(MemoryCache.class);

    private Map<String, T> map = new ConcurrentHashMap<String, T>();

    public void add(String key, T obj) {
    	if(key == null) return;
        map.put(key, obj);
        logger.info("MemoryCache add " + key);
    }

    public void delByKey(String key) {
    	if(key == null) return;
        map.remove(key);
        logger.info("MemoryCache delByKey " + key);
    }

    public void clearAll() {
        map.clear();
        logger.info("MemoryCache clearAll");
    }

    public T getByKey(String key) {
    	if(key == null) return null;
        return map.get(key);
    }
    
    
    public boolean containKey(String key) {
    	if(key == null) return false;
        return map.containsKey(key);
    }

    public void add(String key, T obj, int timeout) {
    	
    }

}
