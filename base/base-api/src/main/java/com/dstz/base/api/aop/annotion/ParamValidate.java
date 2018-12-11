package com.dstz.base.api.aop.annotion;

import java.lang.annotation.*;


/**
 * @description <p>校验入参中对象，对象需要对需要校验字段进行注解， 注解位于</p>
 * @create 2017-11-19 20:31:00
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamValidate {
    String value() default "";
}