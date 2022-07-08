package com.dstz.base.core.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.core.util.StringUtil;

public class ValidateUtil {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final static Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

    /**
     * @param o
     */
    public static void validate(Object o) {
        String msg = ValidateUtil.getValidateMsg(o);
        if (StringUtil.isNotEmpty(msg)) {
            logger.info("参数拦截信息{}",msg);
            throw new BusinessMessage(msg, BaseStatusCode.PARAM_ILLEGAL);
        }
    }

    public static Set<ConstraintViolation<Object>> getValidation(Object o) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(o);
        // 没命中
        return violations;
    }

    public static String getValidateMsg(Object o) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(o);
        // 没命中
        if (violations.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<Object> violation : violations) {
            if (sb.length() != 0) {
                sb.append("; ");
            }
            sb.append(violation.getMessage()).append("[").append(violation.getPropertyPath()).append("]");
        }
        return sb.toString();
    }


}
