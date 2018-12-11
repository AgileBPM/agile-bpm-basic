package com.dstz.org.api.model;

/**
 * 组织类型。
 *
 * @author ray
 */
public class GroupType {

    /**
     * 别名
     */
    private String alias = "";

    /**
     * 名称
     */
    private String name = "";

    public GroupType() {
    }

    public GroupType(String alias, String name) {
        this.alias = alias;
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
