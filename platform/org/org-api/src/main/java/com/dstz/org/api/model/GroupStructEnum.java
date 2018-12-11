package com.dstz.org.api.model;

/**
 * 组结构枚举。
 *
 * @author ray
 */
public enum GroupStructEnum {
    PLAIN("plain", "平铺"),
    TREE("tree", "树形");

    private String key;
    private String label;

    GroupStructEnum(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String key() {
        return key;
    }

    public String label() {
        return label;
    }
}
