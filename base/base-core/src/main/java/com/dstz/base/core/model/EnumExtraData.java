package com.dstz.base.core.model;

/**
 * 枚举扩展数据，用于扩展系统已定义枚举，用于引入后自定义相关数据处理实现
 *
 * @author wacxhs
 */
public interface EnumExtraData {

    /**
     * 获取枚举定义名字
     *
     * @return 枚举定义名称
     */
    String getName();

    /**
     * 获取定义KEY
     *
     * @return 枚举定义键
     */
    String getKey();

    /**
     * 获取定义描述
     *
     * @return 枚举定义描述
     */
    String getDesc();
}
