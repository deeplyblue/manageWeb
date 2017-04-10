package com.oriental.manage.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lupf on 2016/7/15.
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeManage {

    LOGIN_REPEAT("YF600001", "重复登录异常"),
    SYSTEM_EXCEPTION("YF900001","系统错误"),
    LOGIN_PWD_EXPIRE("YF600002", "密码将在一周内过期,请及时更新"),
    FIRST_LOGIN("YF600003", "首次登陆，请先重置密码!"),


    //    security
    GREEN_IP("YF400001", "ip校验不合法,请联系管理员");

    private String code;
    private String desc;
//    private String group;
}
