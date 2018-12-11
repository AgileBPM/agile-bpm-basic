package com.dstz.sys.core.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.SerialNo;

@MapperScan
public interface SerialNoDao extends BaseDao<String, SerialNo> {


    /**
     * 判读流水号别名是否已经存在
     *
     * @param id    id为null 表明是新增的流水号，否则为更新流水号
     * @param alias
     * @return
     */
    Integer isAliasExisted(@Param("id")String id, @Param("alias")String alias);

    /**
     * 根据别名获取流水号数据（数据库锁定了对应的行数据）
     *
     * @param alias
     * @return
     */
    SerialNo getByAlias(String alias);


    /**
     * 根据流程别名 。
     *
     * @param SerialNo void
     */
    int updByAlias(SerialNo SerialNo);
}
