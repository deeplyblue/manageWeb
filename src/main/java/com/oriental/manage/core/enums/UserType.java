package com.oriental.manage.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by wangjun on 2016/12/1.
 */
@Getter
@AllArgsConstructor
public enum UserType {

    MERCHANT_USER("MERCHANT_USER","商户侧用户"),
    SYS_USER("SYS_USER","系统侧用户");

    private String code;
    private String desc;
}
