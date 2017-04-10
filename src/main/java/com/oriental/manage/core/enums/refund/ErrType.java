package com.oriental.manage.core.enums.refund;

import com.oriental.paycenter.commons.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Description:
 * Company: 东方金融
 * @author taoweichao create date: 2014/12/30
 * @version 1.0
 */

@Getter
@AllArgsConstructor
public enum ErrType {

    TYPE_ORG_ERR("01","机构差异类型"),
    TYPE_MERCHANT_ERR("03","业务差异类型"),
    TYPE_INNER_ERR("02","内部差异类型"),

    ORDER_STATUS_NOT_MATCH("","主单状态不符"),
    ERR_TYPE_BANK_MORE("10","银行多"),
    PLAT_MORE("11","平台多"),
    MONEY_NOT_MATCH("15","金额不符"),
    DATE_NOT_MATCH("14","日期不符"),
    BUSINESS_MORE("12","业务多"),
    ;

    private String code;

    private String desc;

}
