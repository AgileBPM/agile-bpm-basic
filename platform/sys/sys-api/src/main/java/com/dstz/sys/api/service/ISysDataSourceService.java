package com.dstz.sys.api.service;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dstz.sys.api.model.ISysDataSource;

/**
 * <pre>
 * 描述：系统数据源对其他模块的服务类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月29日 上午10:42:46
 * 版权:summer
 * </pre>
 */
public interface ISysDataSourceService {
    /**
     * <pre>
     * 根据key获取对象
     * </pre>
     *
     * @param key
     * @return
     */
    ISysDataSource getByKey(String key);

    /**
     * <pre>
     * 根据数据库别名获取 javax.sql.DataSource
     * 1 先从spring的容器数据源（DynamicDataSource）中拿
     * 2 再从系统配置的数据源(sysDataSource)中拿，当数据源是从系统中取出来的时，追加到spring容器数据源中
     * ps：所以，如果你在spring配置文件中配置的数据源别名跟系统配置的别名一样，会优先取spring配置文件中的
     * </pre>
     *
     * @param key
     * @return
     */
    DataSource getDataSourceByKey(String key);

    /**
     * <pre>
     * 根据数据库别名获取 javax.sql.JdbcTemplate
     * 1 先从spring的容器数据源（DynamicDataSource）中拿
     * 2 再从系统配置的数据源(sysDataSource)中拿，当数据源是从系统中取出来的时，追加到spring容器数据源中
     * 3 把拿到的数据源new一个JdbcTemplate
     * ps：所以，如果你在spring配置文件中配置的数据源别名跟系统配置的别名一样，会优先取spring配置文件中的
     * </pre>
     *
     * @param key
     * @return
     */
    JdbcTemplate getJdbcTemplateByKey(String key);

}
