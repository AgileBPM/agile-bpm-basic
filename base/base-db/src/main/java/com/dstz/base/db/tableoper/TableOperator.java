package com.dstz.base.db.tableoper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.db.dboper.DbOperator;
import com.dstz.base.db.dboper.DbOperatorFactory;
import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;

/**
 * <pre>
 * 针对一张表的操作者，系统表的操作有以下几种
 * 1 针对表本身的：建表，删表
 * 2 针对字段：增字段，改字段，删字段
 * 3 针对数据：增，删，查，改
 * </pre>
 *
 * @author aschs
 */
public abstract class TableOperator {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 表对象
	 */
	protected Table<? extends Column> table;
	/**
	 * jdbc
	 */
	protected JdbcTemplate jdbcTemplate;

	/**
	 * @param table
	 * @param jdbcTemplate
	 */
	public TableOperator(Table<? extends Column> table, JdbcTemplate jdbcTemplate) {
		super();
		this.table = table;
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * <pre>
	 * 返回的数据库类型
	 * 枚举：DbType
	 * </pre>
	 *
	 * @return
	 */
	public abstract String type();

	/**
	 * <pre>
	 * 创建表
	 * </pre>
	 */
	public void createTable() {
	}

	/**
	 * <pre>
	 * 删除表
	 * </pre>
	 */
	public void dropTable() {
		if (!isTableCreated()) {
			return;
		}
		String sql = "drop table " + table.getName() + "";
		jdbcTemplate.execute(sql);
	}

	/**
	 * <pre>
	 * 表是否被生成过
	 * 或者说，表是否已存在数据库
	 * </pre>
	 *
	 * @return
	 */
	public boolean isTableCreated() {
		return false;
	}

	/**
	 * <pre>
	 * 增加字段
	 * </pre>
	 *
	 * @param column
	 *            字段
	 */
	public void addColumn(Column column) {

	}

	/**
	 * <pre>
	 * 更新字段
	 * </pre>
	 *
	 * @param column
	 *            字段
	 */
	public void updateColumn(Column column) {

	}

	/**
	 * <pre>
	 * 删除字段
	 * </pre>
	 *
	 * @param column
	 */
	public void dropColumn(String columnName) {

	}

	/**
	 * <pre>
	 * 插入数据
	 * </pre>
	 *
	 * @param data
	 *            数据
	 */
	public void insertData(Map<String, Object> data) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO " + table.getName());

		StringBuilder columnNameSql = new StringBuilder();// 字段名字sql
		StringBuilder paramNameSql = new StringBuilder();// 参数sql
		List<Object> param = new ArrayList<>();// 参数
		for (Entry<String, Object> entry : data.entrySet()) {
			if (columnNameSql.length() > 0) {
				columnNameSql.append(",");
				paramNameSql.append(",");
			}
			columnNameSql.append(entry.getKey());
			paramNameSql.append("?");
			param.add(entry.getValue());
		}
		sql.append("(" + columnNameSql + ") VALUES(" + paramNameSql + ")");
		jdbcTemplate.update(sql.toString(), param.toArray());
	}

	/**
	 * <pre>
	 * 根据主键删除数据
	 * </pre>
	 *
	 * @param id
	 *            主键值
	 */
	public void deleteData(Object id) {
		String sql = "DELETE FROM " + table.getName() + " where " + table.getPkColumn().getName() + " = ?";
		jdbcTemplate.update(sql, id);
	}

	/**
	 * <pre>
	 * 根据参数删除数据
	 * </pre>
	 *
	 * @param param
	 *            参数
	 */
	public void deleteData(Map<String, Object> param) {
		if (param.isEmpty()) {
			throw new RuntimeException("操作删除表[" + table.getComment() + "(" + table.getName() + ")]时，条件参数为空(会导致全表数据清空)");
		}

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM " + table.getName() + " where ");
		List<Object> paramList = new ArrayList<>();// 参数
		for (Entry<String, Object> entry : param.entrySet()) {
			if (sql.toString().endsWith("?")) {
				sql.append(" and ");
			}
			sql.append(entry.getKey() + " = ?");
			paramList.add(entry.getValue());
		}
		jdbcTemplate.update(sql.toString(), paramList.toArray());
	}

