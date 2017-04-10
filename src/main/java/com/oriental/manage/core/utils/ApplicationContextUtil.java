package com.oriental.manage.core.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * Created by lupf on 2016/5/16.
 */
public class ApplicationContextUtil {

    public static ApplicationContext getApplicationContext() {
        return ContextLoader.getCurrentWebApplicationContext();
    }

    public static <T> T getBean(String beanName) {
        return (T) getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(Class className) {
        return (T) getApplicationContext().getBean(className);
    }
}
