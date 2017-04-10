package com.oriental.manage.pojo.report;

import com.oriental.settlementfront.service.facade.common.model.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <ul>
 * <li>总账付款核对报表数据</li>
 * <li>User:蒯越 Date:2016/12/1 Time:14:54</li>
 * </ul>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class GLPaymentReportData extends BaseDTO {

    /**
     * 商户代码
     */
    private String mchntCode;

    /**
     * 商户清算日期
     */
    private Date busiSettleDate;

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
     * 应付笔数
     */
    private Integer transCountC = 0;

    /**
     * 应付金额
     */
    private Long transAmtC = 0L;

    /**
     * 应收笔数
     */
    private Integer transCountD = 0;

    /**
     * 应收金额
     */
    private Long transAmtD = 0L;

    /**
     * 总账付款报表实际收付数据
     */
    private List<GLPaymentReportPaidData> glPaymentReportPaidDataList = new ArrayList<>();

    /**
     * 是否标红
     */
    private boolean isRed;
}
