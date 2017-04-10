package com.oriental.manage.core.fileUtils;

import lombok.AllArgsConstructor;

/**
 * Created by lupf on 2016/6/16.
 */
@AllArgsConstructor
public enum ExcelContentExt {

    CURRENCY("金额类型"),
    MCHNT("商户"),
    MCHNT_BUSI_TYPE("业务种类"),
    IMITIATOR_CODE("接入方"),
    CONN_CHANNEL("接入渠道"),
    PAY_TRANS_CODE("支付类型"),
    ORDER_STATUS("交易状态"),
    PAY_BANK_CODE("银行"),
    BANK_INFO("付款银行"),
    BUSI_TYPE("业务种类（大类）"),
    MCHNT_TRANS_CODE("业务交易代码"),
    CLR_TYPE("结算状态"),
    CLR_FLAG("结算标识"),
    ORGANIZE("机构"),
    CASH_TRANS_FLAG("现金交易状态"),
    FEE_CLEAR_TYPE("手续费收取方式");


    private String extDesc;
}
