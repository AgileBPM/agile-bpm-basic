package com.dstz.bus.dao;

import com.dstz.base.dao.BaseDao;
import com.dstz.bus.model.BusinessColumn;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

/**
 * <pre>
 * 描述：业务字段表 DAO接口
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2017-11-21 17:49:19
 * 版权：summer
 * </pre>
 */
@MapperScan
public interface BusinessColumnDao extends BaseDao<String, BusinessColumn> {

    /**
     * <pre>
     * 根据tableId删除字段
     * </pre>
     *
     * @param tableId
     */
    void removeByTableId(String tableId);

    /**
     * <pre>
     * 根据tableId获取字段
     * </pre>
     *
     * @param tableId
     * @return
     */
    List<BusinessColumn> getByTableId(String tableId);
}
