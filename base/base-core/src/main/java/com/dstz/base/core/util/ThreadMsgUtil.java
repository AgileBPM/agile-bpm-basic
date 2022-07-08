package com.dstz.base.core.util;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 消息Util工具类,在线程变量中添加消息,消息使用list存放。
 * 
 * @author aschs
 */
public class ThreadMsgUtil {
	private static ThreadLocal<List<String>> localMsg = new ThreadLocal<>();

	/**
	 * 添加消息。
	 *
	 * @param msg
	 */
	public static void addMsg(String msg) {
		List<String> list = localMsg.get();
		if (CollectionUtil.isEmpty(list)) {
			list = new ArrayList<>();
			localMsg.set(list);
		}
		list.add(msg);
	}

	/**
	 * 获取消息数据，并直接清除消息中的数据。
	 *
	 * @return
	 */
	public static List<String> getMsg() {
		return getMsg(true);
	}

	/**
	 * 获取消息数据。
	 *
	 * @param clean
	 * @return
	 */
	public static List<String> getMsg(boolean clean) {
		List<String> list = localMsg.get();
		if (clean) {
			localMsg.remove();
		}
		return list;
	}

	/**
	 * 返回流程消息。
	 *
	 * @return
	 */
	public static String getMessage() {
		return getMessage(true);
	}

	/**
	 * 获取消息。
	 *
	 * @param clean
	 * @return
	 */
	public static String getMessage(boolean clean) {
		return getMessage(clean, "\r\n");
	}

	public static String getMessage(boolean clean, String lineBreak) {
		List<String> list = getMsg(clean);
		if (CollectionUtil.isEmpty(list)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String msg : list) {
			sb.append(msg).append(lineBreak);
		}
		return sb.toString();
	}

	/**
	 * 清除消息。
	 */
	public static void clean() {
		localMsg.remove();
	}
}
