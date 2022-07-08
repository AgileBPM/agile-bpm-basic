package com.dstz.sys.aop;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.constant.IStatusCode;
import com.dstz.base.api.exception.BusinessError;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.ExceptionUtil;
import com.dstz.base.core.util.RequestContext;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.api.constant.EnvironmentConstant;
import com.dstz.sys.core.manager.LogErrManager;
import com.dstz.sys.core.model.LogErr;
import com.dstz.sys.util.ContextUtil;

/**
 * @author jeff
 * @说明 使用AOP拦截出现异常的Controller, service的方法，并反馈标准的异常描述，记录日志<br>
 * 在需要拦截的方法之前添加注解  {@CatchErr }<br>
 * 一般使用在【不需要事物控制】的方法中，比如controller或者服务接口
 * @eg:创建用户方法@CatchErr 新增的账户存在 则可以throw new BusException("账户已存在");
 * 则前端会接受到这个result的标准json。服务接口也是如此
 * 该方法避免了所有服务接口捕获异常反馈信息的重复操作
 */
@Aspect
@Component
public class ErrAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrAspect.class);

    @Resource
    LogErrManager logErrManager;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Pointcut("@annotation(com.dstz.base.aop.annotion.CatchErr)")
    public void controllerAspect() {

    }


    @Around(value = "@annotation(catchErr)")
    public Object doAudit(ProceedingJoinPoint point, CatchErr catchErr) throws Throwable {
        Object returnVal = null;
        try {
            returnVal = point.proceed();
        } catch (Exception ex) {
            //如果非业务异常则记录日志
            String errorMessage = ExceptionUtil.getRootErrorMseeage(ex);
            String exception = ExceptionUtil.getExceptionMessage(ex);

            ResultMsg resultMsg = null;
            if (!(ex instanceof BusinessMessage)) {
                LOGGER.error("操作出现异常     {}.{} ", point.getTarget().getClass(), point.getSignature().getName(), ex);
                IStatusCode errorCode = BaseStatusCode.SYSTEM_ERROR;
                // 假如是包装异常则获取具体异常码，以及包装后的异常信息
                if(ex instanceof BusinessException) {
                	errorCode = ((BusinessException)ex).getStatusCode();
                	errorMessage = ex.getMessage();
                }else if(ex instanceof BusinessError) {
                	errorCode = ((BusinessError)ex).getStatusCode();
                	errorMessage = ex.getMessage();
                }

                
                String errorId = logError(point, errorMessage, exception);
                errorMessage = "errorCode[" + errorId + "] : " + errorMessage;
                
                // 生产环境 提示  系统异常，为了不暴露系统架构。而且提示具体异常会引起客户恐慌，增加用户不信任感。
                if(AppUtil.getCtxEnvironment().contains(EnvironmentConstant.PROD.key())) {
                	resultMsg = new ResultMsg(errorCode, errorCode.getDesc());
                	//resultMsg.setCause(error);//可以通过控制台看到具体异常，方便快速定位。也可以删除，呵呵
                }else {
                	resultMsg = new ResultMsg(errorCode, errorMessage);
                }
            } else {
            	BusinessMessage busEx = (BusinessMessage) ex;
                errorMessage = ex.getMessage();
                resultMsg = new ResultMsg(busEx.getStatusCode(), errorMessage);
            }


            // 若返回值是resultType 则返回错误
            org.aspectj.lang.Signature signature = point.getSignature();
            Class returnType = ((MethodSignature) signature).getReturnType();
            if (returnType.isAssignableFrom(ResultMsg.class)) {
            	return resultMsg;
            }
            
            writeResultMessage2Writer(point, resultMsg, catchErr.write2response());
        }

        return returnVal;
    }
    

    /**
     * 假如是void
     *
     * @param point
     * @param resultMsg
     * @throws Exception
     */
    private void writeResultMessage2Writer(ProceedingJoinPoint point, ResultMsg resultMsg, boolean iswrite) throws Exception {
        org.aspectj.lang.Signature signature = point.getSignature();
        Class returnType = ((MethodSignature) signature).getReturnType();
        HttpServletResponse response = null;

        Object[] objects = point.getArgs();
        for (Object o : objects) {
            if (o instanceof HttpServletResponse) {
                response = (HttpServletResponse) o;
            }
        }
       
        //假如 http 请求，且void方法时，写入response
        if (/*void.class.equals(returnType) &&*/ response != null) {
            iswrite = true;
        }

        if (!iswrite) return;

        if (response == null) {
            //	response = RequestContext.getHttpServletResponse();
        }

        if (response != null) {
            response.getWriter().write(JSON.toJSONString(resultMsg));
        }
    }

    private String logError(ProceedingJoinPoint point, String error, String exception) throws IOException {
    	HttpServletRequest request = RequestContext.getHttpServletRequest();
        String errorurl = request.getRequestURI();
	    String ip = RequestUtil.getIpAddr(request);
	    
        IUser sysUser = ContextUtil.getCurrentUser();
        String account = "未知用户";
        if (sysUser != null) {
            account = sysUser.getAccount();
        }
        String id = IdUtil.getSuid();
        LogErr logErr = new LogErr();
        logErr.setId(id);
        logErr.setAccount(account);
        logErr.setIp(ip);
        logErr.setContent(error);
        
        String requestParam = JSON.toJSONString(request.getParameterMap());
        if(StringUtils.isEmpty(requestParam) || requestParam.length()<3) {
        	requestParam = "";
        	for(Object o: point.getArgs()) {
        		if(o instanceof ServletRequest || o instanceof ServletResponse) continue;
        		requestParam += JSON.toJSONString(o);
        	}
        }
        logErr.setRequestParam(requestParam);
        logErr.setUrl(StringUtils.substring(errorurl, 0, 1000));
        logErr.setCreateTime(new Date());
        logErr.setStackTrace(exception);

        //异步写入到数据库当中
        threadPoolTaskExecutor.execute(()-> logErrManager.create(logErr));

        return id;
    }

}