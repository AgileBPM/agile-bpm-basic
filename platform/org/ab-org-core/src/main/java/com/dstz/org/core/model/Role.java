package com.dstz.org.core.model;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.core.model.BaseModel;
import com.dstz.org.api.constant.GroupTypeConstant;
import com.dstz.org.api.model.IGroup;


/**
 * <pre>
 * 描述：角色管理 实体对象
 * </pre>
 */
public class Role extends BaseModel implements IGroup {
    /**
     * 角色名称
     */
    protected String name;

    /**
     * 角色别名
     */
    protected String alias;

    /**
     * 0：禁用，1：启用
     */
    protected Integer enabled = 1;

    /**
     * 角色描述
     */
    protected String description = "";


    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回 角色名称
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 返回 角色别名
     *
     * @return
     */
    public String getAlias() {
        return this.alias;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    /**
     * 返回 0：禁用，1：启用
     *
     * @return
     */
    public Integer getEnabled() {
        return this.enabled;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("name", this.name)
                .append("alias", this.alias)
                .append("enabled", this.enabled)
                .toString();
    }

    public String getGroupId() {
        return this.id;
    }

    public String getGroupCode() {

        return this.alias;
    }

    public Long getSn() {
        return Long.valueOf(1);
    }

    public String getGroupType() {
        return GroupTypeConstant.ROLE.key();
    }

    public String getParentId() {
        return "";
    }

    public String getPath() {
        return this.name;
    }

    public Map<String, Object> getParams() {

        return null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	@Override
	public String getGroupName() {
		return this.name;
	}
}