package com.oriental.manage.pojo.transaction;

import com.oriental.manage.pojo.base.BaseModel;

import java.util.Date;

public class TBusiTransDetailKey extends BaseModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.BUSI_NO
     *
     * @mbggenerated
     */
    private String busiNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.SETTLE_DATE
     *
     * @mbggenerated
     */
    private Date settleDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.BUSI_NO
     *
     * @return the value of t_busi_trans_detail.BUSI_NO
     *
     * @mbggenerated
     */
    public String getBusiNo() {
        return busiNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.BUSI_NO
     *
     * @param busiNo the value for t_busi_trans_detail.BUSI_NO
     *
     * @mbggenerated
     */
    public void setBusiNo(String busiNo) {
        this.busiNo = busiNo == null ? null : busiNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.SETTLE_DATE
     *
     * @return the value of t_busi_trans_detail.SETTLE_DATE
     *
     * @mbggenerated
     */
    public Date getSettleDate() {
        return settleDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.SETTLE_DATE
     *
     * @param settleDate the value for t_busi_trans_detail.SETTLE_DATE
     *
     * @mbggenerated
     */
    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }
}