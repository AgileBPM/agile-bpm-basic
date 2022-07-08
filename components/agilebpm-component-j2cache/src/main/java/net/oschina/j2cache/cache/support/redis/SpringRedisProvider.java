package net.oschina.j2cache.cache.support.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.dstz.agilebpm.component.j2cache.J2CacheConstant;
import net.oschina.j2cache.*;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class SpringRedisProvider implements CacheProvider {

    private static final Logger logger = LoggerFactory.getLogger(SpringRedisProvider.class);

    private RedisTemplate<String, Serializable> redisTemplate;

    private boolean l2CacheOpen;

    private String namespace;

    private String storage;

    protected ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>();

    @Override
    public String name() {
        return "redis";
    }

    @Override
    public int level() {
        return CacheObject.LEVEL_2;
    }

    @Override
    public Collection<CacheChannel.Region> regions() {
        return Collections.emptyList();
    }

    @Override
    public Cache buildCache(String region, CacheExpiredListener listener) {
        //如果二级缓存不开启，则不走
        if (!l2CacheOpen) {
            return new NullCache();
        }
        Cache cache = caches.get(region);
        if (cache == null) {
            synchronized (SpringRedisProvider.class) {
                cache = caches.get(region);
                if (cache == null) {
                    if (J2CacheConstant.HASH.equalsIgnoreCase(this.storage)) {
                        cache = new SpringRedisCache(this.namespace, region, redisTemplate);
                    } else {
                        cache = new SpringRedisGenericCache(this.namespace, region, redisTemplate);
                    }
                    caches.put(region, cache);
                }
            }
        }
        return cache;
    }

    @Override
    public Cache buildCache(String region, long timeToLiveInSeconds, CacheExpiredListener listener) {
        return buildCache(region, listener);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Properties props) {
        this.namespace = props.getProperty("namespace");
        this.storage = props.getProperty("storage");
        this.l2CacheOpen = BooleanUtils.toBoolean(props.getProperty(J2CacheConstant.CACHE_OPEN));
        logger.debug("{}={}", J2CacheConstant.CACHE_OPEN, BooleanUtils.toStringTrueFalse(this.l2CacheOpen));
        if (!l2CacheOpen) {
            return;
        }
        ApplicationContext applicationContext = (ApplicationContext) props.get(J2CacheConstant.SPRING_APPLICATION_CONTEXT);
        Map<String, RedisTemplate> redisTemplateMap = applicationContext.getBeansOfType(RedisTemplate.class);
        Assert.isTrue(CollectionUtil.isNotEmpty(redisTemplateMap), "not found RedisTemplate bean");
        this.redisTemplate = CollectionUtil.getFirst(redisTemplateMap.values());
    }

    @Override
    public void stop() {
        // 由spring控制
    }

}
