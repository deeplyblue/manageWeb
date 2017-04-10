package com.oriental.manage.pojo.report;

import com.oriental.settlementfront.service.facade.common.model.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * <ul>
 * <li>总账付款核对报表数据</li>
 * <li>User:蒯越 Date:2016/12/1 Time:14:54</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class GLPaymentReportPaidData extends BaseDTO {

    /**
     * 支付机构
     */
    private String payOrgCode;

    /**
     * 实付日期
     */
    private Date paidOutDate;

    /**
     * 实付金额
     */
    private Long paidOutAmt;

    /**
     * 实收日期
     */
    private Date paidInDate;

    /**
     * 实收金额
     */
    private Long paidInAmt;

    /**
     * 交易所在日期
     */
    private String transDates;
}
