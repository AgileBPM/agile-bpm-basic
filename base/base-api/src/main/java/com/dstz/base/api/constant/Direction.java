package com.dstz.base.api.constant;

/**
 * 字段排序方式
 *
 * @author Administrator
 */
public enum Direction {
    ASC("asc", "升序"), DESC("desc", "降序");
    /**
     * key
     */
    private String key;
    /**
     * 描述
     */
    private String desc;

    private Direction(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * <pre>
     * 根据key来判断是否跟当前一致
     * </pre>
     *
     * @param key
     * @return
     */
    public boolean equalsWithKey(String key) {
        return this.key.equals(key);
    }

    public static Direction fromString(String value) {
        try {
            return Direction.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return ASC;
        }
    }
}