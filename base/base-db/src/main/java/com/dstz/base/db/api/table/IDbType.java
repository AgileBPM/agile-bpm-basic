package com.dstz.base.db.api.table;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据库类型接口。
 * <pre>
 * 1.设置JdbcTemplate。
 * 2.设置方言。
 *
 * </pre>
 */
public interface IDbType {

    /**
     * 设置spring 的JDBCTemplate
     *
     * @param template
     */
    void setJdbcTemplate(JdbcTemplate template);

}
