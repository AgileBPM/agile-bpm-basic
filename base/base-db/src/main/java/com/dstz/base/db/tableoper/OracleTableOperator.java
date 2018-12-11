package com.dstz.base.db.tableoper;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dstz.base.api.constant.ColumnType;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.api.table.DbType;
import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;

/**
 * <pre>
 * oracle的实现类
 * </pre>
 *
 * @author aschs
 */
public class OracleTableOperator extends TableOperator {

	/**
	 * @param table
	 * @param jdbcTemplate
	 */
	public OracleTableOperator(Table<? extends Column> table, JdbcTemplate jdbcTemplate) {
		super(table, jdbcTemplate);
	}

	@Override
	public String type() {
		return DbType.ORACLE.getKey();
	}

	@Override
	public void createTable() {
		if (isTableCreated()) {
			logger.debug("表[" + table.getName() + "(" + table.getComment() + ")]已存在数据库中，无需再次生成");
			return;
		}

		// 建表语句
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE \"" + table.getName() + "\" (" + "\n");
		for (Column column : table.getColumns()) {
			sql.append(columnToSql(column) + ",\n");
		}
		sql.append("PRIMARY KEY (\"" + table.getPkColumn().getName() + "\")" + "\n)");
		// 建表结束
		jdbcTemplate.execute(sql.toString());
		
		// 开始处理注释
		if(StringUtil.isNotEmpty(table.getComment())) {
			String str = "COMMENT ON TABLE \"" + table.getName() + "\" IS '" + table.getComment() + "'";
			jdbcTemplate.execute(str);// 表注解
		}
		
		// 字段注解
		for (int i = 0; i < table.getColumns().size(); i++) {
			Column column = table.getColumns().get(i);
			if(StringUtil.isEmpty(column.getComment())) {
				continue;
			}
			String str = "COMMENT ON COLUMN \"" + table.getName() + "\".\"" + column.getName() + "\"  IS '" + column.getComment()+"'";
			jdbcTemplate.execute(str);
		}
	}

	@Override
	public boolean isTableCreated() {
		String sql = "select count(1) from user_tables t where table_name =?";
		return jdbcTemplate.queryForObject(sql, Integer.class, table.getName()) > 0 ? true : false;
	}

	@Override
	public void addColumn(Column column) {
		StringBuilder sql = new StringBuilder();
		sql.append("ALTER TABLE \"" + table.getName() + "\"");
		sql.append(" ADD ( " + columnToSql(column) + " )");
		jdbcTemplate.execute(sql.toString());
		
		//注解
		if(StringUtil.isEmpty(column.getComment())) {
			return;
		}
		String str = "COMMENT ON COLUMN \"" + table.getName() + "\".\"" + column.getName() + "\"  IS '" + column.getComment()+"'";
		jdbcTemplate.execute(str);
	}

	@Override
	public void updateColumn(Column column) {
		StringBuilder sql = new StringBuilder();
		sql.append("ALTER TABLE \"" + table.getName() + "\"");
		sql.append(" MODIFY( " + columnToSql(column) + " )");
		jdbcTemplate.execute(sql.toString());
	}

	@Override
	public void dropColumn(String columnName) {
		StringBuilder sql = new StringBuilder();
		sql.append("ALTER TABLE \"" + table.getName() + "\"");
		sql.append(" DROP(\"" + columnName + "\")");
		jdbcTemplate.execute(sql.toString());
	}

	/**
	 * <pre>
	 * 把column解析成Sql
	 * </pre>
	 *
	 * @param column
	 * @return
	 */
	private String columnToSql(Column column) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"" + column.getName() + "\"");
		if (ColumnType.CLOB.equalsWithKey(column.getType())) {
			sb.append(" CLOB");
		} else if (ColumnType.DATE.equalsWithKey(column.getType())) {
			sb.append(" TIMESTAMP");
		} else if (ColumnType.NUMBER.equalsWithKey(column.getType())) {
			sb.append(" NUMBER(" + column.getLength() + "," + column.getDecimal() + ")");
		} else if (ColumnType.VARCHAR.equalsWithKey(column.getType())) {
			sb.append(" VARCHAR2(" + column.getLength() + ")");
		}

		if (column.isRequired() || column.isPrimary()) {
			sb.append(" NOT NULL");
		}
		return sb.toString();
	}

}
