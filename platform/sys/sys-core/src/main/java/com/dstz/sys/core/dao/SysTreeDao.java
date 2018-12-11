package com.dstz.sys.core.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.SysTree;

/**
 * 系统树 DAO接口
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-03-13 19:58:28
 */
@MapperScan
public interface SysTreeDao extends BaseDao<String, SysTree> {

}
