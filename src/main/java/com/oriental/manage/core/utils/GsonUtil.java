package com.oriental.manage.core.utils;

import com.google.gson.Gson;

/**
 * Created by lupf on 2016/11/8.
 */
public class GsonUtil {

    private static final Gson instance = new Gson();

    public static Gson getInstance(){
        return instance;
    }
}
