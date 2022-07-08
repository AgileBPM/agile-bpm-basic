package com.dstz.agilebpm.component.j2cache;

/**
 * 缓存常量定义
 *
 * @author wacxhs
 */
public class J2CacheConstant {

    private J2CacheConstant() {
        throw new IllegalStateException();
    }

    /**
     * 默认缓存区域
     */
    public static final String DEFAULT_REGION = "default";

    /**
     * 未启用
     */
    public static final String NONE = "none";

    /**
     * hash
     */
    public static final String HASH = "hash";

    /**
     * L1 前缀
     */
    public static final String L1_PREFIX = "j2cache.L1";

    /**
     * L2 前缀
     */
    public static final String L2_PREFIX = "j2cache.L2";

    /**
     * jcache缓存前缀
     */
    public static final String BROADCAST_PREFIX = "j2cache.broadcast";

    /**
     * 缓存开启
     */
    public static final String CACHE_OPEN = "cache_open";

    /**
     * 缓存清除模式，
     * <ul>
     * <li>active:主动清除，二级缓存过期主动通知各节点清除，优点在于所有节点可以同时收到缓存清除</li>
     * <li>passive:被动清除，一级缓存过期进行通知各节点清除一二级缓存，</li>
     * <li> blend:两种模式一起运作，对于各个节点缓存准确以及及时性要求高的可以使用，正常用前两种模式中一个就可</li>
     * </ul>
     */
    public static final String CACHE_CLEAN_MODE = "cache_clean_mode";

    /**
     * Spring Context
     */
    public static final String SPRING_APPLICATION_CONTEXT = "springApplicationContext";
}
