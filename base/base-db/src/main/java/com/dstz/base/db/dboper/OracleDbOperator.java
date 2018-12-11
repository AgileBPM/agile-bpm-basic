package com.dstz.base.db.dboper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dstz.base.api.constant.ColumnType;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.db.api.table.DbType;
import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;

/**
 * <pre>
 * 描述：oracle 的DbOperator实现类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月22日 下午8:17:49
 * 版权:summer
 * </pre>
 */
public class OracleDbOperator extends DbOperator {

	/**
	 * @param jdbcTemplate
	 */
	public OracleDbOperator(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	public String type() {
		return DbType.ORACLE.getKey();
	}

	@Override
	public Map<String, String> getTableNames(String tableName) {
		String sql = "select t.table_name,f.comments from user_tables t inner join user_tab_comments f on t.table_name = f.table_name";
		List<Map<String, Object>> list;
		if (StringUtils.isNotEmpty(tableName)) {
			sql += " AND t.table_name LIKE ?";
			list = jdbcTemplate.queryForList(sql, "%" + tableName + "%");
		} else {
			list = jdbcTemplate.queryForList(sql);
		}

		Map<String, String> map = new LinkedHashMap<>();
		for (Map<String, Object> m : list) {
			map.put(m.get("table_name").toString(), getOrDefault(m, "comments", "").toString());
		}

		return map;
	}

	@Override
	public List<String> getViewNames(String viewName) {
		String sql = "SELECT * FROM USER_VIEWS ";
		if (StringUtils.isNotEmpty(viewName))
			sql += " WHERE VIEW_NAME LIKE ?";
		List<String> list = new ArrayList<>();
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, "%" + viewName + "%");
		for (Map<String, Object> line : results) {
			list.add(line.get("VIEW_NAME").toString());
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
		// 先找到主键
		String sqlT = "select col.column_name from user_constraints con,user_cons_columns col where con.constraint_name=col.constraint_name and con.constraint_type='P' and col.table_name= ?";
		List<Map<String, Object>> listT = jdbcTemplate.queryForList(sqlT, name);
		Set<String> pkNames = new HashSet<>();// 主键
		for (Map<String, Object> map : listT) {
			pkNames.add(getOrDefault(map, "COLUMN_NAME", "").toString());
		}

		// 开始解析字段信息
		String sql = "select a.*,b.comments from user_tab_columns a inner join user_col_comments b on a.table_name = b.table_name and a.column_name = b.column_name and a.table_name = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, name);
		List<Column> columns = new ArrayList<>();
		for (Map<String, Object> map : list) {
			Column column = new Column();
			column.setComment(getOrDefault(map, "COMMENTS", "").toString());
			Object defVal = map.get("DATA_DEFAULT");
			if(defVal!=null&&!defVal.toString().trim().equals("NULL")) {
				column.setDefaultValue(map.get("DATA_DEFAULT").toString());
			}
			column.setName(getOrDefault(map, "COLUMN_NAME", "").toString());
			column.setPrimary(pkNames.contains(column.getName()));
			column.setRequired("N".equals(getOrDefault(map, "NULLABLE", "Y")));
			column.setType(ColumnType.getByDbDataType(map.get("DATA_TYPE").toString()).getKey());
			if (ColumnType.VARCHAR.equalsWithKey(column.getType())) {
				column.setLength(Integer.parseInt(getOrDefault(map, "DATA_LENGTH", "0").toString()));
			}
			if (ColumnType.NUMBER.equalsWithKey(column.getType())) {
				column.setLength(Integer.parseInt(getOrDefault(map, "DATA_PRECISION", "0").toString()));
				column.setDecimal(Integer.parseInt(getOrDefault(map, "DATA_SCALE", "0").toString()));
			}
			columns.add(column);
		}
		return columns;
	}

	@Override
	public boolean supportPartition(String tableName) {
		String sql = "select count(*) from user_tab_partitions where table_name = ?";
		Integer rtn = jdbcTemplate.queryForObject(sql, Integer.class, tableName);
		return rtn > 0;
	}

	@Override
	public boolean isExsitPartition(String tableName, String partName) {
		String sql = "select count(*) from user_tab_partitions where table_name = ? and partition_name = ?";
		String[] args = new String[2];
		args[0] = tableName;
		args[1] = "P_" + partName.toUpperCase();
		Integer rtn = jdbcTemplate.queryForObject(sql, args, Integer.class);
		return rtn > 0;
	}

	@Override
	public void createPartition(String tableName, String partName) {
		String sql = "ALTER TABLE " + tableName + " ADD PARTITION P_" + partName.toUpperCase() + " VALUES ( '" + partName + "') NOCOMPRESS ";
		jdbcTemplate.update(sql);
	}
}
