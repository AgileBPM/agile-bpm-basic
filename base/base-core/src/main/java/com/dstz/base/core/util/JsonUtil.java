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
     * 判断是jsonArray是否为空
     *
     * @param jsonArrStr
     * @return
     */
    public static boolean isNotEmptyJsonArr(String jsonArrStr) {
        return !isEmptyJsonArr(jsonArrStr);
    }

    /**
     * 判断是jsonArray是否为空
     *
     * @param jsonArrStr
     * @return
     */
    public static boolean isEmptyJsonArr(String jsonArrStr) {
        if (StringUtil.isEmpty(jsonArrStr))
            return true;
        try {
            JSONArray jsonAry = JSONArray.parseArray(jsonArrStr);
            return jsonAry.size() > 0 ? false : true;
        } catch (Exception e) {
            LOGGER.debug(e.toString());
            return true;
        }
    }

    /**
     * 替换掉包含富文本的json 字符串中特殊的字符
     *
     * @param str
     * @return
     */
    public static String escapeSpecialChar(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {

            char c = str.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 删除的空项，主要controller请求返回的时候 如果数据有{a:null}换转换失败
     *
     * @param jsonObject void
     * @throws
     * @since 1.0.0
     */
    public static void removeNull(JSONObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            Object val = jsonObject.get(key);
            if (val == null) {
                jsonObject.put(key, "");
            }
        }
    }

    /**
     * 删除的空项，主要controller请求返回的时候 如果数据有{a:null}换转换失败
     *
     * @param jsonArray void
     * @throws
     * @since 1.0.0
     */
    public static void removeNull(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            removeNull(jsonArray.getJSONObject(i));
        }
    }

    /**
     * <pre>
     * JSONArray转成JSONObject
     * eg:
     * [{id:"1",name:"a"},{id:"2",name:"b"}] 当keyName设置为id是，转换成
     * {1:{id:"1",name:"a"},2:{id:"2",name:"b"}}
     * </pre>
     *
     * @param jsonArray
     * @param keyName   :以哪个字段为key
     * @return JSONObject
     * @throws
     * @since 1.0.0
     */
    public static JSONObject arrayToObject(JSONArray jsonArray, String keyName) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            jsonObject.put(temp.getString(keyName), temp);
        }
        return jsonObject;
    }

    /**
     * <pre>
     * 把jsonObject 转到jsonArray,通常用于以下这种情况
     * 为了保证jsonArray里的某个值是唯一的所以先用jsonObject来保存着
     * eg:
     * {a:{id:1,name:a},b:{id:2,name:b}}
     * 转成：[{id:1,name:a},{id:2,name:b}]
     * </pre>
     *
     * @param jsonObject
     * @return JSONArray
     * @throws
     * @since 1.0.0
     */
    public static JSONArray objectToArray(JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray();
        for (Object key : jsonObject.keySet()) {
            jsonArray.add(jsonObject.get(key));
        }
        return jsonArray;
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
