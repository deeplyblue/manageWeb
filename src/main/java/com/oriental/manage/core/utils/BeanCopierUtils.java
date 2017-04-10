package com.oriental.manage.core.utils;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

/**
 * 
 * Title: bestpay业务管理系统 Description:Bean 处理类 继承自 cglib Company: 东方金融
 * 
 * @author XiaBin add 2013-07-09
 * @version 1.0
 */
public class BeanCopierUtils {
	
	public static void copyProperties(Object fromObj, Object toObj) {
		BeanCopier copier = BeanCopier.create(fromObj.getClass(),
				toObj.getClass(), true);
		ConverterUtils converterUtils = new ConverterUtils();
		copier.copy(fromObj, toObj, converterUtils);
	}
	
	
	public static void copyProperties(Object fromObj, Object toObj,Converter c){
		BeanCopier copier = BeanCopier.create(fromObj.getClass(),
				toObj.getClass(), true);
		copier.copy(fromObj, toObj, c);
	}
}

