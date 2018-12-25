package com.dstz.sys.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.api.model.IDModel;
import com.dstz.base.api.model.Tree;


/**
 * 系统资源
 * @author jeff
 *
 */
public class SysResource implements Tree, IDModel {
    /**
     * 主键
     */
    protected String id;

    /**
     * 父资源ID
     */
    protected String parentId;
    
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
    protected String url;

    /**
     * 显示到菜单(1,显示,0 ,不显示)
     */
    protected Integer enable;
 
    /**
     * OPENED_
     */
    protected Integer opened;

    /**
     * 图标
     */
    protected String icon = "";

    /**
     * 排序
     */
    protected Integer sn;

    /**
     * 创建时间。
     */
    protected Date createTime;
 
    
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

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    /**
     * 返回 排序
     *
     * @return
     */
    public Integer getSn() {
        return this.sn;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }
}