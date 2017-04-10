package com.oriental.manage.core.exception;

import lombok.Data;

/**
 * Created by lupf on 2016/7/15.
 */
@Data
public class FirstLoginException extends RuntimeException {

    private String msg = "首次登录，请先重置登录密码!";

}
