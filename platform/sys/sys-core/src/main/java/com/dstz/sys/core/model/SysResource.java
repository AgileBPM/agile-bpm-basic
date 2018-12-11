package com.dstz.sys.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.api.model.IDModel;
import com.dstz.base.api.model.Tree;


/**
 * <pre>
 * 描述：子系统资源 实体对象
 * </pre>
 */
public class SysResource implements Tree, IDModel {

    /**
     *
     */
    private static final long serialVersionUID = 1717028148397030560L;

    /**
     * 主键
     */
    protected String id;

    /**
     * 子系统ID
     */
    protected String systemId;

    /**
     * 资源别名
     */
    protected String alias;

    /**
     * 资源名
     */
    protected String name;

    /**
     * 默认地址
     */
    protected String defaultUrl;

    /**
     * 显示到菜单(1,显示,0 ,不显示)
     */
    protected Integer enableMenu;

    /**
     * 是否有子节点
     */
    protected Integer hasChildren;

    /**
     * OPENED_
     */
    protected Integer opened;

    /**
     * 图标
     */
    protected String icon = "";

    /**
     * 打开新窗口
     */
    protected Integer newWindow;

    /**
     * 排序
     */
    protected Long sn;

    /**
     * 父资源ID
     */
    protected String parentId;

    /**
     * 创建时间。
     */
    protected Date createTime;

    /**
     * 资源的URL列表
     */
    protected List<RelResource> relResources = new ArrayList<RelResource>();

    protected List<SysResource> children = new ArrayList<SysResource>();

    /**
     * 是否已分配给角色
     */
    protected boolean checked = false;


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

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * 返回 子系统ID
     *
     * @return
     */
    public String getSystemId() {
        return this.systemId;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 返回 资源别名
     *
     * @return
     */
    public String getAlias() {
        return this.alias;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回 资源名
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    /**
     * 返回 默认地址
     *
     * @return
     */
    public String getDefaultUrl() {
        return this.defaultUrl;
    }

    public void setEnableMenu(Integer enableMenu) {
        this.enableMenu = enableMenu;
    }

    /**
     * 返回 显示到菜单(1,显示,0 ,不显示)
     *
     * @return
     */
    public Integer getEnableMenu() {
        return this.enableMenu;
    }

    public void setHasChildren(Integer hasChildren) {
        this.hasChildren = hasChildren;
    }

    /**
     * 返回 是否有子节点
     *
     * @return
     */
    public Integer getHasChildren() {
        return this.hasChildren;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    /**
     * 返回 OPENED_
     *
     * @return
     */
    public Integer getOpened() {
        return this.opened;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 返回 图标
     *
     * @return
     */
    public String getIcon() {
        return this.icon;
    }

    public void setNewWindow(Integer newWindow) {
        this.newWindow = newWindow;
    }

    /**
     * 返回 打开新窗口
     *
     * @return
     */
    public Integer getNewWindow() {
        return this.newWindow;
    }

    public void setSn(Long sn) {
        this.sn = sn;
    }

    /**
     * 返回 排序
     *
     * @return
     */
    public Long getSn() {
        return this.sn;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<RelResource> getRelResources() {
        return relResources;
    }


    public void setRelResources(List<RelResource> relResources) {
        this.relResources = relResources;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SysResource other = (SysResource) obj;
        if (id.equals(other.id)) return true;
        return false;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("systemId", this.systemId)
                .append("alias", this.alias)
                .append("name", this.name)
                .append("defaultUrl", this.defaultUrl)
                .append("enableMenu", this.enableMenu)
                .append("hasChildren", this.hasChildren)
                .append("opened", this.opened)
                .append("parentId", this.parentId)
                .append("icon", this.icon)
                .append("newWindow", this.newWindow)
                .append("sn", this.sn)
                .toString();
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }
}