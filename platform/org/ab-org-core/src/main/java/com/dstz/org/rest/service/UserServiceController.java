package com.dstz.org.rest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.model.IUserRole;
import com.dstz.org.api.service.UserService;
import com.dstz.org.core.manager.UserManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <pre>
 * 描述：用户表 控制器类
 * </pre>
 */
@RestController
@RequestMapping("/org/userService")
@Api(description="用户信息服务接口")
public class UserServiceController {
    @Resource
    UserManager userManager;
    
    
    @Autowired
    private UserService userService;


    /**
     * 根据用户ID获取用户的对象。
     *
     * @param userId 用户编号
     * @return 结果信息
     */
    @GetMapping("/getUserById/{userId}")
    @ApiOperation(value = "获取用户信息",notes="根据用户ID获取用户的对象")
    public ResultMsg<IUser> getUserById(@PathVariable("userId") @ApiParam(value = "用户ID", required = true) String userId) {
        IUser user = userService.getUserById(userId);
        return new ResultMsg<>(user);
    }


    /**
     * 根据用户帐号获取用户对象。
     *
     * @param account 账户
     * @return 结果信息
     */
    @GetMapping("/getUserByAccount/{account}")
    @ApiOperation(value = "获取用户信息",notes="根据用户帐号获取用户对象")
    public ResultMsg<IUser> getUserByAccount(@PathVariable("account") @ApiParam(value = "用户账户", required = true) String account) {
        final IUser user = userService.getUserByAccount(account);
        return new ResultMsg<>(user);
    }


    /**
     * 根据组织id和组织类型获取用户列表。
     *
     * @param groupId   组织列表
     * @param groupType 组织类型
     * @return 结果信息
     */
    @PostMapping("/getUserListByGroup")
    @ApiOperation(value = "获取组用户",notes="根据某组织下的用户")
    public ResultMsg<List<? extends IUser>> getUserListByGroup(@RequestParam("groupType") @ApiParam(value = "组类型：org,post,role", required = true) String groupType,
    			@RequestParam("groupId") @ApiParam(value = "组ID", required = true) String groupId) {
        List<? extends IUser> userList = userService.getUserListByGroup(groupType, groupId);
        return new ResultMsg<>(userList);
    }

    /**
     * 获取用户的角色
     *
     * @param userId 用户编号
     * @return 结果信息
     */
    @GetMapping("/getUserRole/{userId}")
    @ApiOperation(value = "获取组用户的角色",notes="根据用户ID 获取该用户的角色")
    public ResultMsg<List<? extends IUserRole>> getUserRole(@PathVariable("userId") String userId) {
        List<? extends IUserRole> userRoleList = userService.getUserRole(userId);
        return new ResultMsg<>(userRoleList);
    }

    
    
    
    
    
}
