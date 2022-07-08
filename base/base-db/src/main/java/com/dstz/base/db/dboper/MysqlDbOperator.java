package com.dstz.base.db.dboper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dstz.base.api.constant.ColumnType;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.db.api.table.DbType;
import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;

/**
 * <pre>
 * 描述：mysql 的DbOperator实现类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月22日 下午8:17:49
 * 版权:summer
 * </pre>
 */
public class MysqlDbOperator extends DbOperator {

	/**
	 * @param jdbcTemplate
	 */
	public MysqlDbOperator(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	public String type() {
		return DbType.MYSQL.getKey();
	}

	@Override
	public Map<String, String> getTableNames(String tableName) {
		String sql = "select table_name,table_comment from information_schema.tables t where t.table_type='BASE TABLE' AND t.table_schema=DATABASE()";
		List<Map<String, Object>> list;
		if (StringUtils.isNotEmpty(tableName)) {
			sql += " AND TABLE_NAME LIKE ?";
			list = jdbcTemplate.queryForList(sql, "%" + tableName + "%");
		} else {
			list = jdbcTemplate.queryForList(sql);
		}

		Map<String, String> map = new LinkedHashMap<>();
		for (Map<String, Object> m : list) {
			map.put(m.get("table_name").toString(), m.get("table_comment").toString());
		}

		return map;
	}

	@Override
	public List<String> getViewNames(String viewName) {
		String sql = "show table status where comment='view'";
		if (StringUtils.isNotEmpty(viewName))
			sql += " AND NAME LIKE ?";
		List<String> list = new ArrayList<>();
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, "%" + viewName + "%");
		for (Map<String, Object> line : results) {
			list.add(line.get("Name").toString());
		}
		return list;
	}

	@Override
	public Table<Column> getTable(String tableName) {
		Table<Column> table = new Table<>();
		Map<String, String> tableNames = getTableNames(tableName);
		if (tableNames.isEmpty()) {
			throw new BusinessException(String.format("根据表名[%s]获取不到表", tableName));
		}
		table.setName(tableName);
		table.setComment(tableNames.get(tableName));
		table.setColumns(getColumns(tableName));

		return table;
	}

	@Override
	public Table<Column> getView(String viewName) {
		Table<Column> table = new Table<>();
		List<String> viewNames = getViewNames(viewName);
		if (viewNames.isEmpty()) {
			throw new BusinessException(String.format("根据视图名[%s]获取不到视图", viewName));
		}
		table.setName(viewName);
		table.setComment(viewName);
		table.setColumns(getColumns(viewName));
		return table;
	}

	/**
	 * <pre>
	 * 根据name获取其字段信息
	 * </pre>
	 *
	 * @param name
	 *            （表名/视图名）
	 * @return
	 */
	private List<Column> getColumns(String name) {
		String sql = "SELECT * FROM  INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, name);
		List<Column> columns = new ArrayList<>();
		for (Map<String, Object> map : list) {
			Column column = new Column();
			column.setComment(getOrDefault(map, "COLUMN_COMMENT", "").toString());
			column.setDefaultValue(map.get("COLUMN_DEFAULT") == null ? null : map.get("COLUMN_DEFAULT").toString());
			column.setName(getOrDefault(map, "COLUMN_NAME", "").toString());
			column.setPrimary("PRI".equals(getOrDefault(map, "COLUMN_KEY", "")));
			column.setRequired("NO".equals(getOrDefault(map, "IS_NULLABLE", "")));
			column.setType(ColumnType.getByDbDataType(map.get("DATA_TYPE").toString(),"字段["+column.getComment()+"("+column.getName()+")]").getKey());
			if (ColumnType.VARCHAR.equalsWithKey(column.getType())) {
				column.setLength(Integer.parseInt(getOrDefault(map, "CHARACTER_MAXIMUM_LENGTH", "0").toString()));
			}
			if (ColumnType.NUMBER.equalsWithKey(column.getType())) {
				column.setLength(Integer.parseInt(getOrDefault(map, "NUMERIC_PRECISION", "0").toString()));
				column.setDecimal(Integer.parseInt(getOrDefault(map, "NUMERIC_SCALE", "0").toString()));
			}
			columns.add(column);
		}
		return columns;
	}

	@Override
	public boolean supportPartition(String tableName) {
		String sql = "select count(partition_name) from information_schema.partitions where table_name=?;";
		Integer rtn = jdbcTemplate.queryForObject(sql, Integer.class, tableName);
		return rtn > 0;
	}

	@Override
	public boolean isExsitPartition(String tableName, String partName) {
		String sql = "select count(*) from information_schema.partitions where table_name=? and partition_name =?;";
		String[] args = new String[2];
		args[0] = tableName;
		args[1] = "P_" + partName.toUpperCase();
		Integer rtn = jdbcTemplate.queryForObject(sql, args, Integer.class);
		return rtn > 0;
	}

	@Override
	public void createPartition(String tableName, String partName) {
		String sql = "alter table " + tableName + " add partition (partition P_" + partName.toUpperCase() + " values in ('" + partName + "')) ";
		jdbcTemplate.execute(sql);
	}
}
