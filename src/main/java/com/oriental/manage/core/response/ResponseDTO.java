package com.oriental.manage.core.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by lupf on 2016/4/14.
 */
@Data
public class ResponseDTO<T> {

    private boolean isSuccess;

    private String code;

    private String msg;

    private List<String> messages;

    private T object;

    private Object sumObject;

    private Map<String, Object> infoMap;

    public void getIsSuccess(boolean isSuccess){
        this.isSuccess = isSuccess;
    }


}
