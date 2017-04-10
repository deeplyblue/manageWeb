package com.oriental.manage.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: Yang xp
 * Date: 2016/5/23
 * Time: 17:11
 * Desc：公司类型
 */
@Getter
@AllArgsConstructor
public enum CompanyType {

    MERCHANT("03","商户"),
    BUSINESS("05","业务机构"),
    PAY("02","支付机构"),
    ;

    private String code;
    private String desc;
}
