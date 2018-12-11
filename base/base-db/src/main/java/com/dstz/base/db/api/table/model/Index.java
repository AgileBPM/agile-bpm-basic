package com.dstz.base.db.api.table.model;

import java.util.List;

/**
 * 索引 接口
 *
 * <pre>
 * </pre>
 */
public interface Index {
    // 索引类型
    String INDEX_TYPE_BITMAP = "BITMAP";
    String INDEX_TYPE_BTREE = "BTREE";
    String INDEX_TYPE_FUNCTION = "FUNCTION";
    String INDEX_TYPE_HEAP = "HEAP";
    String INDEX_TYPE_CLUSTERED = "CLUSTERED";
    String INDEX_TYPE_NONCLUSTERED = "NONCLUSTERED";
    String INDEX_TYPE_XML = "XML";
    String INDEX_TYPE_SPATIAL = "SPATIAL";
    String INDEX_TYPE_REG = "REGULAR";
    String INDEX_TYPE_DIM = "DIMENSIONBLOCK";
    String INDEX_TYPE_BLOK = "BLOCK";
    // 表类型
    String TABLE_TYPE_TABLE = "TABLE";
    String TABLE_TYPE_VIEW = "VIEW";
    // 索引状态
    String INDEX_STATUS_VALIDATE = "VALIDATE";
    String INDEX_STATUS_INVALIDATE = "INVALIDATE";

    /**
     * 获取表名
     *
     * @return the tableName
     */
    public String getTableName();

    /**
     * 获取索引名
     *
     * @return the indexName
     */
    public String getIndexName();

    /**
     * 是否是唯一索引
     *
     * @return
     */
    public boolean isUnique();

    /**
     * 索引类型
     *
     * @return
     */
    public String getIndexType();

    /**
     * 索引描述
     *
     * @return
     */
    public String getIndexComment();

    /**
     * @return the columnList
     */
    public List<String> getColumnList();

    public void setTableName(String tableName);

    public void setTableType(String string);

    public void setIndexName(String indexName);

    public void setIndexType(String indexType);

    public void setUnique(boolean unique);

    public void setIndexStatus(String indexStatus);

    public void setIndexDdl(String indexDdl);

    public void setColumnList(List<String> columnList);

    public void setPkIndex(boolean b);

    public void setIndexComment(String indexComment);

}
