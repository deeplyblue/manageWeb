package com.oriental.manage.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by lupf on 2016/5/6.
 */
@Getter
@AllArgsConstructor
public enum CacheKey {
    USER_TYPE("T_USER_INFO", "USER_TYPE", "用户类型", "t_data_dict"),
    USER_STATUS("T_USER_INFO", "USER_STATUS", "用户状态", "t_data_dict"),
    DPET_NAME("COMMON_VALUE", "DPET_NAME", "部门", "t_data_dict"),
    ROLE_TYPE("COMMON_VALUE", "ROLE_TYPE", "用户类型", "t_data_dict"),
    ORG_CATEGORY("COMMON_VALUE ", "ORG_TYPE", "机构类型", "t_data_dict"), //机构种类
    ORG_TYPR("T_ORGANIZE_INFO", "ORG_TYPE", "机构类型 ", "t_data_dict"),    //机构类型
    ORG_STATUS("T_ORGANIZE_INFO", "ORG_STATUS ", "当前状态  ", "t_data_dict"), // 	当前状态
    CTT_TYPE("T_CONTACT_INFO", "CTT_TYPE", "联系人类型 ", "t_data_dict"),
    CTT_STATUS("T_CONTACT_INFO", "CTT_STATUS", "联系人状态  ", "t_data_dict"),
    BANK_TYPE("COMMON_VALUE ", "BANK_TYPE ", "银行分类  ", "t_data_dict"),
    CONN_CHANNEL("COMMON_VALUE", "CONN_CHANNEL", "接入渠道 ", "t_data_dict"),
    TRANS_CODE_ALL("COMMON_VALUE", "TRANS_CODE_ALL", "交易代码 ", "t_data_dict"),
    MCHNT_TRANS_CODE("COMMON_VALUE", "MCHNT_TRANS_CODE", "业务交易代码 ", "t_data_dict"),
    PAY_TRANS_CODE("COMMON_VALUE", "PAY_TRANS_CODE", "支付交易代码 ", "t_data_dict"),
    REFUND_FLAG("T_TRANS_RIGHT", "REFUND_FLAG", "是否可退款 ", "t_data_dict"),
    USE_FLAG("COMMON_VALUE", "USE_FLAG", "模板应用系统", "t_data_dict"),
    BUSINESS_FUNDING_SOURCE("BUSI_FUNDING_SOURCE", "BUSI_TYPE", "业务资金源", "t_data_dict"),
    MCHNT_STATUS("T_MCHNT_INFO", "MCHNT_STATUS ", "当前状态  ", "t_data_dict"), // 	当前状态
    CONSUMPTION_PATTERNS("COMMON_VALUE", "CONSUMPTION_PATTERNS", "消费方式", "t_data_dict"),
    COMPANY_TYPE("COMMON_VALUE", "COMPANY_TYPE", "代码类型_商户或机构", "t_data_dict"),
    MCHNT_BUSI_TYPE("COMMON_VALUE", "MCHNT_BUSI_TYPE", "业务种类（大类）", "t_data_dict"),
    BANK_INFO_TYPE("T_BANK_INFO", "BANK_TYPE", "银行类型", "t_data_dict"),
    RESP_CODE("T_RESP_CODE", "ORG_TYPE", "机构类型", "t_data_dict"),
    ORG_CHANNEL_TYPE("COMMON_VALUE", "ORG_CHANNEL_TYPE", "通道类型", "t_data_dict"),

