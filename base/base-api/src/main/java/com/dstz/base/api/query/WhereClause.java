package com.dstz.base.api.query;

/**
 * 构建SQL语句中的Where条件组件的SQL片段
 *
 * @author csx
 */
public interface WhereClause {
    /**
     * 返回SQL片段
     */
    public String getSql();
}
