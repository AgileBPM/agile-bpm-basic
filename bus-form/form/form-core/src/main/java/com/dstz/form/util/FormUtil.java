package com.dstz.form.util;

import com.dstz.base.core.util.FileUtil;

import java.io.File;

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
        return FileUtil.getClassesPath() + "template" + File.separator + "design" + File.separator;
    }

}
