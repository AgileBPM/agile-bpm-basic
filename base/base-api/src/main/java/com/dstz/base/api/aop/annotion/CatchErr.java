package com.dstz.base.api.aop.annotion;

import java.lang.annotation.*;

/**
 * @param write2response 是否写入response中
 * @param value          暂时没用
 * @author jeff
 * @description <p>如果该注解的方法出现异常，则会反馈标准的异常结果【ResultMsg.java】给前端或者服务调用方</p>
 * 若异常为BusException则不会记录日志<br>
 * 更多信息请查看ErrAspect.java
 * @create 2017-11-19 20:31:00
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CatchErr {
    String value() default "";

    boolean write2response() default false;
}