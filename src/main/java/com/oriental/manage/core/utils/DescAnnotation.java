package com.oriental.manage.core.utils;

import java.lang.annotation.*;


/**
 * 字段注解
 * @author 黄军
 *
 * 2013-4-25 下午1:33:29
 */
@Target(ElementType.FIELD)  
@Retention (RetentionPolicy.RUNTIME)   
@Documented
public @interface DescAnnotation {
	//描叙
	String description();
}
