package com.dstz.base.db.api.table.model;

import java.util.List;

/**
 * 表对象。
 *
 * <pre>
 * </pre>
 */
public interface Table {

    /**
     * 返回表名
     *
     * @return
     */
    public String getTableName();

    /**
     * 返回注释
     *
     * @return
     */
    public String getComment();

    /**
     * 返回字段列表
     *
     * @return
     */
    public List<Column> getColumnList();

    /**
     * 返回主键
     *
     * @return
     */
    public List<Column> getPrimayKey();

    /**
     * 设置表名
     *
     * @param name
     */
    public void setTableName(String name);

    /**
     * 设置表注释
     *
     * @param comment
     */
    public void setComment(String comment);

    /**
     * 设置字段列表
     *
     * @param columns
     */
    public void setColumnList(List<Column> columns);

    /**
     * 增加字段
     *
     * @param column
     */
    public void addColumn(Column column);

}
