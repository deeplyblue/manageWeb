package com.oriental.manage.core.utils;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Object工具类
 * @author 黄军
 *
 * 2013-3-6 下午4:40:32
 */
public class ObjectUtils {
	private static final Logger LOG= LoggerFactory.getLogger(ObjectUtils.class);
	/**
	 * 得到类名
	 * @param fullClassName 全路径名，如com.ideal.util.test
	 * @return
	 */
	public static String getObjectName(String fullClassName){
		String result = "";
		if(fullClassName.contains(".")){
			int firstIndex = fullClassName.lastIndexOf('.');
			int lastIndex = fullClassName.length();
			if(fullClassName.contains("$")){
				lastIndex = fullClassName.indexOf('.');
			}
			result = fullClassName.substring(firstIndex + 1, lastIndex);
		}else{
			result = fullClassName;
		}
		return result;
	}
	/**
	 * 方法名首字母大写
	 */
	public static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
	/**
	 * 把对象的属性装载入MAP
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String convertMapToString(Map map){
		Set set = map.entrySet();
		List<MapObject> infoList = new ArrayList<MapObject>();
		String value = null;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if(entry.getValue() != null){
				MapObject obj = new MapObject();
				obj.setKey(String.valueOf(entry.getKey()));
				value = String.valueOf(entry.getValue());
				//防止长度过长导致数据存储的时候会报错
				if(value != null && !"".equals(value) && value.length() > 200){
					value = value.substring(0,200);
				}
				obj.setValue(value);
				infoList.add(obj);
			}
		}
		return JSONArray.fromObject(infoList).toString();
	}
	/**
	 * 比较2个Map中的值(用于修改操作时)
	 */
	@SuppressWarnings("rawtypes")
	public static String[] comparisonMap(Map oldMap, Map targetMap) {
		String[] result = new String[2];
		Set targetSet = targetMap.entrySet();
		Object targetKey = null;
		String targetValue = null;
		String oldValue = null;
		List<MapObject> oldInfoList = new ArrayList<MapObject>();
		List<MapObject> targetList = new ArrayList<MapObject>();
		for (Iterator iterator = targetSet.iterator(); iterator.hasNext();) {
			Map.Entry targetObject = (Map.Entry) iterator.next();
			targetKey = targetObject.getKey();
			targetValue = String.valueOf(targetObject.getValue());
			oldValue = String.valueOf(oldMap.get(targetKey));
			//只有不同的时候才记录日志
			if ((!targetValue.equals(oldValue) && !"null".equals(oldValue))
                    || !"null".equals(oldValue) || ("null".equals(oldValue) && !"".equals(targetValue))) {
				MapObject oldObj = new MapObject();
				oldObj.setKey(String.valueOf(targetKey));
				
				if(!"".equals(oldValue) && oldValue.length() > 200){
					oldValue = oldValue.substring(0, 200);
				}
				oldObj.setValue(oldValue);
				oldInfoList.add(oldObj);
				MapObject targetObj = new MapObject();
				targetObj.setKey(String.valueOf(targetKey));
				if(targetValue != null && !"".equals(targetValue) && targetValue.length() > 200){
					targetValue = targetValue.substring(0, 200);
				}
				targetObj.setValue(targetValue);
				targetList.add(targetObj);
			}
		}
		result[0] = JSONArray.fromObject(oldInfoList).toString();
		result[1] = JSONArray.fromObject(targetList).toString();
		return result;
	}

