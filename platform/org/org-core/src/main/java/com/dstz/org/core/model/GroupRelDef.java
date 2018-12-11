package com.dstz.org.core.model;

import com.dstz.base.core.model.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * 组织关系定义
 */
public class GroupRelDef extends BaseModel {
    /**
     * 名称
     */
    protected String name;

    /**
     * 编码
     */
    protected String code;

    /**
     * 职务级别
     */
    protected String postLevel;

    /**
     * 描述
     */
    protected String description;


    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回 名称
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 返回 编码
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel;
    }

    /**
     * 返回 职务级别
     *
     * @return
     */
    public String getPostLevel() {
        return this.postLevel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 返回 描述
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("name", this.name)
                .append("code", this.code)
                .append("postLevel", this.postLevel)
                .append("description", this.description)
                .toString();
    }
}