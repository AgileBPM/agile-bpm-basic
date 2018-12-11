package com.dstz.sys.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.api.model.IDModel;
import com.dstz.sys.api.model.system.ISubsystem;


/**
 * <pre>
 * 描述：子系统定义 实体对象
 * </pre>
 */
public class Subsystem implements IDModel,ISubsystem {

    /**
     * 主键
     */
    protected String id;

    /**
     * 系统名称
     */
    protected String name;

    /**
     * 系统别名
     */
    protected String alias;

    /**
     * logo地址
     */
    protected String logo;

    /**
     * 是否可用 1 可用，0 ，不可用
     */
    protected Integer enabled = 1;

    /**
     * 主页地址
     */
    protected String homeUrl;

    /**
     * 基础地址
     */
    protected String baseUrl;

    /**
     * 租户名称
     */
    protected String tenant;

    /**
     * 备注
     */
    protected String memo;

    /**
     * 创建人ID
     */
    protected String creatorId;

    /**
     * 创建人
     */
    protected String creator;

    /**
     * 创建时间
     */
    protected java.util.Date createTime;

    /**
     * 默认子系统。
     */
    protected int isDefault = 0;


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

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回 系统名称
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
     * 返回 系统别名
     *
     * @return
     */
    public String getAlias() {
        return this.alias;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 返回 logo地址
     *
     * @return
     */
    public String getLogo() {
        return this.logo;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    /**
     * 返回 是否可用 1 可用，0 ，不可用
     *
     * @return
     */
    public Integer getEnabled() {
        return this.enabled;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    /**
     * 返回 主页地址
     *
     * @return
     */
    public String getHomeUrl() {
        return this.homeUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 返回 基础地址
     *
     * @return
     */
    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    /**
     * 返回 租户名称
     *
     * @return
     */
    public String getTenant() {
        return this.tenant;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 返回 备注
     *
     * @return
     */
    public String getMemo() {
        return this.memo;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 返回 创建人ID
     *
     * @return
     */
    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 返回 创建人
     *
     * @return
     */
    public String getCreator() {
        return this.creator;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 返回 创建时间
     *
     * @return
     */
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("name", this.name)
                .append("alias", this.alias)
                .append("logo", this.logo)
                .append("enabled", this.enabled)
                .append("homeUrl", this.homeUrl)
                .append("baseUrl", this.baseUrl)
                .append("tenant", this.tenant)
                .append("memo", this.memo)
                .append("creatorId", this.creatorId)
                .append("creator", this.creator)
                .append("createTime", this.createTime)
                .toString();
    }
}