package com.dstz.base.core.cache;

/**
 * 缓存操作接口。
 * 定义了增加缓存，删除缓存，清除缓存，读取缓存接口。
 *
 * @author jeff
 */
public interface ICache<T extends Object> {
    /**
     * 添加缓存。 建议规范所有业务 均预先定义好超时时间
     * @deprecated
     * @param key
     * @param obj
     * @param timeToLiveInSeconds
     */
    void add(String key, T obj, int timeToLiveInSeconds);

    /**
     * 添加缓存。
     *
     * @param key
     * @param obj
     */
    void add(String key, T obj);

    /**
     * 根据键删除缓存
     *
     * @param key
     */
    void delByKey(String key);

    /**
     * 清除所有的缓存
     */
    void clearAll();

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    T getByKey(String key);

    /**
     * 包含key。
     *
     * @param key
     * @return
     */
    boolean containKey(String key);
    
    
    /**
     * 对应的区域需要在配置  caffeine.region.[name] = size, xxxx[s|m|h|d]
     */
    
    /** 在区域内添加缓存 */
    void add2Region(String region,String key, T obj);
    
    /** 获取区域内缓存 */
    T getByKey(String region,String key);
    
    /** 清除区域缓存 */
    void clearRegion(String region);
    
    /** 根据键删除缓存 */
    void delByKey(String region,String key);
    
    /** 判断区域是否存在key **/
    boolean containKey(String region,String key);
}