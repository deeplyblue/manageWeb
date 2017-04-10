package com.oriental.manage.pojo.report;

import com.oriental.settlementfront.service.facade.common.model.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * <ul>
 * <li>支付业务核对报表数据</li>
 * <li>User:蒯越 Date:2016/12/5 Time:15:39</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class PayBusiCheckReportData extends BaseDTO {

    /**
     * 支付机构
     */
    private String payOrgCode;

    /**
     * 银行清算日期
     */
    private Date bankSettleDate;

    /**
     * 支付笔数
     */
    private Integer payCount;

    /**
     * 支付金额
     */
    private Long payAmt;

    /**
     * 业务笔数
     */
    private Integer busiCount;

    /**
     * 业务金额
     */
    private Long busiAmt;

    /**
     * 已退笔数
     */
    private Integer refundCount;

    /**
     * 已退金额
     */
    private Long refundAmt;

    /**
     * 是否标红
     */
    private boolean isRed;
}
