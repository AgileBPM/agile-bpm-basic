package com.dstz.sys.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.sys.core.model.def.SysDataSourceDefAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.sys.core.manager.SysDataSourceDefManager;
import com.dstz.sys.core.model.SysDataSourceDef;

import java.util.List;

/**
 * <pre>
 * 描述：sysDataSourceDef层的controller
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:下午5:11:06
 * 版权:summer
 * </pre>
 */
@RestController
@RequestMapping("/sys/sysDataSourceDef/")
public class SysDataSourceDefController extends BaseController<SysDataSourceDef> {
    @Autowired
    SysDataSourceDefManager sysDataSourceDefManager;

    /**
     * <pre>
     * 根据类路径获取类字段
     * </pre>
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("initAttributes")
    @CatchErr(write2response = true, value = "初始化属性异常")
    public ResultMsg<List<SysDataSourceDefAttribute>> initAttributes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String classPath = RequestUtil.getString(request, "classPath");
        return getSuccessResult(sysDataSourceDefManager.initAttributes(classPath));
    }

    /**
     * <pre>
     * 获取sysDataSourceDef的后端
     * 目前支持根据id 获取sysDataSourceDef
     * </pre>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getObject")
    @CatchErr(write2response = true, value = "获取sysDataSourceDef异常")
    public ResultMsg<SysDataSourceDef> getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getString(request, "id");
        SysDataSourceDef sysDataSourceDef = null;
        if (StringUtil.isNotEmpty(id)) {
            sysDataSourceDef = sysDataSourceDefManager.get(id);
        }
        return getSuccessResult(sysDataSourceDef);
    }

	@Override
	protected String getModelDesc() {
		return "数据源模板";
	}
}
