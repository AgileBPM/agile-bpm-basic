package com.dstz.sys.core.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.SysDataSource;

/**
 * <pre>
 * 描述：数据源 DAO接口
 * 构建组：白日梦团体
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018-01-08 21:14:05
 * 版权：summer
 * </pre>
 */
@MapperScan
public interface SysDataSourceDao extends BaseDao<String, SysDataSource> {
}
