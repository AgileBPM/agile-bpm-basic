package com.dstz.org.rest.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.service.GroupService;
import com.dstz.org.api.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 组
 */
@RestController
@RequestMapping("/org/groupService")
@Api(description="用户组服务接口")
public class GroupServiceController{
    @Resource
    GroupService groupService;
    @Resource
    UserService userService;
    
    /**
     * 根据用户ID和组类别获取相关的组。
     *
     * @param groupType 用户组类型
     * @param userId    用户ID
     * @return 接口信息
     */
	@ApiOperation(value = "获取用户下的某种组", notes = "根据用户ID，组类别  来获取相关的组")
    @PostMapping("/getGroupsByGroupTypeUserId")
    public ResultMsg<List<? extends IGroup>> getGroupsByGroupTypeUserId(
    		@RequestParam("groupType") @ApiParam(value = "组织类型：org,post,role", required = true) String groupType,
    		@RequestParam("userId") @ApiParam(value = "用户ID", required = true) String userId) {
        List<? extends IGroup> groupList = groupService.getGroupsByGroupTypeUserId(groupType, userId);
        return new ResultMsg<>(groupList);
    }

    /**
     * 根据用户账号获取用户当前所在的组。
     *
     * @param account 用户帐号
     * @return 结果信息
     */
	@ApiOperation(value = "通过用户账户，获取用户下的组织，结果以组类型为mapKey", notes = "根据用户账户，来获取相关的组，结果以组类型为mapKey")
    @GetMapping("/getAllGroupByAccount/{account}")
    public ResultMsg<Map<String, List<? extends IGroup>>> getAllGroupByAccount(
    		@PathVariable("account") @ApiParam(value = "用户账户", required = true) String account) {
        Map<String, List<? extends IGroup>> map = groupService.getAllGroupByAccount(account);
        return new ResultMsg<>(map);
    }


    /**
     * 获取用户所在的所有组织。
     *
     * @param userId 用户ID
     * @return 结果信息
     */
    @GetMapping("/getAllGroupByUserId/{userId}")
    @ApiOperation(value = "获取用户下的组织，结果以组类型为mapKey", notes = "根据用户id 获取用户当前所在的组，结果以组类型为mapKey")
    public ResultMsg<Map<String, List<? extends IGroup>>> getAllGroupByUserId(
    		@PathVariable("userId") @ApiParam(value = "用户ID", required = true) String userId) {
        Map<String, List<? extends IGroup>> map = groupService.getAllGroupByUserId(userId);
        return new ResultMsg<>(map);
    }


    /**
     * 根据用户获取用户所属的组。
     *
     * @param userId 用户编号
     * @return 结果信息
     */
    @GetMapping("/getGroupsByUserId/{userId}")
    @ApiOperation(value = "根据用户id，获取改用户所有组织列表", notes = "根据用户id 获取用户当前所在的组")
    public ResultMsg<List<? extends IGroup>> getGroupsByUserId(@PathVariable("userId") @ApiParam(value = "用户ID", required = true) String userId) {
        List<? extends IGroup> groupList = groupService.getGroupsByUserId(userId);
        return new ResultMsg<>(groupList);
    }


    /**
     * 根据组织ID和类型获取组织对象。
     *
     * @param groupType 组别
     * @param groupId   组编号
     * @return 结果信息
     */
    @PostMapping("/getById")
    @ApiOperation(value = "根据组织ID,获取组织详情信息", notes = "根据用户id,组织类型查找组织信息")
    public ResultMsg<IGroup> getById(@RequestParam("groupType") @ApiParam(value = "组织类型：GroupTypeConstant", required = true) String groupType, 
    				@RequestParam("groupId")@ApiParam(value = "组织ID", required = true) String groupId) {
        IGroup group = groupService.getById(groupType, groupId);
        return new ResultMsg<>(group);
    }


    /**
     * 根据组织ID和类型获取组织对象。
     *
     * @param groupType 组别
     * @param code      编码
     * @return 结果信息
     */
    @PostMapping("/getByCode")
    @ApiOperation(value = "根据组织CODE,获取组织详情信息", notes = "根据用户CODE,组织类型查找组织信息")
    public ResultMsg<IGroup> getByCode(@RequestParam("groupType")@ApiParam(value = "组织类型：GroupTypeConstant", required = true) String groupType,
    		@RequestParam("code") @ApiParam(value = "组织code", required = true) String code) {
        IGroup group = groupService.getByCode(groupType, code);
        return new ResultMsg<>(group);
    }


    /**
     * 用户编号查到主组织
     *
     * @param userId 用户编号
     * @return 结果信息
     */
    @ApiOperation(value = "获取用户的主组织", notes = "获取用户的主岗位、若未配置主岗位默认获取查询的第一个组织")
    @GetMapping("/getMainGroup/{userId}")
    public ResultMsg<IGroup> getMainGroup(@PathVariable("userId") @ApiParam(value = "用户ID", required = true) String userId) {
        IGroup group = groupService.getMainGroup(userId);
        return new ResultMsg<>(group);
    }
    
    
    
}
