package com.oriental.manage.pojo.report;

import com.oriental.settlementfront.service.facade.common.model.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * <ul>
 * <li>总账收款核对报表数据</li>
 * <li>User:蒯越 Date:2016/12/1 Time:14:54</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class GLCollectionReportData extends BaseDTO {

    /**
     * 支付机构
     */
    private String payOrgCode;

    /**
     * 银行清算日期
     */
    private Date bankSettleDate;

    /**
     * 交易成功笔数
     */
    private Integer succCount;

    /**
     * 交易成功金额
     */
    private Long succAmt;

    /**
     * 支付笔数
     */
    private Integer payCount;

    /**
     * 支付金额
     */
    private Long payAmt;

    /**
     * 退款笔数
     */
    private Integer refundCount;

    /**
     * 退款金额
     */
    private Long refundAmt;

    /**
     * 对账文件笔数
     */
    private Integer fileCount;

    /**
     * 对账文件金额
     */
    private Long fileAmt;

    /**
     * 应收笔数
     */
    private Integer transCountD;

    /**
     * 应收金额
     */
    private Long transAmtD;

    /**
     * 应付笔数
     */
    private Integer transCountC;

    /**
     * 应付金额
     */
    private Long transAmtC;

    /**
     * 实收日期
     */
    private Date paidInDate;

    /**
     * 实收金额
     */
    private Long paidInAmt;

    /**
     * 实付日期
     */
    private Date paidOutDate;

    /**
     * 实付金额
     */
    private Long paidOutAmt;

    /**
     * 交易所在日期
     */
    private String transDates;

    /**
     * 是否标红
     */
    private boolean isRed;
}
