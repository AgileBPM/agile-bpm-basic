package com.dstz.bus.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.util.AppUtil;

/**
 * <pre>
 * 描述：BusinessObject的缓存工具类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年10月14日
 * 版权:summer
 * </pre>
 */
public class BusinessObjectCacheUtil {
	private static final String BUSINESS_OBJECT_DATASOURCE_KEYS_MAP = "businessObjectDataSourceKeysMap";

	private BusinessObjectCacheUtil() {

	}
	
	/**
	 * <pre>
	 * 缓存bo的数据源别名set
	 * </pre>	
	 * @param boKey
	 * @param dsKeys
	 */
	public static void putDataSourcesKeys(String boKey, Set<String> dsKeys) {
		Map<String, Set<String>> map = (Map<String, Set<String>>)  AppUtil.getBean(ICache.class).getByKey(BUSINESS_OBJECT_DATASOURCE_KEYS_MAP);
		if (map == null) {
			map = new HashMap<>();
		}
		map.put(boKey, dsKeys);
		 AppUtil.getBean(ICache.class).add(BUSINESS_OBJECT_DATASOURCE_KEYS_MAP, map);
	}
	
	/**
	 * <pre>
	 * 拿出缓存中bo数据源别名set
	 * </pre>	
	 * @param boKey
	 * @return
	 */
	public static Set<String> getDataSourcesKeys(String boKey) {
		Map<String, Set<String>> map = (Map<String, Set<String>>) AppUtil.getBean(ICache.class).getByKey(BUSINESS_OBJECT_DATASOURCE_KEYS_MAP);
		if (map == null) {
			return null;
		}
		return map.get(boKey);
	}
}
