package com.dstz.sys.core.manager.impl;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.support.http.util.IPAddress;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.base.rest.util.IPAddressUtil;
import com.dstz.sys.core.dao.LogErrDao;
import com.dstz.sys.core.manager.LogErrManager;
import com.dstz.sys.core.model.LogErr;

/**
 * <pre>
 * 描述：错误日志 处理实现类
 * </pre>
 */
@Service("sysLogErrManager")
public class LogErrManagerImpl extends BaseManager<String, LogErr> implements LogErrManager {
    @Resource
    LogErrDao sysLogErrDao;

    
    @Override
    public void create(LogErr entity) {
    	String ip = entity.getIp();
    	if(StringUtil.isNotEmpty(ip)) {
    		try {
    			entity.setIpAddress(IPAddressUtil.getAddresses(ip));
			} catch (UnsupportedEncodingException e) {
			}
    	}
    	
    	sysLogErrDao.create(entity);
    }
    
    
    
}
