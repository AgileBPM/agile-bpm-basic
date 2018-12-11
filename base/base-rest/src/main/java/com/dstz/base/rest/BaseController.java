package com.dstz.base.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.model.IDModel;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.manager.Manager;
import com.github.pagehelper.Page;

/**
 * <pre>
 * 描述：controller的基础类
 * 包含了常用的增删查改的方法，需要定制化可自行覆盖
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年4月3日 下午8:41:30
 * 版权:summer
 * </pre>
 */
public abstract class BaseController<T extends IDModel> extends GenericController{
	
    protected abstract String getModelDesc();

    @Autowired
    Manager<String,T> manager;
    
    /**
     * 分页列表
     */
    @RequestMapping("listJson")
    public PageResult listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        Page<T> pageList = (Page<T>) manager.query(queryFilter);
        return new PageResult(pageList);
    }

    /**
     * 获取对象
     */
    @RequestMapping("get")
    @CatchErr
    public ResultMsg<T> get(@RequestParam String id) throws Exception {
    	T t = null;
    	if(StringUtil.isNotEmpty(id)) {
    		t = manager.get(id);
    	}
       return getSuccessResult(t);
    }

    /**
     * 保存
     */
    @RequestMapping("save")
    @CatchErr
    public ResultMsg<String> save(@RequestBody T t) throws Exception {
        String desc;
        if (StringUtil.isEmpty(t.getId())) {
            desc = "添加%s成功";
            manager.create(t);
        } else {
            manager.update(t);
            desc = "更新%s成功";
        }
        return getSuccessResult(String.format(desc, getModelDesc()));
    }

    /**
     * 批量删除
     */
    @RequestMapping("remove")
    @CatchErr
    public ResultMsg<String> remove(@RequestParam String id) throws Exception {
         String[] aryIds = StringUtil.getStringAryByStr(id);
         manager.removeByIds(aryIds);
         return getSuccessResult(String.format("删除%s成功", getModelDesc()));
    }
  
}