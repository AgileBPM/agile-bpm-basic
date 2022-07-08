package com.dstz.bus.util;

import java.util.HashMap;
import java.util.Map;

import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.util.AppUtil;
import com.dstz.bus.model.BusinessTable;

/**
 * <pre>
 * 描述：BusinessTable的缓存工具类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年5月15日
 * 版权:summer
 * </pre>
 */
public class BusinessTableCacheUtil {
	private static String businessTableMap = "businessTableMap";

	private BusinessTableCacheUtil() {

	}

	public static void put(BusinessTable businessTable) {
		Map<String, BusinessTable> map = (Map<String, BusinessTable>) AppUtil.getBean(ICache.class).getByKey(businessTableMap);
		if (map == null) {
			map = new HashMap<>();
		}
		map.put(businessTable.getKey(), businessTable);
		AppUtil.getBean(ICache.class).add(businessTableMap, map);
	}

	public static BusinessTable get(String key) {
		Map<String, BusinessTable> map = (Map<String, BusinessTable>) AppUtil.getBean(ICache.class).getByKey(businessTableMap);
		if (map == null) {
			return null;
		}
		return map.get(key);
	}

	public static void clean() {
		Map<String, BusinessTable> map = (Map<String, BusinessTable>) AppUtil.getBean(ICache.class).getByKey(businessTableMap);
		if (map == null) {
			return;
		}
		map.clear();
	}
}
