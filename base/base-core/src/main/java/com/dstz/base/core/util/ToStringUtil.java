package com.dstz.base.core.util;

import java.lang.reflect.Method;

/**
 * <pre>
 * 描述：类转字符串工具，字符串格式如下：
 * 类名[字段a:a,字段b:b,...]
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月8日 上午9:37:48
 * 版权:summer
 * </pre>
 */
public class ToStringUtil {
    @Override
    public String toString() {
        return toString(this);
    }

    /**
     * <pre>
     * 转成字符串
     * </pre>
     *
     * @param c
     * @return
     */
    public static <C> String toString(C c) {
        Class<?> cls = c.getClass();
        if (c instanceof String) {// 是字符串本身则结束
            return c + "";
        }
        try {
            Method toStringMethod = cls.getMethod("toString");// 没有toString方法 则是基础类型
            if (toStringMethod == null) {
                return c + "";
            }
        } catch (Exception e) {
        }

        StringBuilder sb = new StringBuilder();
        sb.append("" + cls.getSimpleName() + " [");

        for (Method method : cls.getMethods()) {
            // 获取get方法
            if (!method.getName().startsWith("get") || method.getParameters().length != 0 || "getClass".equals(method.getName())) {
                continue;
            }
            try {
                Object obj = method.invoke(c);
                String str = "";
                if (obj != null) {
                    str = obj + "";
                }
                if (!sb.toString().endsWith("[")) {
                    sb.append(", ");
                }
                sb.append(StringUtil.toFirst(method.getName().replace("get", ""), false) + "=" + str);
            } catch (Exception e) {
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
