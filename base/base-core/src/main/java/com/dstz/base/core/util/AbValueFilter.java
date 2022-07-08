package com.dstz.base.core.util;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * <pre>
 * 描述：ab项目解决前端long精度缺失问题
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2019年7月13日
 * 版权:summer
 * </pre>
 */
public class AbValueFilter implements ValueFilter {

	@Override
	public Object process(Object object, String name, Object value) {
		if (value instanceof Long) {
			Long l = (Long) value;
			return l > 9007199254740992l ? l.toString() : l;
		}
		return value;
	}

	public static void main(String[] args) {
	}
}
