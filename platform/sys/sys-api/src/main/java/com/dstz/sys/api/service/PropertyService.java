package com.dstz.sys.api.service;

/**
 * 属性服务接口。
 */
public interface PropertyService {

    /**
     * 根据别名返回参数值。
     *
     * @param alias
     * @return
     */
    String getByAlias(String alias);

    /**
     * 根据别名返回参数值。
     *
     * @param alias        别名
     * @param defaultValue 默认值
     * @return
     */
    String getByAlias(String alias, String defaultValue);

    /**
     * 根据别名获取int参数值。
     *
     * @param alias
     * @return
     */
    Integer getIntByAlias(String alias);

    /**
     * 根据别名获取参数值。
     *
     * @param alias
     * @param defaulValue
     * @return
     */
    Integer getIntByAlias(String alias, Integer defaulValue);

    /**
     * 根据别名获取长整型参数值。
     *
     * @param alias
     * @return
     */
    Long getLongByAlias(String alias);

    /**
     * 根据别名获取布尔型参数值。
     *
     * @param alias
     * @return
     */
    boolean getBooleanByAlias(String alias);

    /**
     * 根据别名获取布尔型参数值。
     *
     * @param alias
     * @param defaulValue
     * @return
     */
    boolean getBooleanByAlias(String alias, boolean defaulValue);

}
