package com.dstz.agilebpm.component.j2cache;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2CacheBuilder;
import net.oschina.j2cache.J2CacheConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.Map;

/**
 * cacheChannel bean工厂
 *
 * @author wacxhs
 */
public class CacheChannelFactoryBean implements FactoryBean<CacheChannel> {

    @Autowired
    private StandardEnvironment environment;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public CacheChannel getObject() throws Exception {
        J2CacheConfig config = new J2CacheConfig();
        config.setSerialization(environment.getProperty("j2cache.serialization"));
        config.setBroadcast(environment.getProperty("j2cache.broadcast"));
        config.setL1CacheName(environment.getProperty("j2cache.L1.provider_class"));
        config.setL2CacheName(environment.getProperty("j2cache.L2.provider_class"));
        config.setSyncTtlToRedis(!BooleanUtils.toBoolean(environment.getProperty("j2cache.sync_ttl_to_redis")));
        config.setDefaultCacheNullObject(BooleanUtils.toBoolean(environment.getProperty("j2cache.default_cache_null_object")));
        config.getL1CacheProperties().put(J2CacheConstant.SPRING_APPLICATION_CONTEXT, applicationContext);
        config.getL2CacheProperties().put(J2CacheConstant.SPRING_APPLICATION_CONTEXT, applicationContext);
        config.getBroadcastProperties().put(J2CacheConstant.SPRING_APPLICATION_CONTEXT, applicationContext);
        environment.getPropertySources().forEach(a -> {
            if (a instanceof MapPropertySource) {
                MapPropertySource c = (MapPropertySource) a;
                for (Map.Entry<String, Object> propertySourceEntry : c.getSource().entrySet()) {
                    if (propertySourceEntry.getKey().startsWith(J2CacheConstant.L1_PREFIX)) {
                        config.getL1CacheProperties().put(propertySourceEntry.getKey().substring(J2CacheConstant.L1_PREFIX.length() + 1), propertySourceEntry.getValue());
                    } else if (propertySourceEntry.getKey().startsWith(J2CacheConstant.L2_PREFIX)) {
                        config.getL2CacheProperties().put(propertySourceEntry.getKey().substring(J2CacheConstant.L2_PREFIX.length() + 1), propertySourceEntry.getValue());
                    } else if (propertySourceEntry.getKey().startsWith(J2CacheConstant.BROADCAST_PREFIX)) {
                        String key = StringUtils.substring(propertySourceEntry.getKey(), J2CacheConstant.BROADCAST_PREFIX.length() + 1);
                        if (StringUtils.isNotEmpty(key)) {
                            config.getBroadcastProperties().put(key, propertySourceEntry.getValue());
                        }

                    }
                }
            }
        });
        config.getBroadcastProperties().putAll(config.getL1CacheProperties());
        config.getBroadcastProperties().putAll(config.getL2CacheProperties());
        return J2CacheBuilder.init(config).getChannel();
    }

    @Override
    public Class<?> getObjectType() {
        return CacheChannel.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
