package com.dstz.base.api.query;

/**
 * 查询字段接口类。
 */
public interface QueryField extends WhereClause {
    /**
     * 返回字段名
     *
     * @return
     */
    public String getField();

    /**
     * 比较符
     *
     * @return
     */
    public QueryOP getCompare();

    /**
     * 返回值
     *
     * @return
     */
    public Object getValue();

}