	/**
	 * 打印对象中的属性
	 * @param obj
	 */
	@SuppressWarnings("rawtypes")
	public static void printObjectValue(Object obj) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Object object = AopTargetUtils.getTarget(obj);
			if (object != null) {
				Class<?> cls = object.getClass();
				Field[] fields = cls.getDeclaredFields();
				Method method = null;
				Object val = null;
				String typeName = null;
				for (Field field : fields) {
					typeName = field.getGenericType().toString();
					if (typeName.equals("class java.lang.String")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (String) method.invoke(object);
					} else if (typeName.equals("class java.lang.Integer")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Integer) method.invoke(object);
					} else if (typeName.equals("class java.lang.Double")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Double) method.invoke(object);
					} else if (typeName.equals("class java.util.Date")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Date) method.invoke(object);
					} else if (typeName.equals("class java.lang.Short")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Short) method.invoke(object);
					} else if (typeName.equals("class java.lang.Long")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Long) method.invoke(object);
					} else if (typeName.equals("boolean")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Boolean) method.invoke(object);
					}
					if (val != null) {
						map.put(field.getName(), val);
					}
				}
			} else {
				LOG.debug("该对象为空");
			}
			Set set = map.entrySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				LOG.debug(entry.getKey() + ":" + entry.getValue());
			}
		} catch (SecurityException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (IllegalArgumentException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (NoSuchMethodException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (IllegalAccessException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (InvocationTargetException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (Exception e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		}
	}
	/**
	 * 得到对象中的属性并转化成MAP
	 */
	public static Map<String,Object> getObjectValue(Object object) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if (object != null) {
				Class<?> cls = object.getClass();
				Field[] fields = cls.getDeclaredFields();
				Method method;
				Object val = null;
				String typeName;
				for (Field field : fields) {
                    int modifiers = Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL;
                    // 只获取非常量字段
                    if(modifiers != field.getModifiers()) {
                        typeName = field.getGenericType().toString();
                        if (typeName.equals("class java.lang.String")) {
                            method = (Method) object.getClass().getMethod(
                                    "get" + getMethodName(field.getName()));
                            val = (String) method.invoke(object);
                        } else if (typeName.equals("class java.lang.Integer")) {
                            method = (Method) object.getClass().getMethod(
                                    "get" + getMethodName(field.getName()));
                            val = (Integer) method.invoke(object);
                        } else if (typeName.equals("class java.lang.Double")) {
                            method = (Method) object.getClass().getMethod(
                                    "get" + getMethodName(field.getName()));
                            val = (Double) method.invoke(object);
                        } else if (typeName.equals("class java.util.Date")) {
                            method = (Method) object.getClass().getMethod(
                                    "get" + getMethodName(field.getName()));
                            val = (Date) method.invoke(object);
                        } else if (typeName.equals("class java.lang.Short")) {
                            method = (Method) object.getClass().getMethod(
                                    "get" + getMethodName(field.getName()));
                            val = (Short) method.invoke(object);
                        } else if (typeName.equals("class java.lang.Long")) {
                            method = (Method) object.getClass().getMethod(
                                    "get" + getMethodName(field.getName()));
                            val = (Long) method.invoke(object);
                        } else if (typeName.equals("boolean")) {
                            method = (Method) object.getClass().getMethod(
                                    "get" + getMethodName(field.getName()));
                            val = (Boolean) method.invoke(object);
                        }
                        if (val != null) {
                            DescAnnotation descAnnotation = field.getAnnotation(DescAnnotation.class);
                            if (descAnnotation != null) {
                                map.put(descAnnotation.description(), val);
                            } else {
                                map.put(field.getName(), val);
                            }
                        }
                    }
				}
			} else {
				LOG.info("该对象为空");
			}
		} catch (SecurityException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (IllegalArgumentException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (NoSuchMethodException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (IllegalAccessException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (InvocationTargetException e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		} catch (Exception e) {
			LOG.error(StringCommonUtils.getExceptionInfo(e),e);
		}
		return map;
	}
	
	 @SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {  
	        if (obj == null){
	            return true;  
            }
	        if (obj instanceof CharSequence){
	            return ((CharSequence) obj).length() == 0;  
            }
	        if (obj instanceof Collection){
	            return ((Collection) obj).isEmpty();  
            }
	        if (obj instanceof Map){
	            return ((Map) obj).isEmpty();  
            }
	        if (obj instanceof Object[]) {  
	            Object[] object = (Object[]) obj;  
	            if (object.length == 0) {  
	                return true;  
	            }  
	            boolean empty = true;  
	            for (int i = 0; i < object.length; i++) {  
	                if (!isNullOrEmpty(object[i])) {  
	                    empty = false;  
	                    break;  
	                }  
	            }  
	            return empty;  
	        }  
	        return false;  
	    }

    public static boolean isNotNullAndEmpty(Object obj){
        return !ObjectUtils.isNullOrEmpty(obj);
    }
}
