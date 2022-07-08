package com.dstz.agilebpm.component.j2cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dstz.base.core.cache.ICache;

import cn.hutool.core.lang.Assert;
import net.oschina.j2cache.CacheChannel;

/**
 * j2cache缓存实现
 *
 * @author wacxhs
 */
public class J2CacheImpl implements ICache {

    private Logger logger = LoggerFactory.getLogger(J2CacheImpl.class);

    @Autowired
    private CacheChannel cacheChannel;

    @Deprecated
    @Override
    public void add(String key, Object obj, int timeToLiveInSeconds) {
        cacheChannel.set(J2CacheConstant.DEFAULT_REGION, key, obj, timeToLiveInSeconds);
    }

    @Override
    public void add(String key, Object obj) {
        this.add2Region(J2CacheConstant.DEFAULT_REGION, key, obj);
    }

    @Override
    public void delByKey(String key) {
        this.delByKey(J2CacheConstant.DEFAULT_REGION, key);
        cacheChannel.evict(J2CacheConstant.DEFAULT_REGION, key);
    }

    @Override
    public void clearAll() {
        cacheChannel.regions().forEach(region -> {
            cacheChannel.clear(region.getName());
            logger.info("J2Cache Region:{} clear !", region.getName());
        });
    }

    @Override
    public Object getByKey(String key) {
        return this.getByKey(J2CacheConstant.DEFAULT_REGION, key);
    }

    @Override
    public boolean containKey(String key) {
        return this.containKey(J2CacheConstant.DEFAULT_REGION, key);
    }


    @Override
    public void add2Region(String region, String key, Object obj) {
        Assert.notBlank(region);
        Assert.notBlank(key);

        cacheChannel.set(region, key, obj);
    }


    @Override
    public Object getByKey(String region, String key) {
        Assert.notBlank(region);
        Assert.notBlank(key);
        return cacheChannel.get(region, key).getValue();
    }


    @Override
    public void clearRegion(String region) {
        Assert.notBlank(region);
        cacheChannel.clear(region);
    }


    @Override
    public void delByKey(String region, String key) {
        Assert.notBlank(region);
        Assert.notBlank(key);

        cacheChannel.evict(region, key);
    }


    @Override
    public boolean containKey(String region, String key) {
        Assert.notBlank(region);
        Assert.notBlank(key);

        return cacheChannel.exists(region, key);
    }
}
