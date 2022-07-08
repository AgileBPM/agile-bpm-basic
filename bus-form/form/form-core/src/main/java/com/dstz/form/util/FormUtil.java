package com.dstz.form.util;

import java.io.File;

import cn.hutool.core.util.ClassUtil;

/**
 * 表单实用函数。
 *
 * @author ray。
 */
public class FormUtil {
    /**
     * 返回模版物理的路径。
     *
     * @return
     * @throws Exception
     */
    public static String getDesignTemplatePath() throws Exception {
        return ClassUtil.getClassPath() + "temp" + File.separator + "design" + File.separator;
    }

}
