package com.dstz.base.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 错误信息处理类。
 *
 * @author jeff
 */
public class ExceptionUtil {


    /**
     * 获取exception的详细错误信息。
     *
     * @param e
     * @return
     */
    public static String getExceptionMessage(Throwable e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();

        return str;
    }

    public static String getRootErrorMseeage(Exception e) {
        Throwable root = ExceptionUtils.getRootCause(e);
        root = (root == null ? e : root);

        if (root == null) return "";

        String msg = root.getMessage();
        
        if(msg == null) return "null";
        
        return StringUtils.defaultString(msg);
    }
}
