package com.oriental.manage.core.system.log;

import java.lang.annotation.*;

/**
 * 
 * Title: bestpay业务管理系统 Description:编写自定义注解。实现对方法所实现的功能进行描述，以便在通知中获取描述信息
 * Company: 东方金融
 * 
 * @author
 * @version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLogger {
	//描叙信息
	String content() default "无描述信息";

	// C:增加 R：查询 U：修改 D：删除
	enum OperationType {
		C, R, U, D , LOGIN_IN,LOGIN_OUT
	};
	OperationType operationType() default OperationType.R;

	// D时必须传入原始对象(修改时要有用到，action中必须有getOldBean()方法，也就是先的查询出修改前的对象)
	String oldObject() default "OldBean";

	// 目标对象 必须传入值(增加，删除，查询时用到，页面传过来的对象，action中必须有getBean()方法)
	String targetObject() default "Bean";
	
	/**   下面是通知必须要的注解信息  **/
	//类型
	String companyType() default "";

	// OUT:通知外部系统，IN：通知本系统，ALL：同时通知
	enum NoticeSystem {
		OUT, IN, ALL
	};
	NoticeSystem noticeSystem() default NoticeSystem.ALL;

	// 对应的表，多个的话以“,”分割
	String tables() default "";
}
