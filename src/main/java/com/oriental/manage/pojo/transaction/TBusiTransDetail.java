package com.oriental.manage.pojo.transaction;

import lombok.Data;

import java.util.Date;

@Data
public class TBusiTransDetail extends TBusiTransDetailKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.MCHNT_CODE
     *
     * @mbggenerated
     */
    private String mchntCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.ORDER_NO
     *
     * @mbggenerated
     */
    private String orderNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.ORDER_DATE
     *
     * @mbggenerated
     */
    private Date orderDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.CHANNEL
     *
     * @mbggenerated
     */
    private String channel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.OUR_TRANS_NO
     *
     * @mbggenerated
     */
    private String ourTransNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.TRANS_CODE
     *
     * @mbggenerated
     */
    private String transCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.TRANS_AMT
     *
     * @mbggenerated
     */
    private Long transAmt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.TRANS_STATUS
     *
     * @mbggenerated
     */
    private String transStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.BUSI_REQ_NO
     *
     * @mbggenerated
     */
    private String busiReqNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.BUSI_REQ_DATE
     *
     * @mbggenerated
     */
    private Date busiReqDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.BUSI_RESP_NO
     *
     * @mbggenerated
     */
    private String busiRespNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.BUSI_RESP_DATE
     *
     * @mbggenerated
     */
    private Date busiRespDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.BUSI_RESP_CODE
     *
     * @mbggenerated
     */
    private String busiRespCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.BUSI_RESP_DESC
     *
     * @mbggenerated
     */
    private String busiRespDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.SETTLE_STATUS
     *
     * @mbggenerated
     */
    private String settleStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.SETTLE_BATCH_NO
     *
     * @mbggenerated
     */
    private String settleBatchNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.SETTLE_FLAG
     *
     * @mbggenerated
     */
    private String settleFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.CHK_STATUS
     *
     * @mbggenerated
     */
    private String chkStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.REVERSE_FLAG
     *
     * @mbggenerated
     */
    private String reverseFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.REFUND_FLAG
     *
     * @mbggenerated
     */
    private String refundFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.OLD_BUSI_NO
     *
     * @mbggenerated
     */
    private String oldBusiNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.ACCEPT_SRV_IP
     *
     * @mbggenerated
     */
    private String acceptSrvIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.MAIN_BUSI_FIELD
     *
     * @mbggenerated
     */
    private String mainBusiField;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.SYNC_STATUS
     *
     * @mbggenerated
     */
    private String syncStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.DELETE_FLAG
     *
     * @mbggenerated
     */
    private String deleteFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_busi_trans_detail.LAST_UPD_TIME
     *
     * @mbggenerated
     */
    private Date lastUpdTime;




    private Date beginOrderDate;
    private Date endOrderDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.MCHNT_CODE
     *
     * @return the value of t_busi_trans_detail.MCHNT_CODE
     *
     * @mbggenerated
     */
    public String getMchntCode() {
        return mchntCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.MCHNT_CODE
     *
     * @param mchntCode the value for t_busi_trans_detail.MCHNT_CODE
     *
     * @mbggenerated
     */
    public void setMchntCode(String mchntCode) {
        this.mchntCode = mchntCode == null ? null : mchntCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.ORDER_NO
     *
     * @return the value of t_busi_trans_detail.ORDER_NO
     *
     * @mbggenerated
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.ORDER_NO
     *
     * @param orderNo the value for t_busi_trans_detail.ORDER_NO
     *
     * @mbggenerated
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.ORDER_DATE
     *
     * @return the value of t_busi_trans_detail.ORDER_DATE
     *
     * @mbggenerated
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.ORDER_DATE
     *
     * @param orderDate the value for t_busi_trans_detail.ORDER_DATE
     *
     * @mbggenerated
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.CHANNEL
     *
     * @return the value of t_busi_trans_detail.CHANNEL
     *
     * @mbggenerated
     */
    public String getChannel() {
        return channel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.CHANNEL
     *
     * @param channel the value for t_busi_trans_detail.CHANNEL
     *
     * @mbggenerated
     */
    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.OUR_TRANS_NO
     *
     * @return the value of t_busi_trans_detail.OUR_TRANS_NO
     *
     * @mbggenerated
     */
    public String getOurTransNo() {
        return ourTransNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.OUR_TRANS_NO
     *
     * @param ourTransNo the value for t_busi_trans_detail.OUR_TRANS_NO
     *
     * @mbggenerated
     */
    public void setOurTransNo(String ourTransNo) {
        this.ourTransNo = ourTransNo == null ? null : ourTransNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.TRANS_CODE
     *
     * @return the value of t_busi_trans_detail.TRANS_CODE
     *
     * @mbggenerated
     */
    public String getTransCode() {
        return transCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.TRANS_CODE
     *
     * @param transCode the value for t_busi_trans_detail.TRANS_CODE
     *
     * @mbggenerated
     */
    public void setTransCode(String transCode) {
        this.transCode = transCode == null ? null : transCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.TRANS_AMT
     *
     * @return the value of t_busi_trans_detail.TRANS_AMT
     *
     * @mbggenerated
     */
    public Long getTransAmt() {
        return transAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.TRANS_AMT
     *
     * @param transAmt the value for t_busi_trans_detail.TRANS_AMT
     *
     * @mbggenerated
     */
    public void setTransAmt(Long transAmt) {
        this.transAmt = transAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.TRANS_STATUS
     *
     * @return the value of t_busi_trans_detail.TRANS_STATUS
     *
     * @mbggenerated
     */
    public String getTransStatus() {
        return transStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.TRANS_STATUS
     *
     * @param transStatus the value for t_busi_trans_detail.TRANS_STATUS
     *
     * @mbggenerated
     */
    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.BUSI_REQ_NO
     *
     * @return the value of t_busi_trans_detail.BUSI_REQ_NO
     *
     * @mbggenerated
     */
    public String getBusiReqNo() {
        return busiReqNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.BUSI_REQ_NO
     *
     * @param busiReqNo the value for t_busi_trans_detail.BUSI_REQ_NO
     *
     * @mbggenerated
     */
    public void setBusiReqNo(String busiReqNo) {
        this.busiReqNo = busiReqNo == null ? null : busiReqNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.BUSI_REQ_DATE
     *
     * @return the value of t_busi_trans_detail.BUSI_REQ_DATE
     *
     * @mbggenerated
     */
    public Date getBusiReqDate() {
        return busiReqDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.BUSI_REQ_DATE
     *
     * @param busiReqDate the value for t_busi_trans_detail.BUSI_REQ_DATE
     *
     * @mbggenerated
     */
    public void setBusiReqDate(Date busiReqDate) {
        this.busiReqDate = busiReqDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.BUSI_RESP_NO
     *
     * @return the value of t_busi_trans_detail.BUSI_RESP_NO
     *
     * @mbggenerated
     */
    public String getBusiRespNo() {
        return busiRespNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.BUSI_RESP_NO
     *
     * @param busiRespNo the value for t_busi_trans_detail.BUSI_RESP_NO
     *
     * @mbggenerated
     */
    public void setBusiRespNo(String busiRespNo) {
        this.busiRespNo = busiRespNo == null ? null : busiRespNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.BUSI_RESP_DATE
     *
     * @return the value of t_busi_trans_detail.BUSI_RESP_DATE
     *
     * @mbggenerated
     */
    public Date getBusiRespDate() {
        return busiRespDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.BUSI_RESP_DATE
     *
     * @param busiRespDate the value for t_busi_trans_detail.BUSI_RESP_DATE
     *
     * @mbggenerated
     */
    public void setBusiRespDate(Date busiRespDate) {
        this.busiRespDate = busiRespDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.BUSI_RESP_CODE
     *
     * @return the value of t_busi_trans_detail.BUSI_RESP_CODE
     *
     * @mbggenerated
     */
    public String getBusiRespCode() {
        return busiRespCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.BUSI_RESP_CODE
     *
     * @param busiRespCode the value for t_busi_trans_detail.BUSI_RESP_CODE
     *
     * @mbggenerated
     */
    public void setBusiRespCode(String busiRespCode) {
        this.busiRespCode = busiRespCode == null ? null : busiRespCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.BUSI_RESP_DESC
     *
     * @return the value of t_busi_trans_detail.BUSI_RESP_DESC
     *
     * @mbggenerated
     */
    public String getBusiRespDesc() {
        return busiRespDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.BUSI_RESP_DESC
     *
     * @param busiRespDesc the value for t_busi_trans_detail.BUSI_RESP_DESC
     *
     * @mbggenerated
     */
    public void setBusiRespDesc(String busiRespDesc) {
        this.busiRespDesc = busiRespDesc == null ? null : busiRespDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.SETTLE_STATUS
     *
     * @return the value of t_busi_trans_detail.SETTLE_STATUS
     *
     * @mbggenerated
     */
    public String getSettleStatus() {
        return settleStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.SETTLE_STATUS
     *
     * @param settleStatus the value for t_busi_trans_detail.SETTLE_STATUS
     *
     * @mbggenerated
     */
    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.SETTLE_BATCH_NO
     *
     * @return the value of t_busi_trans_detail.SETTLE_BATCH_NO
     *
     * @mbggenerated
     */
    public String getSettleBatchNo() {
        return settleBatchNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.SETTLE_BATCH_NO
     *
     * @param settleBatchNo the value for t_busi_trans_detail.SETTLE_BATCH_NO
     *
     * @mbggenerated
     */
    public void setSettleBatchNo(String settleBatchNo) {
        this.settleBatchNo = settleBatchNo == null ? null : settleBatchNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.SETTLE_FLAG
     *
     * @return the value of t_busi_trans_detail.SETTLE_FLAG
     *
     * @mbggenerated
     */
    public String getSettleFlag() {
        return settleFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.SETTLE_FLAG
     *
     * @param settleFlag the value for t_busi_trans_detail.SETTLE_FLAG
     *
     * @mbggenerated
     */
    public void setSettleFlag(String settleFlag) {
        this.settleFlag = settleFlag == null ? null : settleFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.CHK_STATUS
     *
     * @return the value of t_busi_trans_detail.CHK_STATUS
     *
     * @mbggenerated
     */
    public String getChkStatus() {
        return chkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.CHK_STATUS
     *
     * @param chkStatus the value for t_busi_trans_detail.CHK_STATUS
     *
     * @mbggenerated
     */
    public void setChkStatus(String chkStatus) {
        this.chkStatus = chkStatus == null ? null : chkStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.REVERSE_FLAG
     *
     * @return the value of t_busi_trans_detail.REVERSE_FLAG
     *
     * @mbggenerated
     */
    public String getReverseFlag() {
        return reverseFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.REVERSE_FLAG
     *
     * @param reverseFlag the value for t_busi_trans_detail.REVERSE_FLAG
     *
     * @mbggenerated
     */
    public void setReverseFlag(String reverseFlag) {
        this.reverseFlag = reverseFlag == null ? null : reverseFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.REFUND_FLAG
     *
     * @return the value of t_busi_trans_detail.REFUND_FLAG
     *
     * @mbggenerated
     */
    public String getRefundFlag() {
        return refundFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.REFUND_FLAG
     *
     * @param refundFlag the value for t_busi_trans_detail.REFUND_FLAG
     *
     * @mbggenerated
     */
    public void setRefundFlag(String refundFlag) {
        this.refundFlag = refundFlag == null ? null : refundFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.OLD_BUSI_NO
     *
     * @return the value of t_busi_trans_detail.OLD_BUSI_NO
     *
     * @mbggenerated
     */
    public String getOldBusiNo() {
        return oldBusiNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.OLD_BUSI_NO
     *
     * @param oldBusiNo the value for t_busi_trans_detail.OLD_BUSI_NO
     *
     * @mbggenerated
     */
    public void setOldBusiNo(String oldBusiNo) {
        this.oldBusiNo = oldBusiNo == null ? null : oldBusiNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.ACCEPT_SRV_IP
     *
     * @return the value of t_busi_trans_detail.ACCEPT_SRV_IP
     *
     * @mbggenerated
     */
    public String getAcceptSrvIp() {
        return acceptSrvIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.ACCEPT_SRV_IP
     *
     * @param acceptSrvIp the value for t_busi_trans_detail.ACCEPT_SRV_IP
     *
     * @mbggenerated
     */
    public void setAcceptSrvIp(String acceptSrvIp) {
        this.acceptSrvIp = acceptSrvIp == null ? null : acceptSrvIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.MAIN_BUSI_FIELD
     *
     * @return the value of t_busi_trans_detail.MAIN_BUSI_FIELD
     *
     * @mbggenerated
     */
    public String getMainBusiField() {
        return mainBusiField;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.MAIN_BUSI_FIELD
     *
     * @param mainBusiField the value for t_busi_trans_detail.MAIN_BUSI_FIELD
     *
     * @mbggenerated
     */
    public void setMainBusiField(String mainBusiField) {
        this.mainBusiField = mainBusiField == null ? null : mainBusiField.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.SYNC_STATUS
     *
     * @return the value of t_busi_trans_detail.SYNC_STATUS
     *
     * @mbggenerated
     */
    public String getSyncStatus() {
        return syncStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.SYNC_STATUS
     *
     * @param syncStatus the value for t_busi_trans_detail.SYNC_STATUS
     *
     * @mbggenerated
     */
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus == null ? null : syncStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.DELETE_FLAG
     *
     * @return the value of t_busi_trans_detail.DELETE_FLAG
     *
     * @mbggenerated
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.DELETE_FLAG
     *
     * @param deleteFlag the value for t_busi_trans_detail.DELETE_FLAG
     *
     * @mbggenerated
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.CREATE_TIME
     *
     * @return the value of t_busi_trans_detail.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.CREATE_TIME
     *
     * @param createTime the value for t_busi_trans_detail.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_busi_trans_detail.LAST_UPD_TIME
     *
     * @return the value of t_busi_trans_detail.LAST_UPD_TIME
     *
     * @mbggenerated
     */
    public Date getLastUpdTime() {
        return lastUpdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_busi_trans_detail.LAST_UPD_TIME
     *
     * @param lastUpdTime the value for t_busi_trans_detail.LAST_UPD_TIME
     *
     * @mbggenerated
     */
    public void setLastUpdTime(Date lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }
}