package com.dstz.sys.api.redis;

import java.util.Set;

public interface IRedisService {

    /**
     * @param keyo
     */
    public long del(String... keys);

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte[] key, byte[] value, long liveTime);

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key, String value, long liveTime);

    /**
     * @param key
     * @param value
     */
    public void set(String key, String value);

    public void set(String key, Object object);

    public void set(String key, Object object, long liveTime);

    /**
     * @param key
     * @param value
     */
    public void set(String key, byte[] value);

    /**
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value);

    /**
     * @param key
     * @return
     */
    public String get(String key);

    public Object getObject(String key);

    /**
     * @param key
     * @return
     */
    public byte[] getBytes(String key);

    /**
     * @param key
     * @return
     */
    public String get(String key, String charsetName);

    /**
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern);

    /**
     * @param key
     * @return
     */
    public boolean exists(String key);

    /**
     * @return
     */
    public String flushDB();

    /**
     * @return
     */
    public long dbSize();

    /**
     * @return
     */
    public String ping();

}