package com.dstz.base.db.dboper;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;

/**
 * <pre>
 * 描述：针对整个数据库的操作者，系统操作有以下几种
 * 1 获取库的全部表
 * 2 获取库的全部视图
 * 3 获取某个表的详细信息（包含字段）
 * 4 获取某个视图的详细信息（包含字段）
 * 5 分区相关
 * ps:针对某个表的操作在TableOperator中实现了
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月22日 下午8:01:35
 * 版权:summer
 * </pre>
 */
public abstract class DbOperator {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * jdbc
	 */
	protected JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate
	 */
	public DbOperator(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		
		if(jdbcTemplate == null) {
			logger.warn("create DbOperator with error ! jdbcTemplate cannot be null!");
		}
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
	 * 获取数据库的表名
	 * </pre>
	 *
	 * @param tableName
	 *            like查询的表名
	 * @return Map<表名 , 表描述>
	 */
	public abstract Map<String, String> getTableNames(String tableName);

	/**
	 * <pre>
	 * 获取数据库的视图名
	 * </pre>
	 *
	 * @param viewName
	 *            like查询的视图名
	 * @return List<视图名>
	 */
	public abstract List<String> getViewNames(String viewName);

	/**
	 * <pre>
	 * 根据表名获取表的详细信息
	 * </pre>
	 *
	 * @param tableName
	 * @return
	 */
	public abstract Table<Column> getTable(String tableName);

	/**
	 * <pre>
	 * 根据视图名获取视图的详细信息
	 * ps:这里的视图信息也用table来封装，其实也是一个意思
	 * </pre>
	 *
	 * @param viewName
	 * @return
	 */
	public abstract Table<Column> getView(String viewName);

	/**
	 * <pre>
	 * 判断表名tableName是否支持分区
	 * </pre>
	 * 
	 * @param tableName
	 * @return
	 */
	public abstract boolean supportPartition(String tableName);

	/**
	 * <pre>
	 * 判断表中是否已存在某个分区
	 * </pre>
	 * 
	 * @param tableName
	 * @param partName
	 * @return
	 */
	public abstract boolean isExsitPartition(String tableName, String partName);

	/**
	 * <pre>
	 * 为表创建分区
	 * </pre>
	 * 
	 * @param tableName
	 * @param partName
	 */
	public abstract void createPartition(String tableName, String partName);

	/**
	 * <pre>
	 * 这方法完全是Map的getOrDefault一样的
	 * 但是spring的关于jdbc的一个map实现类LinkedCaseInsensitiveMap重载了一个错误实现，导致会有可能出现null异常
	 * </pre>
	 * 
	 * @param map
	 * @param key
	 * @param defVal
	 * @return
	 */
	public Object getOrDefault(Map<?, ?> map, Object key, Object defVal) {
		if (!map.containsKey(key)) {
			return defVal;
		}
		Object val = map.get(key);
		if (val == null) {
			return defVal;
		}
		return val;
	}
}
