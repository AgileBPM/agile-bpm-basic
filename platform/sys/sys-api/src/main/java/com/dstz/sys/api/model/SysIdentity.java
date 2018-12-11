package com.dstz.sys.api.model;

import java.io.Serializable;

/**
 * 描述：流程与组织挂接实体接口
 */
public interface SysIdentity extends Serializable{

    /**
     * 用户。
     */
    public final static String TYPE_USER = "user";
    /***role**/
    public final static String TYPE_ROLE = "role";

    /**
     * 视组织具体实现，这里参考提供
     **/
    public final static String TYPE_GROUP = "group";
    public final static String TYPE_ORG = "org";
    public final static String TYPE_POST = "post";
    public final static String TYPE_JOB = "job";

    /**
     * 实体ID
     *
     * @return String
     */
    String getId();

    /**
     * 设置实体ID
     *
     * @param id void
     */
    void setId(String id);

    /**
     * 实体名称
     *
     * @return String
     */
    String getName();

    /**
     * 设置实体名称。
     *
     * @param name void
     */
    void setName(String name);

    /**
     * user
     * group
     *
     * @return String
     */
    String getType();

    /**
     * 实体分类。
     *
     * @param type void
     */
    void setType(String type);

}
