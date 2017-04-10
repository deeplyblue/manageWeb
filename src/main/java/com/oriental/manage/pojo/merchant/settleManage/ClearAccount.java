package com.oriental.manage.pojo.merchant.settleManage;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Author: Yang xp
 * Date: 2016/5/31
 * Time: 15:57
 * Desc：商户账户配置
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClearAccount extends BaseModel {

    private String id;

    /**
     * 分支机构ID
     */
    private String merchantBrCode;

    /**
     * 商户ID
     */
    private String merchantCode;

    /**
     * 开户行行号
     */
    private String bankCode;

    /**
     * 开户行行名
     */
    private String bankName;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 创建人员
     */
    private String creator;

    /**
     * 修改人员
     */
    private String modifier;

    /**
     * 记录生成日期
     */
    private Date createTime;

    /**
     * 记录最后更新日期
     */
    private Date lastUpdTime;

    /**
     * 类型
     */
    private String companyType;

    /**
     * 银行联行号
     */
    private String cnapsCode;

    /**
     * 地区码
     */
    private String areaCode;

    /**
     * 付款摘要
     */
    private String paySummary;

    private String reportType;

}
