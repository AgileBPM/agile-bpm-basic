package com.dstz.org.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 组织常量。
 *
 * @author ray
 */
public enum GroupTypeConstant {
    ORG("org", "组织"),
    ROLE("role", "角色"),
    POSITION("post", "岗位");
    private String key;
    private String label;

    GroupTypeConstant(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String key() {
        return key;
    }

    public String label() {
        return label;
    }

    public static Map<String, String> getGroupTypes() {
        Map<String, String> map = new HashMap<String, String>();
        for (GroupTypeConstant e : GroupTypeConstant.values()) {
            map.put(e.key(), e.label());
        }
        return map;
    }

}
