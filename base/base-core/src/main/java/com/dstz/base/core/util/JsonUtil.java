package com.dstz.base.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * json工具类。
 * <pre>
 * </pre>
 */
public class JsonUtil {

    protected static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 根据键获取值。
     *
     * @param obj
     * @param key
     * @param defaultValue
     * @return String
     */
    public static String getString(JSONObject obj, String key, String defaultValue) {
        if (obj == null || !obj.containsKey(key)) return defaultValue;
        return obj.getString(key);
    }

    /**
     * 根据键获取值。
     *
     * @param obj
     * @param key
     * @return String
     */
    public static String getString(JSONObject obj, String key) {
        return getString(obj, key, "");
    }

    /**
     * 根据键获取int值。
     *
     * @param obj
     * @param key
     * @return int
     */
    public static int getInt(JSONObject obj, String key) {
        if (obj == null || !obj.containsKey(key)) return 0;
        return obj.getIntValue(key);
    }

    /**
     * 根据键获取int值。
     *
     * @param obj
     * @param key
     * @param defaultValue
     * @return int
     */
    public static int getInt(JSONObject obj, String key, int defaultValue) {
        if (obj == null || !obj.containsKey(key)) return defaultValue;
        return obj.getIntValue(key);
    }

    public static boolean getBoolean(JSONObject obj, String key) {
        if (obj == null || !obj.containsKey(key)) return false;
        return obj.getBoolean(key);
    }

    /**
     * 根据键获取boolean值。
     *
     * @param obj
     * @param key
     * @param defaultValue
     * @return boolean
     */
    public static boolean getBoolean(JSONObject obj, String key, boolean defaultValue) {
        if (obj == null || !obj.containsKey(key)) return defaultValue;
        return obj.getBoolean(key);
    }

 

    /**
     * <pre>
     * 优化了JSON.parseObject()方法
     * </pre>
     *
     * @param jsonStr
     * @param cls
     * @return
     */
    public static <T> T parseObject(String jsonStr, Class<T> cls) {
        if (StringUtil.isEmpty(jsonStr)) {
            return null;
        }
        return JSON.parseObject(jsonStr, cls);
    }

    /**
     * <pre>
     * 优化了JSON.parseArray()方法
     * </pre>
     *
     * @param jsonStr
     * @param cls
     * @return
     */
    public static <T> List<T> parseArray(String jsonStr, Class<T> cls) {
        if (StringUtil.isEmpty(jsonStr)) {
            return null;
        }
        return JSON.parseArray(jsonStr, cls);
    }

    /**
     * <pre>
     * 优化了JSON.toJSONString()方法
     * </pre>
     *
     * @param obj
     * @return
     */
    public static String toJSONString(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj);
    }

    public static void main(String[] args) {
        String str = JSON.toJSONString(null);
        System.out.println();
    }
}
