package com.dstz.base.api.query;


/**
 * 排序
 */
public interface FieldSort {


    /**
     * 返回排序的字段名
     *
     * @return
     */
    public String getField();

    /**
     * 返回排序的类型 FieldSort.SORT_ASC 或	 FieldSort.SORT_DESC
     *
     * @return
     */
    public Direction getDirection();
}
