package com.dstz.sys.core.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.SysDataSourceDef;

/**
 * <pre>
 * 描述：数据源模板 DAO接口
 * 构建组：白日梦团体
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018-01-03 18:04:15
 * 版权：summer
 * </pre>
 */
@MapperScan
public interface SysDataSourceDefDao extends BaseDao<String, SysDataSourceDef> {
}
