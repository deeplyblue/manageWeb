package com.oriental.manage.core.utils;

/**
 * map对象，用于向页面提供json数据，不用把对象中所有的属性都设置到json中去
 * @author 黄军
 *
 * 2013-5-8 上午9:14:37
 */
public class MapObject {
	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
