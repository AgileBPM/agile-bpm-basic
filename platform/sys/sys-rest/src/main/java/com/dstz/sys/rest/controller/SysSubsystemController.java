package com.dstz.sys.rest.controller;


import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.github.pagehelper.Page;
import com.dstz.base.rest.GenericController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.core.manager.SubsystemManager;
import com.dstz.sys.core.model.Subsystem;
import com.dstz.sys.core.model.Subsystem;
import com.dstz.sys.util.ContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * <pre>
 * 描述：子系统定义 控制器类
 * </pre>
 */
@Controller
@RequestMapping("/sys/subsystem")
public class SysSubsystemController extends GenericController {
    @Resource
    SubsystemManager subsystemManager;

    /**
     * 子系统定义列表(分页条件查询)数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception PageJson
     * @throws
     */
    @RequestMapping("listJson")
    public @ResponseBody
    PageResult listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        Page<Subsystem> subsystemList = (Page<Subsystem>) subsystemManager.query(queryFilter);
        return new PageResult(subsystemList);
    }

    @RequestMapping("getUserSystem")
    @CatchErr(write2response = true)
    public @ResponseBody
    void getUserSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!ContextUtil.currentUserIsAdmin()) {
            throw new BusinessException("目前仅仅支持超管操作，尚未开发分管授权功能！");
        }

        List Subsystem = subsystemManager.getAll();
        writeSuccessData(response, Subsystem);
    }

    /**
     * 子系统定义明细页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception ModelAndView
     */
    @RequestMapping("getJson")
    public @ResponseBody
    Subsystem getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getString(request, "id");
        if (StringUtil.isEmpty(id)) {
            return null;
        }
        Subsystem subsystem = subsystemManager.get(id);
        return subsystem;
    }

    /**
     * 保存子系统定义信息
     *
     * @param request
     * @param response
     * @param subsystem
     * @throws Exception void
     * @throws
     */
    @RequestMapping("save")
    public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody Subsystem subsystem) throws Exception {
        String resultMsg = null;

        boolean isExist = subsystemManager.isExist(subsystem);
        if (isExist) {
            resultMsg = "别名子系统中已存在!";
        //    writeResultMessage(response.getWriter(), resultMsg, ResultMsg.FAIL);
            return;
        }

        String id = subsystem.getId();
        try {
            if (StringUtil.isEmpty(id)) {
                subsystem.setId(IdUtil.getSuid());
                IUser user = ContextUtil.getCurrentUser();
                subsystem.setCreator(user.getFullname());
                subsystem.setCreatorId(user.getUserId());
                subsystemManager.create(subsystem);
                resultMsg = "添加子系统定义成功";
            } else {
                subsystemManager.update(subsystem);
                resultMsg = "更新子系统定义成功";
            }
      //      writeResultMessage(response.getWriter(), resultMsg, ResultMsg.SUCCESS);
        } catch (Exception e) {
            resultMsg = "对子系统定义操作失败";
    //        writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMsg.FAIL);
        }
    }

    /**
     * 批量删除子系统定义记录
     *
     * @param request
     * @param response
     * @throws Exception void
     * @throws
     */
    @RequestMapping("remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMsg message = null;
        try {
            String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
            subsystemManager.removeByIds(aryIds);
            message = new ResultMsg(ResultMsg.SUCCESS, "删除子系统定义成功");
        } catch (Exception e) {
            message = new ResultMsg(ResultMsg.FAIL, "删除子系统定义失败");
        }
        writeResultMessage(response.getWriter(), message);
    }
}
