package com.dstz.sys.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.sys.core.manager.SysDataSourceDefManager;
import com.dstz.sys.core.model.SysDataSourceDef;

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
    public void initAttributes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String classPath = RequestUtil.getString(request, "classPath");
        writeSuccessData(response, sysDataSourceDefManager.initAttributes(classPath));
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
    public void getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getString(request, "id");
        SysDataSourceDef sysDataSourceDef = null;
        if (StringUtil.isNotEmpty(id)) {
            sysDataSourceDef = sysDataSourceDefManager.get(id);
        }
        writeSuccessData(response, sysDataSourceDef);
    }

	@Override
	protected String getModelDesc() {
		return "数据源模板";
	}
}
