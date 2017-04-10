package com.oriental.manage.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lupf on 2016/4/22.
 */
@Getter
@AllArgsConstructor
public enum UserStatus {

    INITIAL("01","初始"),
    NORMAL("02","正常"),
    LOCKED("03","锁定"),
    CANCELLATION("04","注销"),
    STOPPED("05","停用");

    private String code;
    private String desc;
}
