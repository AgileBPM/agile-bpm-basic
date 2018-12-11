package com.dstz.base.db.api.table;

import com.dstz.base.api.query.FieldSort;

import java.util.List;

/**
 * 方言接口
 *
 * <pre>
 * </pre>
 */
public interface IDialect {
    /**
     * 支持limit
     *
     * @return
     */
    public boolean supportsLimit();

    /**
     * 支持limit偏移
     *
     * @return
     */
    public boolean supportsLimitOffset();

    /**
     * 将sql变成分页sql语句,直接使用offset,limit的值作为占位符.</br>
     *
     * @param sql    sql语句
     * @param offset 记录编号
     * @param limit  页大小
     * @return String
     */
    public String getLimitString(String sql, int offset, int limit);

    /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符(placeholder)替换.
     *
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     *
     * @return 包含占位符的分页sql
     */
    public String getLimitString(String sql, int offset,
                                 String offsetPlaceholder, int limit, String limitPlaceholder);

    /**
     * 将sql转换为总记录数SQL
     *
     * @param sql SQL语句
     * @return 总记录数的sql
     */
    public String getCountString(String sql);

    /**
     * 将sql转换为带排序的SQL
     *
     * @param sql    SQL语句
     * @param orders 排序对象
     * @return String
     */
    public String getSortString(String sql, List<FieldSort> orders);

}
