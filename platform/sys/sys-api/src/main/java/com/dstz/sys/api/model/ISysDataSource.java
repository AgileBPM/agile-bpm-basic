package com.dstz.sys.api.model;


/**
 * <pre>
 * 描述：系统数据源
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月29日 上午10:21:25
 * 版权:summer
 * </pre>
 */
public interface ISysDataSource {
    /**
     * <pre>
     * 数据源的别名
     * </pre>
     *
     * @return
     */
    public String getKey();

    /**
     * <pre>
     * 名字
     * </pre>
     *
     * @return
     */
    public String getName();

    /**
     * <pre>
     * 描述
     * </pre>
     *
     * @return
     */
    public String getDesc();

    /**
     * <pre>
     * 数据库类型 枚举在com.dstz.base.api.db.DbType 的key
     * </pre>
     *
     * @return
     */
    public String getDbType();

}
