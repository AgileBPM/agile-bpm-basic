package com.dstz.form.model.custdialog;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * <pre>
 * 描述：自定义对话框的条件字段
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月17日 下午8:26:42
 * 版权:summer
 * </pre>
 */
public class FormCustDialogConditionField implements Serializable {
    /**
     * 字段名
     */
    @NotEmpty
    private String columnName;
    /**
     * 字段的类型，枚举在com.dstz.base.db.model.Column.TYPE
     */
    @NotEmpty
    private String dbType;
    /**
     * 显示名
     */
    @NotEmpty
    private String showName;
    /**
     * 条件
     */
    @NotEmpty
    private String condition;
    /**
     * 值来源 枚举 FormCustDialogConditionFieldValueSource
     */
    @NotEmpty
    private String valueSource;
    /**
     * 值
     */
    @NotEmpty
    private Value value;

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getValueSource() {
        return valueSource;
    }

    public void setValueSource(String valueSource) {
        this.valueSource = valueSource;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * <pre>
     * 描述：值对象
     * 作者:aschs
     * 邮箱:aschs@qq.com
     * 日期:2018年1月24日 下午5:20:30
     * 版权:summer
     * </pre>
     */
    public static class Value implements Serializable {
        /**
         * 控件类型
         */
        private String ctrlType;
        /**
         * 控件key
         */
        private String ctrlKey;
        /**
         * 控件返回
         */
        private String ctrlReturn;
        /**
         * 文本
         */
        private String text;

        public String getCtrlType() {
            return ctrlType;
        }

        public void setCtrlType(String ctrlType) {
            this.ctrlType = ctrlType;
        }

        public String getCtrlKey() {
            return ctrlKey;
        }

        public void setCtrlKey(String ctrlKey) {
            this.ctrlKey = ctrlKey;
        }

        public String getCtrlReturn() {
            return ctrlReturn;
        }

        public void setCtrlReturn(String ctrlReturn) {
            this.ctrlReturn = ctrlReturn;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }
}