    CLR_METHOD("COMMON_VALUE", "CLR_METHOD", "结算方式", "t_data_dict"),
    CLR_RANGE("T_CLEAR_CFG", "CLR_RANGE", "结算范围 ", "t_data_dict"),
    CLR_CYCLE("T_CLEARING_CFG", "CLR_CYCLE", "结算周期 ", "t_data_dict"),
    AUDIT_STATUS("COMMON_VALUE", "AUDIT_STATUS", "审核状态 ", "t_data_dict"),
    ENABLE_FLAG("COMMON_VALUE", "ENABLE_FLAG", "启用标识 ", "t_data_dict"),
    ENABLE_LIMITFLAG("COMMON_VALUE", "ENABLE_LIMITFLAG", "启用标识 ", "t_data_dict"),
    IP_LIMITTYPE("COMMON_VALUE", "IP_LIMITTYPE", "IP类型 ", "t_data_dict"),
    IP_TYPE("T_IPLIMIT_CFG", "IP_TYPE", "IP类型 ", "t_data_dict"),
    RATE_ROUND_TYPE("T_FEE_CFG_TEMPALTE", "RATE_ROUND_TYPE", "结算精确类型 ", "t_data_dict"),
    RE_FEE_FLAG("T_CLEARING_CFG", "RE_FEE_FLAG", "是否返回手续费 ", "t_data_dict"),
    BANK_CARD_TYPE("T_MCHNT_BANK", "BANK_CARD_TYPE", "卡类型 ", "t_data_dict"),
    BANK_INFO("T_BANK_INFO", "BANK_CODE", "银行名称", "T_BANK_INFO"),
    TRANS_CODE("COMMON_VALUE", "TRANS_CODE", "支付机构交易代码", "t_data_dict"),
    SPLIT_FLAG("T_BUSI_TRANS_DETAIL", "SPLIT_FLAG", "分账标识", "t_data_dict"),
    REVERSE_FLAG("COMMON_VALUE", "REVERSE_FLAG", "正反交易标识", "t_data_dict"),
    REPRT_TYPE("COMMON_VALUE", "REPRT_TYPE", "报表类型 ", "t_data_dict"),
    FEE_CLEAR_TYPE("COMMON_VALUE", "FEE_CLEAR_TYPE", "手续费收取方式", "t_data_dict"),
    QUOTA_FLAG("COMMON_VALUE", "QUOTA_FLAG", "限额额度标示 ", "t_data_dict"),
    CFG_FLAG("BUSI_CHANNEL", "CFG_FLAG", "业务路由公私标识 ", "t_data_dict"),
    CARD_TYPE("T_CLIENT_BUSI_BANK", "CARD_TYPE", "卡类型 ", "t_data_dict"),
    ORDER_STATUS("COMMON_VALUE", "TRANS_STATUS", "订单状态 ", "t_data_dict"),
    REFUND_TYPE("COMMON_VALUE", "ERR_TYPE", "退款类型 ", "t_data_dict"),
    REFUND_STATUS("COMMON_VALUE", "REFUND_STATUS", "退款状态 ", "t_data_dict"),
    NOTIFY_STATUS("COMMON_VALUE", "NOTIFY_STATUS", "通知状态 ", "t_data_dict"),
    ORDER_TYPE("COMMON_VALUE", "ORDER_TYPE", "总单类型 ", "t_data_dict"),
    DELETE_STATUS("COMMON_VALUE", "DELETE_STATUS", "删除状态 ", "t_data_dict"),
    COMMON_REFUND_FLAG("COMMON_VALUE", "REFUND_FLAG", "退款标识 ", "t_data_dict"),
    CHK_DEAL_FLAG("T_MCHNT_INFO", "CHK_DEAL_FLAG", " 差异处理标识", "t_data_dict"),
    CHK_METHOD("T_MCHNT_INFO", "CHK_METHOD", " 对账方式 ", "t_data_dict"),
    CA_APPLY_STATE("T_MCHNT_INFO", "CA_APPLY_STATE", " 对账方式 ", "t_data_dict"),
    MCHNT_TYPE("MONITOR_INFO", "MCHNT_TYPE", "商户监控类型 ", "t_data_dict"),
    BUSINESS_BELONG("COMMON_VALUE", "BUSINESS_BELONG", "业务归属", "t_data_dict"),
    PAY_BANK_CODE("COMMON_VALUE", "PAY_BANK_CODE", "商业银行名称", "t_data_dict"),
    T_BANK_CODE("T_BANK_CODE", "BANK_CODE", "银行代码", "t_data_dict"),
    T_USER_DOWNLOAD("T_USER_DOWNLOAD", "STATUS", "下载文件状态", "t_data_dict"),
    ENCTYPE("T_KEY_INFO", "ENCTYPE", "信息验证类型", "t_data_dict"),
    SEETLE_FLAG("SEETLE_FLAG", "SEETLE_FLAG", "信息验证类型", "t_data_dict"),

