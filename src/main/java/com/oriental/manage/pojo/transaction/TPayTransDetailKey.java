package com.oriental.manage.pojo.transaction;

import java.util.Date;

public class TPayTransDetailKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_trans_detail.PAY_TRANS_NO
     *
     * @mbggenerated
     */
    private String payTransNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_trans_detail.SETTLE_DATE
     *
     * @mbggenerated
     */
    private Date settleDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pay_trans_detail.PAY_TRANS_NO
     *
     * @return the value of t_pay_trans_detail.PAY_TRANS_NO
     *
     * @mbggenerated
     */
    public String getPayTransNo() {
        return payTransNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pay_trans_detail.PAY_TRANS_NO
     *
     * @param payTransNo the value for t_pay_trans_detail.PAY_TRANS_NO
     *
     * @mbggenerated
     */
    public void setPayTransNo(String payTransNo) {
        this.payTransNo = payTransNo == null ? null : payTransNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pay_trans_detail.SETTLE_DATE
     *
     * @return the value of t_pay_trans_detail.SETTLE_DATE
     *
     * @mbggenerated
     */
    public Date getSettleDate() {
        return settleDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pay_trans_detail.SETTLE_DATE
     *
     * @param settleDate the value for t_pay_trans_detail.SETTLE_DATE
     *
     * @mbggenerated
     */
    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }
}