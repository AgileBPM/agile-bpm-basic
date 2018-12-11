package com.dstz.base.core.cache;

/**
 * 缓存操作接口。
 * 定义了增加缓存，删除缓存，清除缓存，读取缓存接口。
 */
public interface ICache<T extends Object> {
    /**
     * 添加缓存。
     *
     * @param key
     * @param obj
     * @param timeout
     */
    void add(String key, T obj, int timeout);

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
}