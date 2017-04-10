package com.oriental.manage.core.exception;

import lombok.Data;

/**
 * Created by lupf on 2016/7/15.
 */
@Data
public class LoginRepeatException extends RuntimeException {

    private String userName;
    private String msg = "用户名：{userName}已登录，不允许重复登录!";

    public LoginRepeatException(String userName) {
        this.userName = userName;
        this.msg = this.msg.replace("{userName}", userName);
    }
}
