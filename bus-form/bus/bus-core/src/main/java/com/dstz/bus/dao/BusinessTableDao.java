package com.dstz.bus.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.bus.model.BusinessTable;

/**
 * <pre>
 * 描述：业务表 DAO接口
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2017-11-21 17:49:19
 * 版权：summer
 * </pre>
 */
@MapperScan
public interface BusinessTableDao extends BaseDao<String, BusinessTable> {
}
