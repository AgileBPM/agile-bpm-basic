package com.dstz.org.api.model;

import java.io.Serializable;
import java.util.Map;

import com.dstz.base.api.model.IBaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 描述：抽象用户组类型
 */
@ApiModel(description="组定义，含org,role,post 组类型详见 GroupTypeConstant.java")
public interface IGroup extends Serializable {

    /**
     * 组织ID
     *
     * @return
     */
    @ApiModelProperty("组ID")
    String getGroupId();

    /**
     * 组织名称
     *
     * @return
     */
    @ApiModelProperty("组名字")
    String getGroupName();

    /**
     * 组织编码
     *
     * @return
     */
    @ApiModelProperty("组CODE")
    String getGroupCode();

    /**
     * 组织类型。
     * 比如：org,role,post
     *
     * @return
     */
    @ApiModelProperty("组类型：org,role,post")
    String getGroupType();

    /**
     * 上级ID
     *
     * @return
     */
    @ApiModelProperty("树形组 parentId")
    String getParentId();

}
