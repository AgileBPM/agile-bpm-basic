package com.dstz.sys.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.sys.core.model.SysTree;

/**
 * 系统树 Manager处理接口
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-03-13 19:58:28
 */
public interface SysTreeManager extends Manager<String, SysTree> {
    /**
     * <pre>
     * 根据别名获取对象
     * </pre>
     *
     * @param key
     * @return
     */
    SysTree getByKey(String key);

    void removeContainNode(String id);
}
