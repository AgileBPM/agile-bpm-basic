package com.dstz.sys.core.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.LogErr;

/**
 * <pre>
 * 描述：错误日志 DAO接口
 * </pre>
 */
@MapperScan
public interface LogErrDao extends BaseDao<String, LogErr> {
}
