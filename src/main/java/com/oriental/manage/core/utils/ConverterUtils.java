package com.oriental.manage.core.utils;

import net.sf.cglib.core.Converter;

/**
 * 
 * @author XiaBin add 2013-07-09
 * 
 */
@SuppressWarnings("rawtypes")
public class ConverterUtils implements Converter {
	@SuppressWarnings("unchecked")
	public Object convert(Object value, Class clz, Object arg2) {
		if (value != null && value.getClass().isAssignableFrom(String.class) && clz.isAssignableFrom(Long.class)) {
			return Long.parseLong(value.toString());
		} else {
			return value;
		}
	}
}
