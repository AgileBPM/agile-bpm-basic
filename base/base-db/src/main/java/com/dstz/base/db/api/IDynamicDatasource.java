package com.dstz.base.db.api;

public interface IDynamicDatasource {

    /**
     * 根据数据源别名获取数据库类型。
     *
     * @param alias
     * @return
     */
    String getDbTypeByAlias(String alias);

}
