package com.dstz.bus.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.bus.model.BusinessObject;

/**
 * <pre>
 * 描述：BusinessObject Dao层
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:下午8:08:21
 * 版权:summer
 * </pre>
 */
@MapperScan
public interface BusinessObjectDao extends BaseDao<String, BusinessObject> {
}
