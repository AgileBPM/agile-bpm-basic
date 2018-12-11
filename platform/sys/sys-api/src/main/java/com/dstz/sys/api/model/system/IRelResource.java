package com.dstz.sys.api.model.system;

public interface IRelResource {

    /**
     * 返回 主键
     *
     * @return
     */
    public String getId() ;
    /**
     * 返回 资源ID
     *
     * @return
     */
    public String getResId();
    /**
     * 返回 名称
     *
     * @return
     */
    public String getName() ;
    /**
     * 返回 资源地址
     *
     * @return
     */
    public String getResUrl() ;
 
}
