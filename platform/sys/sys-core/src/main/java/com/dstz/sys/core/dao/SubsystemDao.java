package com.dstz.sys.core.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.Subsystem;

/**
 * <pre>
 * 描述：子系统定义 DAO接口
 * </pre>
 */
@MapperScan
public interface SubsystemDao extends BaseDao<String, Subsystem> {

    /**
     * 判断别名是否存在
     *
     * @param subsystem
     * @return
     */
	Integer isExist(Subsystem subsystem);

    /**
     * 获取子系统列表。
     *
     * @return
     */
    List<Subsystem> getList();

    /**
     * 更新为默认。
     */
    void updNoDefault();


    /**
     * 根据用户获取子系统列表。
     *
     * @param userId
     * @return
     */
    List<Subsystem> getSystemByUser(String userId);
}
