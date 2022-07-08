package com.dstz.sys.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dstz.base.api.aop.annotion.ParamValidate;
import com.dstz.base.api.response.impl.BaseResult;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.core.validate.ValidateUtil;
import com.dstz.base.db.model.page.PageResult;

/**
 * 提供接口响应aop拦截
 *
 * @author jeff
 * @date 2017-06-09
 */
@Aspect
@Component
public class ValidateParamAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around(value = "@annotation(paramValidate)")
    public Object doAround(ProceedingJoinPoint pjp, ParamValidate paramValidate) throws Throwable {
        Object result;
        Object[] objects = pjp.getArgs();
        logger.debug("参数拦截开始====={}" , JSON.toJSONString(objects));
        for (Object o : objects) {
            String msg = ValidateUtil.getValidateMsg(o);
            if (StringUtil.isNotEmpty(msg)) {
                logger.error("参数拦截信息{}",  msg);
                return getResult(pjp, msg);
            }
        }
        logger.debug("======参数拦截结束=====");
        result = pjp.proceed();
        return result;
    }


    private BaseResult getResult(ProceedingJoinPoint point, String error) {
        org.aspectj.lang.Signature signature = point.getSignature();
        Class returnType = ((MethodSignature) signature).getReturnType();

        //只有两种result类型
        BaseResult res;
        if (PageResult.class.equals(returnType)) {
            res = new PageResult();
        } else {
            res = new ResultMsg();
        }
        res.setOk(false);
        res.setMsg(error);
        return res;
    }

}
