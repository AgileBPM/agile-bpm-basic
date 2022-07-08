package com.dstz.base.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * <pre>
 * 描述：线程map的工具类
 * 让开发员随时放自己想要的东西到线程变量中
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年5月13日
 * 版权:summer
 * </pre>
 */
public class ThreadMapUtil {
	private static ThreadLocal<Map<String, Object>> threadLocalMap = new ThreadLocal<>();
	
	private ThreadMapUtil() {

	}

	private static Map<String, Object> map() {
		Map<String, Object> map = threadLocalMap.get();
		if (map == null) {
			threadLocalMap.set(new ConcurrentHashMap<String, Object>());
			map = threadLocalMap.get();
		}
		return map;
	}

	public static void put(String key, Object value) {
		map().put(key, value);
	}

	public static Object get(String key) {
		return map().get(key);
	}

	public static void remove(String key) {
		map().remove(key);
		if(map().isEmpty()) {
			threadLocalMap.remove();
		}
	}
	
	/**
	 * <pre>
	 * 清除线程变量
	 * </pre>
	 */
	public static void remove() {
		threadLocalMap.remove();
	}
	
	public static Object getOrDefault(String key, Object defaultValue) {
		return map().getOrDefault(key, defaultValue);
	}
	
	/**
	 * <pre>
	 * 获取某个值，为空时创建mappingFunction
	 * </pre>	
	 * @param key
	 * @param mappingFunction
	 * @return
	 */
	public static Object computeIfAbsent(String key, Function<? super String, ? extends Object> mappingFunction) {
		return map().computeIfAbsent(key, mappingFunction);
	}
}
