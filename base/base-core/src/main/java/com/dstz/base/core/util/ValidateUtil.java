package com.dstz.base.core.util;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.Map.Entry;


/**
 * <pre>
 * LogicParam的校验器
 * </pre>
 *
 * @author aschs
 */
public class ValidateUtil {
    /**
     * 校验器工厂
     */
    private static ValidatorFactory factory;

    /**
     * 初始化
     */
    static {
        factory = Validation.buildDefaultValidatorFactory();
    }

    private ValidateUtil() {

    }

    /**
     * <pre>
     * 校验某个对象
     * 基于javax.validation工具包
     * </pre>
     *
     * @param obj
     */
    public static void validate(Object obj) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        // 没命中
        if (violations.isEmpty()) {
            return;
        }

        Map<Class<? extends Annotation>, List<ConstraintViolation<Object>>> resultMap = new HashMap<>();
        for (ConstraintViolation<Object> violation : violations) {
            List<ConstraintViolation<Object>> list = resultMap.get(violation.getConstraintDescriptor().getAnnotation().annotationType());
            if (list == null) {
                list = new ArrayList<>();
                resultMap.put(violation.getConstraintDescriptor().getAnnotation().annotationType(), list);
            }
            list.add(violation);
        }
        StringBuilder sb = new StringBuilder();
        for (Entry<Class<? extends Annotation>, List<ConstraintViolation<Object>>> entry : resultMap.entrySet()) {
            sb.append("[");
            for (ConstraintViolation<Object> violation : entry.getValue()) {
                if (!sb.toString().endsWith("[")) {
                    sb.append(",");
                }
                sb.append(violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath().toString());
            }
            sb.append("]" + entry.getValue().get(0).getMessage() + ";");
        }

        if (StringUtil.isNotEmpty(sb.toString())) {
            throw new BusinessException(sb.toString(), BaseStatusCode.PARAM_ILLEGAL);
        }
    }

}
