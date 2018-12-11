package com.dstz.base.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息Util工具类,在线程变量中添加消息,消息使用list存放。
 * @author aschs
 */
public class ThreadMsgUtil {
    private static ThreadLocal<List<String>> localMsg = new ThreadLocal<List<String>>();

    /**
     * 添加消息。
     *
     * @param msg
     */
    public static void addMsg(String msg) {
        List<String> list = localMsg.get();
        if (BeanUtils.isEmpty(list)) {
            list = new ArrayList<String>();
            list.add(msg);
            localMsg.set(list);
        } else {
            list.add(msg);
        }
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
    	return	getMessage(clean, "\r\n");
    }
    
    public static String getMessage(boolean clean,String lineBreak) {
        List<String> list = getMsg(clean);
        String str = "";
        if (BeanUtils.isEmpty(list)) {
            return str;
        }
        for (String msg : list) {
            str += msg + lineBreak;
        }
        return str;
    }

    /**
     * 清除消息。
     */
    public static void clean() {
        localMsg.remove();
    }
}
