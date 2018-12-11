package com.dstz.bus.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.bus.model.BusColumnCtrl;

/**
 * <pre>
 * 描述：业务字段控件表 DAO接口
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2017-11-21 17:49:19
 * 版权：summer
 * </pre>
 */
@MapperScan
public interface BusColumnCtrlDao extends BaseDao<String, BusColumnCtrl> {

    /**
     * <pre>
     * 根据tableId删除BusColCtrl
     * </pre>
     *
     * @param tableId
     */
    void removeByTableId(String tableId);

    /**
     * <pre>
     * 根据columnId获取BusColCtrl
     * </pre>
     *
     * @param columnId
     * @return
     */
    BusColumnCtrl getByColumnId(String columnId);
}
