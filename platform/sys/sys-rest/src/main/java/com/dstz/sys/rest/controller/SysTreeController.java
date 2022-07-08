package com.dstz.sys.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.rest.ControllerTools;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.sys.core.manager.SysTreeManager;
import com.dstz.sys.core.manager.SysTreeNodeManager;
import com.dstz.sys.core.model.SysTree;

/**
 * <pre>
 * 描述：sysTree层的controller
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:下午5:11:06
 * 版权:summer
 * </pre>
 */
@RestController
@RequestMapping("/sys/sysTree/")
public class SysTreeController extends ControllerTools {
    @Autowired
    SysTreeManager sysTreeManager;
    @Autowired
    SysTreeNodeManager sysTreeNodeManager;

    /**
     * <pre>
     * sysTreeEdit.html的save后端
     * </pre>
     *
     * @param request
     * @param response
     * @param sysTree
     * @throws Exception
     */
    @RequestMapping("save")
    @CatchErr(write2response = true, value = "保存系统树失败")
    public ResultMsg<SysTree> save(HttpServletRequest request, HttpServletResponse response, @RequestBody SysTree sysTree) throws Exception {
        if (StringUtil.isEmpty(sysTree.getId())) {
            sysTree.setId(IdUtil.getSuid());
            sysTreeManager.create(sysTree);
        } else {
            sysTreeManager.update(sysTree);
        }
        return getSuccessResult( sysTree, "保存系统树成功");
    }

    /**
     * <pre>
     * 获取sysTree的后端
     * </pre>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getObject")
    @CatchErr(write2response = true, value = "获取sysTree异常")
    public ResultMsg<SysTree> getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getString(request, "id");
        String key = RequestUtil.getString(request, "key");
        SysTree sysTree = null;
        if (StringUtil.isNotEmpty(id)) {
            sysTree = sysTreeManager.get(id);
        } else if (StringUtil.isNotEmpty(key)) {
            sysTree = sysTreeManager.getByKey(key);
        }
        return getSuccessResult(sysTree);
    }

    /**
     * <pre>
     * list页的后端
     * </pre>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("listJson")
    @ResponseBody
    public PageResult listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        List<SysTree> list = sysTreeManager.query(queryFilter);
        return new PageResult(list);
    }

    /**
     * <pre>
     * 批量删除
     * </pre>
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("remove")
    @CatchErr(write2response = true, value = "删除系统树失败")
    public ResultMsg<String> remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
        for (String id : aryIds) {
            sysTreeManager.removeContainNode(id);
        }
        return  getSuccessResult( "删除系统树成功");
    }
}
