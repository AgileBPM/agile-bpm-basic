package com.dstz.base.api.model;

import java.util.List;

/**
 * <pre>
 * 描述：树结构对象，用于将列表数据转换成树结构。
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月13日 下午6:00:56
 * 版权:summer
 * </pre>
 *
 * @param <C> 声明子数据的类型，那么在实现类中就能直接使用实现类作为children。eg:SysDataDict
 */
public interface Tree<C extends Tree<?>> {

    /**
     * 主键ID
     *
     * @return
     */
    String getId();

    /**
     * 父ID
     *
     * @return
     */
    String getParentId();

    /**
     * 子对象。
     *
     * @return
     */
    List<C> getChildren();

    /**
     * 设置子对象。
     *
     * @param list
     */
    void setChildren(List<C> list);
}