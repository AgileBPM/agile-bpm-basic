package com.dstz.base.api.query;

/**
 * <pre>
 * 描述：查询字段之间的关系枚举。
 * </pre>
 */
public enum FieldRelation {
    AND("AND"), OR("OR"), NOT("NOT");
    private String val;

    FieldRelation(String _val) {
        val = _val;
    }

    public String value() {
        return val;
    }
}
