package com.dstz.base.api.query;


/**
 * 字段排序方向。
 * <pre>
 * </pre>
 */
public enum Direction {
    ASC, DESC;


    public static Direction fromString(String value) {
        try {
            return Direction.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return ASC;
        }
    }
}

