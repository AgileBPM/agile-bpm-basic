package com.dstz.org.api.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 组织级别
 */
public enum GroupGradeConstant {
    GROUP(0, "GROUP"),
    COMPANY(1, "公司"),
    DEPARTMENT(3, "部门");


    private int key;
    private String label;

    GroupGradeConstant(int key, String label) {
        this.key = key;
        this.label = label;
    }

    public int key() {
        return key;
    }

    public String label() {
        return label;
    }

    public static Map<Integer, String> getGroupTypes() {
        Map<Integer, String> map = new HashMap<>();
        for (GroupGradeConstant e : GroupGradeConstant.values()) {
            map.put(e.key(), e.label());
        }
        return map;
    }

}
