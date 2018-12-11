package com.dstz.sys.core.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.dstz.base.core.model.BaseModel;
import com.dstz.sys.api.model.ISysTreeNode;

/**
 * <pre>
 * 描述：系统树节点
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月13日 下午6:44:47
 * 版权:summer
 * </pre>
 */
public class SysTreeNode extends BaseModel implements ISysTreeNode {
    /**
     * 别名
     */
    @NotEmpty
    private String key;
    /**
     * 名字
     */
    @NotEmpty
    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 所属树id
     */
    @NotEmpty
    private String treeId;
    /**
     * 父ID
     */
    private String parentId;
    /**
     * 路径 eg:pppid.ppid.pid
     */
    private String path;
    /**
     * 排序号
     */
    private int sn;

    // 以下字段与数据库无关
    /**
     * 当前节点的子节点
     */
    private List<SysTreeNode> children;

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    @Override
    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public List<SysTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<SysTreeNode> children) {
        this.children = children;
    }

}
