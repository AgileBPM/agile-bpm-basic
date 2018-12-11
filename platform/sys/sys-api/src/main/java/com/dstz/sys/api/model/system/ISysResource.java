package com.dstz.sys.api.model.system;

import java.util.List;

public interface ISysResource {
	
	
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
    public String getDefaultUrl();
    /**
     * 返回 显示到菜单(1,显示,0 ,不显示)
     *
     * @return
     */
    public Integer getEnableMenu();
    /**
     * 返回 是否有子节点
     *
     * @return
     */
    public Integer getHasChildren();
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
     * 返回 打开新窗口
     *
     * @return
     */
    public Integer getNewWindow();
    /**
     * 返回 排序
     *
     * @return
     */
    public Long getSn();


    public String getParentId() ;
    public List<IRelResource> getRelResources();
 
    public List getChildren();

  
}