    //mcc码
    MCC_CODE1("MERCHANT_MCCCODE", "MCC_CODE_LEVEL1", "mcc码第一级", "t_data_dict_more"),
    MCC_CODE2("MERCHANT_MCCCODE", "MCC_CODE_LEVEL2", "mcc码第一级", "t_data_dict_more"),
    MCC_CODE3("MERCHANT_MCCCODE", "MCC_CODE_LEVEL3", "mcc码第一级", "t_data_dict_more"),


    //统一资金
    CLEAR_FLAG("COMMON_VALUE", "CLR_FLAG_STATUS", "经办状态 ", "t_data_dict"),
    CLR_FLAG("COMMON_VALUE", "CLR_FLAG", "结算状态 ", "t_data_dict"),
    REPORT_TYPE("COMMON_VALUE", "REPRT_TYPE", "报表类型", "t_data_dict"),
    CLR_TYPE("COMMON_VALUE", "CLR_TYPE", "结算类型", "t_data_dict"),
    CLR_ADJUST_FLAG("COMMON_VALUE", "CLR_ADJUST_FLAG", "结算调整状态", "t_data_dict"),
    PAY_REQ_FLAG("COMMON_VALUE", "PAY_REQ_FLAG", "付款申请状态", "t_data_dict"),
    CLEAR_POINT("T_CLR_POINT_CFG", "ITEM_DESC", "结算点配置", "T_CLR_POINT_CFG"),
    CLEAR_VALUE_POINT("T_CLR_POINT_CFG", "ITEM_DESC", "结算点配置", "T_CLR_POINT_CFG"),
    CLEAR_VALUE_POINT_WEEK("T_CLR_POINT_CFG", "ITEM_DESC", "结算点配置", "T_CLR_POINT_CFG"),
    CASH_TRANS_FLAG("COMMON_VALUE", "CASH_TRANS_FLAG", "现金交易状态", "t_data_dict"),
    CLR_RULES_1("COMMON_VALUE", "CLR_RULES_1", "特殊结算规则1", "t_data_dict"),
    CLR_RULES_2("COMMON_VALUE", "CLR_RULES_2", "特殊结算规则2", "t_data_dict"),

    APPLY_STATUS_FOR_RESERVE("COMMON_VALUE", "APPLY_STATUS_FOR_RESERVE", "数据下发申请状态", "t_data_dict"),
    MESSAGE_TYPE_FOR_RESERVE("COMMON_VALUE", "MESSAGE_TYPE_FOR_RESERVE", "备付金申请报文类型", "t_data_dict"),

    CENTER_STATUS_FOR_RESERVE("COMMON_VALUE", "CENTER_STATUS_FOR_RESERVE", "是否人行下发", "t_data_dict"),
    MAIN_CONN_FOR_RESERVE("COMMON_VALUE", "MAIN_CONN_FOR_RESERVE", "备付金支付机构联系人主要联系人", "t_data_dict"),
    OFF_STATUS_FOR_RESERVE("COMMON_VALUE", "OFF_STATUS_FOR_RESERVE", "备付金支付机构联系人离职标识", "t_data_dict"),

    COMPLAINT_TYPE_FOR_RESERVE("COMMON_VALUE","COMPLAINT_TYPE_FOR_RESERVE","投诉申请类型","t_data_dict"),
    MESSAGE_APPLY_TYPE_FOR_RESERVE("COMMON_VALUE","MESSAGE_APPLY_TYPE_FOR_RESERVE","备付金信息管理类申请报文类型","t_data_dict"),
    HANDLE_STATUS_FOR_RESERVE("COMMON_VALUE","HANDLE_STATUS_FOR_RESERVE","投诉申请处理状态","t_data_dict"),
    DAY_END_REPORT_STATUS("COMMON_VALUE","DAY_END_REPORT_STATUS","日终异常申请回执状态","t_data_dict"),
    BUSINESS_STATUS_FOR_RESERVE("COMMON_VALUE","BUSINESS_STATUS_FOR_RESERVE","操作类型","t_data_dict"),
    BUSINESS_TYPE_FOR_RESERVE("COMMON_VALUE","BUSINESS_TYPE_FOR_RESERVE","商户类型","t_data_dict"),
    BUSINESS_AGENT_SETTLEMENT_RESERVE("COMMON_VALUE","BUSINESS_AGENT_SETTLEMENT_RESERVE","代理结算关系","t_data_dict"),

