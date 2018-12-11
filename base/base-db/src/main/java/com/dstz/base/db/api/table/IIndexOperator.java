package com.dstz.base.db.api.table;

import com.dstz.base.db.api.table.model.Index;

import java.sql.SQLException;
import java.util.List;

/**
 * 索引操作接口
 *
 * <pre>
 * </pre>
 */
public interface IIndexOperator extends IDbType {

    void createIndex(Index index) throws SQLException;

    /**
     * 根据表名和索引名，删除表名和索引名对应的索引.所有实现使用精确匹配方式。
     *
     * @param tableName 表名
     * @param indexName 索引名
     */
    void dropIndex(String tableName, String indexName);

    /**
     * 根据表名和索引名，取得表名和索引名对应的索引.所有实现使用精确匹配方式。
     *
     * @param tableName 表名
     * @param indexName 索引名
     * @return
     * @throws SQLException
     */
    Index getIndex(String tableName, String indexName) throws SQLException;


    List<Index> getIndexByFuzzyMatch(String indexName) throws SQLException;

    /**
     * 根据表名，取得表名对应的索引列表.所有实现使用精确匹配方式。
     *
     * @param tableName 表名
     * @return
     * @throws SQLException
     */
    List<Index> getIndexsByTable(String tableName) throws SQLException;

    /**
     * 重建索引
     *
     * @param tableName 表名
     * @param indexName 索引名
     */
    void rebuildIndex(String tableName, String indexName);

}
