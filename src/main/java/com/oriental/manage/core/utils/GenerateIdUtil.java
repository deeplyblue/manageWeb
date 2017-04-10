package com.oriental.manage.core.utils;

/**
 * Desc:
 * Date: 2016/6/2
 * User: ZhouBin
 * See :
 */
public class GenerateIdUtil {

    public static String generateId(){
        return System.currentTimeMillis()+RandomMath.getNum(5);
    }
}
