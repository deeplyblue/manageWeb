package com.oriental.manage.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lupf on 2016/4/25.
 */
@Getter
@AllArgsConstructor
public enum SessionKey {

    ROLE("role_resource", "角色信息"),
    MENU("menu_resource", "菜单信息"),
    OPERATION("operation_resource", "动作权限"),
    LAST_LOGIN_TIME("lastLoginTime", "最后登陆时间"),
    LAST_LOGIN_ADDRESS("lastLoginIpAddress", "最后登陆时间"),

    VERIFY_CODE("verify_code", "验证码"),
    SMS_VERIFY_CODE("sms_verify_code", "验证码"),

    USER_ID("user_id", "用户ID"),

    USER_TEL("user_tel","用户电话"),
    MCHNT_CODE("mchnt_code", "商户号"),

    IP_LIMIT_CFG("ip_limit_cfg", "ip访问控制"),


    SERVER_RANDOM("serverRandom","登录密码控件使用的服务器随机数");

    private String code;
    private String desc;
}
