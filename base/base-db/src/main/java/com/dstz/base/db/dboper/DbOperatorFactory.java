package com.dstz.base.db.dboper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dstz.base.core.util.AppUtil;
import com.dstz.base.db.api.table.DbType;
import com.dstz.base.db.datasource.DbContextHolder;

/**
 * <pre>
 * 描述：DbOperator的工厂类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月22日 下午8:37:04
 * 版权:summer
 * </pre>
 */
public class DbOperatorFactory {
	protected static final Logger LOG = LoggerFactory.getLogger(DbOperatorFactory.class);

    private DbOperatorFactory() {

    }

    /**
     * <pre>
     * 构建一个操作器
     * </pre>
     *
     * @param type
     * @param jdbcTemplate
     * @return
     */
    public static DbOperator newOperator(String type, JdbcTemplate jdbcTemplate) {
        if (DbType.MYSQL.equalsWithKey(type)) {
            return new MysqlDbOperator(jdbcTemplate);
        }
        if (DbType.ORACLE.equalsWithKey(type)) {
            return new OracleDbOperator(jdbcTemplate);
        }
        LOG.warn("cannot get DbOperator ! DbType:{}",type);
        return null;
    }
    
    /**
     * <pre>
     * 获取本地数据库操作类
     * </pre>	
     * @return
     */
    public static DbOperator getLocal() {
    	return newOperator(DbContextHolder.getDbType(), AppUtil.getBean(JdbcTemplate.class));
    }
}
