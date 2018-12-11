package com.dstz.form.model.custdialog;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * <pre>
 * 描述：自定义对话框列表数据时的展示字段
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月17日 下午8:26:42
 * 版权:summer
 * </pre>
 */
public class FormCustDialogDisplayField implements Serializable {
    /**
     * 字段名
     */
    @NotEmpty
    private String columnName;
    /**
     * 显示名
     */
    @NotEmpty
    private String showName;
    /**
     * 格式化
     */
    private String formatter;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

}
