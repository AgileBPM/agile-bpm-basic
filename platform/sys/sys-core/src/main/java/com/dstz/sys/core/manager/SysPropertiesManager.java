package com.dstz.sys.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.sys.core.model.SysProperties;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 描述：SYS_PROPERTIES 处理接口
 * </pre>
 */
public interface SysPropertiesManager extends Manager<String, SysProperties> {


    /**
     * 分组列表。
     *
     * @return
     */
    List<String> getGroups();

    /**
     * 判断别名是否存在。
     *
     * @param sysProperties
     * @return
     */
    boolean isExist(SysProperties sysProperties);


    /**
     * 重新读取属性配置。
     *
     * @return
     */
    Map<String, Map<String, String>> reloadProperty();

}
