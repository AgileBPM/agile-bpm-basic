package com.dstz.sys.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.sys.core.model.SysDataSourceDef;
import com.dstz.sys.core.model.def.SysDataSourceDefAttribute;

import java.util.List;

/**
 * <pre>
 * 描述：数据源模板 Manager处理接口
 * 构建组：白日梦团体
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018-01-03 18:04:15
 * 版权：summer
 * </pre>
 */
public interface SysDataSourceDefManager extends Manager<String, SysDataSourceDef> {

    /**
     * <pre>
     * 根据classPath类路径获取数据源的配置参数
     * </pre>
     *
     * @param classPath
     * @return
     */
    List<SysDataSourceDefAttribute> initAttributes(String classPath);

}
