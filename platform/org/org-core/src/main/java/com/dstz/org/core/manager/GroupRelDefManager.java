package com.dstz.org.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.org.core.model.GroupRelDef;

/**
 * <pre>
 * 描述：组织关系定义 处理接口
 * </pre>
 */
public interface GroupRelDefManager extends Manager<String, GroupRelDef> {
    /**
     * 根据职务编码获取职务定义
     *
     * @param code
     * @return
     */
    public GroupRelDef getByCode(String code);

}
