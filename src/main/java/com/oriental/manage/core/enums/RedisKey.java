package com.oriental.manage.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lupf on 2016/6/28.
 */
@Getter
@AllArgsConstructor
public enum RedisKey {

    PAY_BANK_CODE("PAY_BANK_CODE", "支付机构银行"),
    ORGANIZE_NAME("ORGANIZE_NAME", "机构名称"),
    MERCHANT_NAME("MERCHANT_NAME", "商户全称"),
    MERCHANT_ABBR_NAME("MERCHANT_ABBR_NAME", "商户简称"),
    BANK_NAME("BANK_NAME", "银行名称"),

    DATA_DICT("data_dict_key", "数据字典"),


    SMS_VERIFY_CODE("yf_sms", "短信验证码"),
    IMG_VERIFY_CODE("yf_img", "图片验证码");

    private String hashKey;

    private String desc;
}
