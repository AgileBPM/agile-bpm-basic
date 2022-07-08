package com.dstz.org.api.model.system;

import java.util.List;

import com.dstz.base.api.model.Tree;

public interface ISysResource extends Tree{
	
	
   /**
     * 返回 主键
     *
     * @return
     */
    public String getId() ;
    /**
     * 返回 子系统ID
     *
     * @return
     */
    public String getSystemId() ;
    /**
     * 返回 资源别名
     *
     * @return
     */
    public String getAlias() ;
    
    public String getKey();
    /**
     * 返回 资源名
     *
     * @return
     */
    public String getName() ;
    /**
     * 返回 默认地址
     *
     * @return
     */
    public String getUrl();
    /**
     * 返回 显示到菜单(1,显示,0 ,不显示)
     *
     * @return
     */
    public Integer getEnable();
    /**
     * 返回 OPENED_
     *
     * @return
     */
    public Integer getOpened() ;
    /**
     * 返回 图标
     *
     * @return
     */
    public String getIcon();

    /**
     * 返回类型
     *
     * @return
     */
    public String getType();
    /**
     * 返回 排序
     *
     * @return
     */
    public Integer getSn();


    public String getParentId() ;
 
    public List getChildren();

  
}
