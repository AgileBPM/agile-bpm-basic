package com.dstz.form.model;

import java.io.Serializable;

/**
 * <pre>
 * 描述：表单模版 entity对象
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月8日 上午9:27:16
 * 版权:summer
 * </pre>
 */
public class FormTemplate implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 别名
     */
    private String key;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 类型 枚举 FormTemplateType
     */
    private String type;
    
    /**
     * formType 表单的分类  pc、mobile、vuepc 等不同的表单分类  默认 pc
     */
    private String formType;
    /**
     * html内容
     */
    private String html;
    /**
     * 是否可以编辑 不可编辑也相当于系统内置
     */
    private boolean editable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

}