	/**
	 * <pre>
	 * 更新数据
	 * 获取数据中的主键来进行更新
	 * </pre>
	 *
	 * @param data
	 *            数据
	 */
	public void updateData(Map<String, Object> data) {
		Object id = data.get(table.getPkColumn().getName());
		if (BeanUtils.isEmpty(id)) {
			throw new RuntimeException("操作更新表[" + table.getComment() + "(" + table.getName() + ")]时，参数中有没主键[" + table.getPkColumn().getComment() + "(" + table.getPkColumn().getName() + ")]");
		}

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE " + table.getName() + " SET ");
		List<Object> param = new ArrayList<>();// 参数
		for (Entry<String, Object> entry : data.entrySet()) {
			// 主键跳过
			if (entry.getKey().equals(table.getPkColumn().getName())) {
				continue;
			}
			if (sql.toString().endsWith("?")) {
				sql.append(" , ");
			}
			param.add(entry.getValue());
			sql.append(entry.getKey() + " = ?");
		}
		// 主键设置为过滤条件
		sql.append(" WHERE " + table.getPkColumn().getName() + " = ?");
		param.add(id);
		jdbcTemplate.update(sql.toString(), param.toArray());
	}

	/**
	 * <pre>
	 * 根据主键获取唯一数据
	 * </pre>
	 *
	 * @param columnName
	 *            要查的字段
	 * @param id
	 *            主键值
	 * @return
	 */
	public Map<String, Object> selectData(List<String> columnName, Object id) {
		Map<String, Object> param = new HashMap<>();
		param.put(table.getPkColumn().getName(), id);
		List<Map<String, Object>> list = selectData(columnName, param);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * <pre>
	 * 根据主键获取唯一数据
	 * </pre>
	 *
	 * @param id
	 *            主键值
	 * @return
	 */
	public Map<String, Object> selectData(Object id) {
		Map<String, Object> param = new HashMap<>();
		param.put(table.getPkColumn().getName(), id);
		List<Map<String, Object>> list = selectData(param);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * <pre>
	 * </pre>
	 *
	 * @param param
	 *            参数
	 * @return
	 */
	public List<Map<String, Object>> selectData(Map<String, Object> param) {
		return selectData(null, param);
	}

	/**
	 * <pre>
	 * </pre>
	 *
	 * @param columnName
	 *            要查的字段
	 * @param param
	 *            参数列表
	 * @return
	 */
	public List<Map<String, Object>> selectData(List<String> columnName, Map<String, Object> param) {
		StringBuilder sql = new StringBuilder();
		if (BeanUtils.isEmpty(columnName)) {
			sql.append("SELECT * FROM " + table.getName());
		} else {
			sql.append("SELECT");
			for (String cn : columnName) {
				if (!sql.toString().endsWith("SELECT")) {
					sql.append(",");
				}
				sql.append(" " + cn);
			}
			sql.append(" FROM " + table.getName());
		}

		sql.append(" WHERE ");

		List<Object> paramList = new ArrayList<>();// 参数
		for (Entry<String, Object> entry : param.entrySet()) {
			if (sql.toString().endsWith("?")) {
				sql.append(" and ");
			}
			sql.append(entry.getKey() + " = ?");
			paramList.add(entry.getValue());
		}

		return jdbcTemplate.queryForList(sql.toString(), paramList.toArray());
	}

	/**
	 * <pre>
	 * 同步table和数据库中的字段信息
	 * 目前只处理字段的增删
	 * </pre>
	 */
	public void syncColumn() {
		// 未生成表，不处理
		if (!isTableCreated()) {
			return;
		}
		Set<String> dbColumnNames = new HashSet<>();//数据库中存在的字段名
		for (Column c : getDbTable().getColumns()) {
			dbColumnNames.add(c.getName());
		}

		for (String columnName : dbColumnNames) {
			if (this.table.getColumn(columnName) == null) {// 数据库表内有，但是结构没有，删除
				dropColumn(columnName);
			}
		}

		for (Column column : table.getColumns()) {
			if (!dbColumnNames.contains(column.getName())) {// 结构有，数据库表内没有，增加
				addColumn(column);
			}
		}

	}

	public Table<Column> getDbTable() {
		DbOperator dbOperator = DbOperatorFactory.newOperator(type(), jdbcTemplate);
		return dbOperator.getTable(table.getName());
	}
	
}
