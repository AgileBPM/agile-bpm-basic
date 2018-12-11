package com.dstz.base.api.query;

public enum QueryOP {
    /**
     * 等于
     */
    EQUAL("EQ", "=", "等于", new String[]{"varchar", "number", "date"}),
    /**
     * 等于忽略大小写
     */
    EQUAL_IGNORE_CASE("EIC", "=", "等于忽略大小写", new String[]{"varchar"}),
    /**
     * 小于
     */
    LESS("LT", "<", "小于", new String[]{"number", "date"}),
    /**
     * 大于
     */
    GREAT("GT", ">", "大于", new String[]{"number", "date"}),
    /**
     * 小于等于
     */
    LESS_EQUAL("LE", "<=", "小于等于", new String[]{"number", "date"}),
    /**
     * 大于等于
     */
    GREAT_EQUAL("GE", ">=", "大于等于", new String[]{"number", "date"}),
    /**
     * 不等于
     */
    NOT_EQUAL("NE", "!=", "不等于", new String[]{"varchar", "number", "date"}),
    /**
     * 相似
     */
    LIKE("LK", "like", "相似", new String[]{"varchar"}),
    /**
     * 左相似
     */
    LEFT_LIKE("LFK", "like", "左相似", new String[]{"varchar"}),
    /**
     *
     */
    RIGHT_LIKE("RHK", "like", "右相似", new String[]{"varchar"}),
    /**
     *
     */
    IS_NULL("INL", "is null", "为空", new String[]{"varchar", "number", "date"}),
    /**
     *
     */
    NOTNULL("NNL", "is not null", "非空", new String[]{"varchar", "number", "date"}),
    /**
     * 在...中
     */
    IN("IN", "in", "在...中", new String[]{"varchar", "number", "date"}),
    /**
     * 不在...中
     */
    NOT_IN("NI", "not in", "不在...中", new String[]{"varchar", "number", "date"}),
    /**
     * 在...之间
     */
    BETWEEN("BT", "between", "在...之间", new String[]{"number", "date"});
    private String val;
    private String op;
    private String desc;
    private String[] supports;

    private QueryOP(String val, String op, String desc, String[] supports) {
        this.val = val;
        this.op = op;
        this.desc = desc;
        this.supports = supports;
    }

    public String value() {
        return val;
    }

    public String op() {
        return op;
    }

    public String desc() {
        return desc;
    }

    public String[] supports() {
        return supports;
    }

    /**
     * 根据运算符获取QueryOp
     *
     * @param op
     * @return QueryOP
     * @throws
     * @since 1.0.0
     */
    public static QueryOP getByOP(String op) {
        for (QueryOP queryOP : values()) {
            if (queryOP.op().equals(op)) {
                return queryOP;
            }
        }
        return null;
    }

    public static QueryOP getByVal(String val) {
        for (QueryOP queryOP : values()) {
            if (queryOP.val.equals(val)) {
                return queryOP;
            }
        }
        return null;
    }

    /**
     * <pre>
     * 根据val来判断是否跟当前一致
     * </pre>
     *
     * @param val
     * @return
     */
    public boolean equalsWithVal(String val) {
        return this.val.equals(val);
    }

}
