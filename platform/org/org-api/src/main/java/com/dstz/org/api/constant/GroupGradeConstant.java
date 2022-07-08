package com.dstz.org.api.constant;

/**
 * 组织级别
 */
public enum GroupGradeConstant {
    GROUP(0, "0-集团"),
    COMPANY(1, "1-分公司"),
    DEPARTMENT(3, "3-部门"); 


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
 

}
