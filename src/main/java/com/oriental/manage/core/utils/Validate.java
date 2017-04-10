package com.oriental.manage.core.utils;

import com.oriental.manage.core.exception.BusiException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


/**
 * 请求参数验证工具类
 * 验证失败，抛产品层异常
 * Created by lupf on 2016/4/13.
 */
@Slf4j
public class Validate<T> {
    private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();


    /**
     * 请求参数非空、格式验证，请求对象
     *
     * @param t 请求校验参数
     */
    public static <T> void validateObject(T t) throws BusiException{
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (violations.size() == 0) return;
        for (ConstraintViolation<T> violation : violations){
            throw new BusiException(violation.getMessage());
        }
    }

    /**
     * 请求参数非空、格式验证，请求对象
     *
     * @param t 请求校验参数
     * @param type 校验类型
     */
    public static <T> void validateObject(T t,Class type) throws BusiException{
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(t,type);
        if (violations.size() == 0) return;
        for (ConstraintViolation<T> violation : violations){
            throw new BusiException(violation.getMessage());
        }
    }


}