    NON_SETTLE_FOR_RESERVE("COMMON_VALUE","NON_SETTLE_FOR_RESERVE","非结算交易类型","t_data_dict"),
    //备付金类型
    BUSINESS_UP_FOR_RESERVE("COMMON_VALUE","BUSINESS_UP_FOR_RESERVE","业务数据上传","t_data_dict"),
    BASE_DATA_FOR_RESERVE("COMMON_VALUE","BASE_DATA_FOR_RESERVE","基础数据管理","t_data_dict"),
    BASE_INFO_FOR_RESERVE("COMMON_VALUE","BASE_INFO_FOR_RESERVE","基础信息管理","t_data_dict"),
    REPORT_MONTH_FOR_RESERVE("COMMON_VALUE","REPORT_MONTH_FOR_RESERVE","月报信息管理","t_data_dict"),
    REPORT_QUARTER_FOR_RESERVE("COMMON_VALUE","REPORT_QUARTER_FOR_RESERVE","季报信息管理","t_data_dict"),
    REPORT_YEAR_FOR_RESERVE("COMMON_VALUE","REPORT_YEAR_FOR_RESERVE","年报信息管理","t_data_dict"),
    SYSTEM_NOTICE_FOR_RESERVE("COMMON_VALUE","SYSTEM_NOTICE_FOR_RESERVE","系统通知及预警管理","t_data_dict"),
    SYSTEM_CONTROL_FOR_RESERVE("COMMON_VALUE","SYSTEM_CONTROL_FOR_RESERVE","系统控制管理","t_data_dict"),
    TYPE_FOR_RESERVE("COMMON_VALUE","TYPE_FOR_RESERVE","备付金类型大类","t_data_dict"),

    SERVICE_TYPE_FOR_RESERVE("COMMON_VALUE", "SERVICE_TYPE_FOR_RESERVE", "业务类型", "t_data_dict"),
    SERVICE_RANGE_FOR_RESERVE("COMMON_VALUE", "SERVICE_RANGE_FOR_RESERVE", "业务范围", "t_data_dict"),
    //opcif
    OPCIF_BANK_CARD_TYPE("COMMON_VALUE", "BANK_CARD_TYPE", "卡类型 ", "t_data_dict"),
    OPCIF_AGREEMENT_TYPE("COMMON_VALUE", "AGREEMENT_TYPE", "协议类型 ", "t_data_dict"),
    OPCIF_CERTIFICATE_TYPE("COMMON_VALUE", "CERTIFICATE_TYPE", "证件类型 ", "t_data_dict"),
    OPCIF_OPERATOR_ROLE("COMMON_VALUE", "OPERATOR_ROLE", "用户类型 ", "t_data_dict"),
    OPCIF_AUTH_STATUS("COMMON_VALUE", "AUTH_STATUS", "签约状态 ", "t_data_dict"),
    OPCIF_APPLY_CHANNEL("COMMON_VALUE", "APPLY_CHANNEL", "申请渠道 ", "t_data_dict"),
    OPCIF_AGREEMENT_STATUS("COMMON_VALUE", "AGREEMENT_STATUS", "协议状态 ", "t_data_dict"),
    REALNAME_LV("OPCIF", "REALNAME_LV", "实名等级 ", "t_data_dict"),
    CUSTOMER_CRADE("OPCIF", "CUSTOMER_CRADE", "客户等级 ", "t_data_dict"),

    //风险监管
    RISKFRONT_STATUS("RISKFRONT", "STATUS",  "消息处理状态","t_data_dict"),
    RISKFRONT_DEAL_STATUS("RISKFRONT",  "DEAL_STATUS","业务处理状态", "t_data_dict"),
    RISKFRONT_TX_CODE("RISKFRONT",  "TX_CODE","消息类型码", "t_data_dict"),
    RISKFRONT_TYPE("RISKFRONT",  "TYPE","业务类型", "t_data_dict"),
    RISKFRONT_DATA_TYPE("RISKFRONT",  "DATA_TYPE","参数类型", "t_data_dict"),
    RISKFRONT_SUBJECT_TYPE("RISKFRONT",  "SUBJECT_TYPE","账号类别", "t_data_dict"),
    RISKFRONT_ID_TYPE("RISKFRONT",  "ID_TYPE","证件类型", "t_data_dict"),
    RISKFRONT_CASETYPE("RISKFRONT",  "CASETYPE","案件类型", "t_data_dict"),

