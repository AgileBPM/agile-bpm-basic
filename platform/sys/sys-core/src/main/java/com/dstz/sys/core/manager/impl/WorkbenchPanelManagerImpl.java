package com.dstz.sys.core.manager.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.datasource.DbContextHolder;
import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.api.constant.RightsObjectConstants;
import com.dstz.sys.core.dao.WorkbenchPanelDao;
import com.dstz.sys.core.manager.SysAuthorizationManager;
import com.dstz.sys.core.manager.WorkbenchLayoutManager;
import com.dstz.sys.core.manager.WorkbenchPanelManager;
import com.dstz.sys.core.model.WorkbenchLayout;
import com.dstz.sys.core.model.WorkbenchPanel;
import com.dstz.sys.util.ContextUtil;

import cn.hutool.core.collection.CollectionUtil;


@Service("workbenchPanelManager")
public class WorkbenchPanelManagerImpl extends BaseManager<String, WorkbenchPanel> implements WorkbenchPanelManager {
    @Resource
    WorkbenchPanelDao workbenchPanelDao;
    @Resource
    WorkbenchLayoutManager workbenchLayoutManager;

    @Resource
    SysAuthorizationManager sysAuthorizationManager;


    @Override
    public List<WorkbenchPanel> getByUserId(String userId) {
        Map<String, Object> userPermission = sysAuthorizationManager.getUserRightsSql(RightsObjectConstants.WORKBENCH, userId, "p.id_");
        userPermission.put("userId", userId);
        
        userPermission.put("dbType", DbContextHolder.getDbType());

        List<WorkbenchPanel> layOut = workbenchPanelDao.getByUser(userPermission);

        if (CollectionUtil.isEmpty(layOut)) {
            userPermission.put("userId", WorkbenchLayout.DEFAULT_LAYOUT);
            layOut = workbenchPanelDao.getByUser(userPermission);
        }

        return layOut;
    }	


    @Override
    public List<WorkbenchPanel> getBylayoutKey(String layoutKey) {
    	String dbType = DbContextHolder.getDbType();
        return workbenchPanelDao.getBylayoutKey(layoutKey,dbType);
    }

    @Override
    public List<WorkbenchPanel> getMyUsablePanels(QueryFilter query) {
        //获取默认的布局
        String layoutKey = (String) query.getParams().get("layoutKey");
        if (StringUtil.isNotEmpty(layoutKey)) {
            return workbenchPanelDao.query();
        }

        String userId = ContextUtil.getCurrentUserId();
        Map<String, Object> userPermission = sysAuthorizationManager.getUserRightsSql(RightsObjectConstants.WORKBENCH, userId, null);
        query.getParams().putAll(userPermission);

        DefaultQueryFilter queryFilter = (DefaultQueryFilter) query;
        queryFilter.setPage(null);

        return workbenchPanelDao.getUsablePanelsByUserRight(queryFilter);
    }

    @Override
    public JSON getPanelData(Map<String, String> requstParam) {

        return null;
    }


    @Override
    public JSON getDataByInterFace(QueryFilter filter, String dataSource) {

        if (StringUtil.isEmpty(dataSource)) throw new RuntimeException("自定义对话框数据服务接口不能为空！");

        String[] aryHandler = dataSource.split("[.]");
        if (aryHandler == null || aryHandler.length != 2) throw new RuntimeException("自定义对话框数据服务接口格式不正确！" + dataSource);

        String beanId = aryHandler[0];
        String method = aryHandler[1];

        // 触发该Bean下的业务方法
        Object serviceBean = AppUtil.getBean(beanId);
        if (serviceBean == null) return null;
        Object objct = null;
        try {
            objct = invokeMethod(serviceBean, method, filter);
        } catch (Exception e) {
            throw new RuntimeException("查询异常！" + e.getMessage(), e);
        }

        return (JSON) JSON.toJSON(objct);
    }


    private Object invokeMethod(Object serviceBean, String method, QueryFilter filter) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class clazz = serviceBean.getClass();

        Method invokeMethod = null;
        Object o = null;

        try {
            invokeMethod = clazz.getMethod(method);
            o = invokeMethod.invoke(serviceBean);

        } catch (NoSuchMethodException e) {
        }
        if (invokeMethod == null) {
            try {
                invokeMethod = clazz.getMethod(method, QueryFilter.class);
                o = invokeMethod.invoke(serviceBean, filter);
            } catch (NoSuchMethodException e) {
                throw new BusinessException(String.format("被调用方法%s.%s 入参为QueryFilter,或者为空", serviceBean, method));
            }
        }

        if (void.class == invokeMethod.getReturnType()) {
            throw new BusinessException(String.format("被调用方法%s.%s 返回值不能为void！", serviceBean, method));
        }

        return o;
    }

    /**
     * 测试用
     *
     * @return
     */
    @Override
    public JSON getTestData() {

        String json = "[[\"product\", \"纯牛奶\", \"咖啡\", \"矿泉水\"]," // 产品项
                + "[\"2015\"," + getRandomInt() + ", " + getRandomInt() + ", " + getRandomInt() + "], [\"2016\", " + getRandomInt() + ", " + getRandomInt() + ", " + getRandomInt() + "],"
                + "[\"2017\", " + getRandomInt() + ", " + getRandomInt() + ", " + getRandomInt() + "], [\"2018\"," + getRandomInt() + ", " + getRandomInt() + ", " + getRandomInt() + "]]";
        JSONArray jsonArray = JSONArray.parseArray(json);

        return jsonArray;
    }

    /**
     * 测试用
     *
     * @return
     */
    @Override
    public JSON getPieData() {
        String json = "[[\"纯牛奶\"," + getRandomInt() + "]," // 产品项
                + "[\"咖啡\"," + getRandomInt() + "],"
                + "[\"矿泉水\"," + getRandomInt() + "],"
                + "[\"碳酸饮料\"," + getRandomInt() + "]]";
        JSONArray jsonArray = JSONArray.parseArray(json);
        return jsonArray;
    }

    private int getRandomInt() {
        Random rand = new Random();
        return rand.nextInt(6000);
    }

}
