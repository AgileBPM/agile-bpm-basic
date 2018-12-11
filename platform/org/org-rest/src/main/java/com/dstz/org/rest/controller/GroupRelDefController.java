package com.dstz.org.rest.controller;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.Manager;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.core.manager.GroupRelDefManager;
import com.dstz.org.core.model.GroupRelDef;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 * 描述：组织关系定义 控制器类
 * </pre>
 */
@RestController
@RequestMapping("/org/groupRelDef")
public class GroupRelDefController extends BaseController<GroupRelDef> {
    @Resource
    GroupRelDefManager groupRelDefManager;


    @RequestMapping("getAllJson")
    public List<GroupRelDef> getAllJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<GroupRelDef> orgReldefList = groupRelDefManager.getAll();
        return orgReldefList;
    }

    /**
     * @return 
     * 保存组织关系定义信息
     *
     * @param request
     * @param response
     * @param orgReldef
     * @throws Exception void
     * @throws
     */
    @RequestMapping("save")
    @CatchErr
    @Override
    public ResultMsg<String> save(@RequestBody GroupRelDef orgReldef) throws Exception {

        if (StringUtil.isEmpty(orgReldef.getId())) {
            GroupRelDef temp = groupRelDefManager.getByCode(orgReldef.getCode());
            if (temp != null) throw new BusinessMessage("code已存在，不可重复");
        }

      return  super.save( orgReldef);
    }


    @RequestMapping("isExist")
    public boolean isExist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getString(request, "id");
        String code = RequestUtil.getString(request, "key");
        if (StringUtil.isNotEmpty(id))
            return false;
        if (StringUtil.isNotEmpty(code)) {
            GroupRelDef temp = groupRelDefManager.getByCode(code);
            return temp != null;
        }
        return false;
    }

    @Override
    protected String getModelDesc() {
        return "职务";
    }
}
