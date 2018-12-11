package com.dstz.form.model.custdialog;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * <pre>
 * 描述：自定义对话框的返回字段
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月17日 下午8:26:42
 * 版权:summer
 * </pre>
 */
public class FormCustDialogReturnField implements Serializable {
    /**
     * 字段名
     */
    @NotEmpty
    private String columnName;
    /**
     * 返回名
     */
    @NotEmpty
    private String returnName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

}