    TGB_CUSTOMER_STATUS("COMMON_VALUE", "TGB_CUSTOMER_STATUS", "客户状态", "t_data_dict"),
    ACCOUNT_TYPE("ACCOUNT", "ACCOUNT_TYPE", "账户类型", "t_data_dict"),

    ROLE_INFO("T_ROLE_INFO", "ROLE_NAME", "角色名称", "T_ROLE_INFO"),
    ROLE_INFO_02("T_ROLE_INFO", "ROLE_NAME", "角色名称", "T_ROLE_INFO"),

    ORG_BANK_CODE_DESC("t_org_bank", "ORG_BANK_CODE_DESC", "机构银行描述", "t_org_bank"),
    TYPE_OPERATION("COMMON_VALUE", "TYPE_OPERATION", "操作类型 ", "t_data_dict"),
    OPERATION_RESULT("COMMON_VALUE", "OPERATION_RESULT", "操作结果 ", "t_data_dict"),

    COMANY_CODE("T_ORGANIZE_INFO", "ORG_NAME", "机构全称", "T_ORGAN文件类型IZE_INFO"),

    MERCHANT_CODE("T_MCHNT_INFO", "MCHNT_NAME", "商户全称", "T_MCHNT_INFO"),
    MERCHANT_ABBR_NAME("T_MCHNT_INFO", "MCHNT_NAME", "商户简称", "T_MCHNT_INFO"),
    AREA_CODE("T_GOV_AREA", "AREA_NAME", "省市全称", "T_GOV_AREA"),
    FILE_TEMPLATE_NO("T_CHK_FILE_DATA_CFG", "FILE_TEMPLATE_NO", "对账文件模板名称", "T_CHK_FILE_DATA_CFG"),
    ALL_COMPANY_CODE("COMMON_VALUE", "ALL_COMPANY_CODE", "商户或支付机构名称", "T_MCHNT_INFO|T_ORGANIZE_INFO"),

    DOCUMENT_TYPE("COMMON_VALUE", "DOCUMENT_TYPE", "文档类型 ", "t_data_dict"),
    UPLOAD_STATUS("COMMON_VALUE", "UPLOAD_STATUS", "上传状态 ", "t_data_dict"),
    /*系统安全相关*/
    SYSTEM_DOMAINS("SYSTEM_SECURITY", "DOMAINS", "系统域名白名单", "t_data_dict"),

    TRANS_CODE_02("COMMON_VALUE", "TRANS_CODE", "交易代码 ", "t_data_dict_more"),
    COMPANY_IP("COMPANY_IP", "IP_ADDR", "公司ip", "t_data_dict"),
    MCHNT_IP("MCHNT_IP", "IP_ADDR", "商户ip", "t_data_dict"),

    MCC_CODE("MERCHANT_MCCCODE", "MCC_CODE", "商户类型码", "t_data_dict_more"),
    ALL_ROLE("com.oriental.manage.service.base.IRoleInfoService", "getAllRole", "", "t_role_info"),
    ALL_CITY("", "", "", ""),
    ALL_AREA("", "", "", ""),
    ALL_USER("", "", "", ""),

    BANK_DETAIL_TYPE("COMMON_VALUE", "BANK_DETAIL_TYPE", "备付金银行性质", "t_data_dict"),
    IS_CROSS_TRANS("COMMON_VALUE", "IS_CROSS_TRANS", "是否跨境人民币账户", "t_data_dict"),
    BANK_STATUS("COMMON_VALUE", "BANK_STATUS", "银行账户状态", "t_data_dict"),
    ACCT_TYPE("COMMON_VALUE", "ACCT_TYPE", "银行信息账户类型", "t_data_dict"),
    /*查看日志*/
    TASK_STATUS("COMMON_VALUE", "TASK_STATUS", "日志状态 ", "t_data_dict");
    

    ;


    //    字典项名
    private String itemName;
    //    列项名
    private String colName;
    //    描述
    private String desc;
    //    存储空间
    private String scope;

}
