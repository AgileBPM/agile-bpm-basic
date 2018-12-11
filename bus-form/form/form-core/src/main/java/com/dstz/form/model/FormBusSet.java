package com.dstz.form.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.api.model.IDModel;


/**
 * <pre>
 * 描述：form_bus_set 实体对象
 * </pre>
 */
public class FormBusSet implements IDModel {

    /**
     * 主键
     */
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * formKey
     */
    @XmlAttribute(name = "formKey")
    protected String formKey;

    /**
     * JavaScript前置脚本
     */
    @XmlElement(name = "jsPreScript")
    protected String jsPreScript;

    /**
     * JavaScript后置脚本
     */
    @XmlElement(name = "jsAfterScript")
    protected String jsAfterScript;

    /**
     * 保存前置脚本
     */
    @XmlElement(name = "preScript")
    protected String preScript;

    /**
     * 保存后置脚本
     */
    @XmlElement(name = "afterScript")
    protected String afterScript;

    /**
     * 展示前Java脚本
     */
    @XmlElement(name = "showScript")
    protected String showScript;

    /**
     * 是否树形列表
     */
    @XmlAttribute(name = "isTreeList")
    protected Short isTreeList;

    /**
     * {idKey:"idKEY名称",pIdKey:"",name:"显示名称",title,rootPid}
     */
    @XmlElement(name = "treeConf")
    protected String treeConf;


    public void setId(String id) {
        this.id = id;
    }

    /**
     * 返回 主键
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    /**
     * 返回 formKey
     *
     * @return
     */
    public String getFormKey() {
        return this.formKey;
    }

    public void setJsPreScript(String jsPreScript) {
        this.jsPreScript = jsPreScript;
    }

    /**
     * 返回 JavaScript前置脚本
     *
     * @return
     */
    public String getJsPreScript() {
        return this.jsPreScript;
    }

    public void setJsAfterScript(String jsAfterScript) {
        this.jsAfterScript = jsAfterScript;
    }

    /**
     * 返回 JavaScript后置脚本
     *
     * @return
     */
    public String getJsAfterScript() {
        return this.jsAfterScript;
    }

    public void setPreScript(String preScript) {
        this.preScript = preScript;
    }

    /**
     * 返回 保存前置脚本
     *
     * @return
     */
    public String getPreScript() {
        return this.preScript;
    }

    public void setAfterScript(String afterScript) {
        this.afterScript = afterScript;
    }

    /**
     * 返回 保存后置脚本
     *
     * @return
     */
    public String getAfterScript() {
        return this.afterScript;
    }

    public String getShowScript() {
        return showScript;
    }

    public void setShowScript(String showScript) {
        this.showScript = showScript;
    }

    public void setIsTreeList(Short isTreeList) {
        this.isTreeList = isTreeList;
    }

    /**
     * 返回 是否树形列表
     *
     * @return
     */
    public Short getIsTreeList() {
        return this.isTreeList;
    }

    public void setTreeConf(String treeConf) {
        this.treeConf = treeConf;
    }

    /**
     * 返回 {idKey:"idKEY名称",pIdKey:"",name:"显示名称",title,rootPid}
     *
     * @return
     */
    public String getTreeConf() {
        return this.treeConf;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("formKey", this.formKey)
                .append("jsPreScript", this.jsPreScript)
                .append("jsAfterScript", this.jsAfterScript)
                .append("preScript", this.preScript)
                .append("afterScript", this.afterScript)
                .append("isTreeList", this.isTreeList)
                .append("treeConf", this.treeConf)
                .toString();
    }
}