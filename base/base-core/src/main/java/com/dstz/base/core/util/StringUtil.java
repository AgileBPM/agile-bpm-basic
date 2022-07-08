package com.dstz.base.core.util;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.util.ArrayUtil;

/**
 * 字符串工具类
 */
public class StringUtil {
	/**
	 * 逗号分隔
	 * @param strs
	 * @return
	 */
	public static String join(Iterable<String> strs) {
		return join(strs,",");
	}
	
	public static String join(Iterable<String> iterable,String split) {
		StringBuilder sb = new StringBuilder();
		for (String str : iterable) {
			sb.append(str);
			sb.append(split);
		}
		return sb.substring(0, sb.length() - split.length());
		
	}

	 

	/**
	 * 把字符串的第一个字母转为大写
	 *
	 * @param str字符串
	 * @return
	 */
	public static String upperFirst(String str) {
		return toFirst(str, true);
	}

	/**
	 * 判断字符串非空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断字符串是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		
		if (str == null) return true;
		
		if (str.trim().equals("")) return true;
		
		return false;
	}

	/**
	 * 为空判断,0做空处理。
	 * 
	 * <pre>
	 * 这里判断：
	 * 1.字符串为NULL
	 * 2.字符串为空串。
	 * 3.字符串为0。
	 * </pre>
	 *
	 * @param tmp
	 * @return boolean
	 */
	public static boolean isZeroEmpty(String tmp) {
		boolean isEmpty = StringUtil.isEmpty(tmp);
		if (isEmpty) return true;
		
		return "0".equals(tmp);
	}

	/**
	 * 非空判断。
	 *
	 * @param tmp
	 * @return boolean
	 */
	public static boolean isNotZeroEmpty(String tmp) {
		return !isZeroEmpty(tmp);
	}

	/**
	 * 把字符串的第一个字母转为小写
	 *
	 * @param str
	 * @return
	 */
	public static String lowerFirst(String str) {
		return toFirst(str, false);
	}

	/**
	 * 把字符串的第一个字母转为大写或者小写
	 *
	 * @param str
	 *            字符串
	 * @param isUpper
	 *            是否大写
	 * @return
	 */
	public static String toFirst(String str, boolean isUpper) {
		if (StringUtils.isEmpty(str))
			return "";
		char first = str.charAt(0);
		String firstChar = new String(new char[] { first });
		firstChar = isUpper ? firstChar.toUpperCase() : firstChar.toLowerCase();
		return firstChar + str.substring(1);
	}
 
	/**
	 * 将数据库字段名转为DataGrid字段名 isIgnoreFirst:是否忽略第一个字段不转大写
	 *
	 * @return
	 */
	public static String convertDbFieldToField(String dbField) {
		return convertDbFieldToField(dbField, "_", true);
	}

	/**
	 * 将数据库字段名转为DataGrid字段名,如 sys_data_ 转为sysData symbol:间隔符号
	 * isIgnoreFirst：是否忽略第一个单词的首字母转大写
	 *
	 * @return
	 */
	public static String convertDbFieldToField(String dbField, String symbol, boolean isIgnoreFirst) {
		StringBuilder result = new StringBuilder();
		if (dbField.startsWith(symbol)) {
			dbField = dbField.substring(1);
		}
		if (dbField.endsWith(symbol)) {
			dbField = dbField.substring(0, dbField.length() - 1);
		}
		String[] arr = dbField.split(symbol);
		for (int i = 0; i < arr.length; i++) {
			String str = arr[i];
			if (isIgnoreFirst && i != 0) {
				char oldChar = str.charAt(0);
				char newChar = (oldChar + "").toUpperCase().charAt(0);
				str = newChar + str.substring(1);
			}
			result.append(str);
		}
		return result.toString();
	}

	public static String[] getStringAryByStr(String str) {
		if (StringUtil.isEmpty(str)) {
			Collections.emptyList();
		}

		return str.split(",");
	}

	/**
	 * 数组合并。
	 *
	 * @param vals
	 * @param separator
	 * @return
	 */
	public static String join(String[] vals, String separator) {
		if (ArrayUtil.isEmpty(vals))
			return "";
		String val = "";
		for (int i = 0; i < vals.length; i++) {
			if (i == 0) {
				val += vals[i];
			} else {
				val += separator + vals[i];
			}
		}
		return val;
	}

	/**
	 * 对字符串去掉前面的指定字符
	 *
	 * @param content
	 *            待处理的字符串
	 * @param suffix
	 *            要去掉后面的指定字符串
	 * @return
	 */
	public static String trimSuffix(String content, String suffix) {
		String resultStr = content;
		while (resultStr.endsWith(suffix)) {
			resultStr = resultStr.substring(0, resultStr.length() - suffix.length());
		}
		return resultStr;
	}

	/**
	 * 对字符串去掉前面的指定字符
	 *
	 * @param content
	 *            待处理的字符串
	 * @param prefix
	 *            要去掉前面的指定字符串
	 * @return
	 */
	public static String trimPrefix(String content, String prefix) {
		String resultStr = content;
		while (resultStr.startsWith(prefix)) {
			resultStr = resultStr.substring(prefix.length());
		}
		return resultStr;
	}
}
