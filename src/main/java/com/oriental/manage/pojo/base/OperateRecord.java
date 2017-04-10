package com.oriental.manage.pojo.base;

import java.util.Date;

public class OperateRecord extends  BaseModel{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.ID
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.USER_ID
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.RSRC_CODE
     *
     * @mbggenerated
     */
    private String rsrcCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.OP_TYPE
     *
     * @mbggenerated
     */
    private String opType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.OP_TIME
     *
     * @mbggenerated
     */
    private Date opTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.OP_RESULT
     *
     * @mbggenerated
     */
    private String opResult;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.OP_DESC
     *
     * @mbggenerated
     */
    private String opDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.PREV_CONTENT
     *
     * @mbggenerated
     */
    private String prevContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.CONTENT
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.LAST_UPD_TIME
     *
     * @mbggenerated
     */
    private Date lastUpdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_operate_record.CLIENT_IP
     *
     * @mbggenerated
     */
    private String clientIp;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.ID
     *
     * @return the value of t_operate_record.ID
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.ID
     *
     * @param id the value for t_operate_record.ID
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.USER_ID
     *
     * @return the value of t_operate_record.USER_ID
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.USER_ID
     *
     * @param userId the value for t_operate_record.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.RSRC_CODE
     *
     * @return the value of t_operate_record.RSRC_CODE
     *
     * @mbggenerated
     */
    public String getRsrcCode() {
        return rsrcCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.RSRC_CODE
     *
     * @param rsrcCode the value for t_operate_record.RSRC_CODE
     *
     * @mbggenerated
     */
    public void setRsrcCode(String rsrcCode) {
        this.rsrcCode = rsrcCode == null ? null : rsrcCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.OP_TYPE
     *
     * @return the value of t_operate_record.OP_TYPE
     *
     * @mbggenerated
     */
    public String getOpType() {
        return opType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.OP_TYPE
     *
     * @param opType the value for t_operate_record.OP_TYPE
     *
     * @mbggenerated
     */
    public void setOpType(String opType) {
        this.opType = opType == null ? null : opType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.OP_TIME
     *
     * @return the value of t_operate_record.OP_TIME
     *
     * @mbggenerated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.OP_TIME
     *
     * @param opTime the value for t_operate_record.OP_TIME
     *
     * @mbggenerated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.OP_RESULT
     *
     * @return the value of t_operate_record.OP_RESULT
     *
     * @mbggenerated
     */
    public String getOpResult() {
        return opResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.OP_RESULT
     *
     * @param opResult the value for t_operate_record.OP_RESULT
     *
     * @mbggenerated
     */
    public void setOpResult(String opResult) {
        this.opResult = opResult == null ? null : opResult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.OP_DESC
     *
     * @return the value of t_operate_record.OP_DESC
     *
     * @mbggenerated
     */
    public String getOpDesc() {
        return opDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.OP_DESC
     *
     * @param opDesc the value for t_operate_record.OP_DESC
     *
     * @mbggenerated
     */
    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc == null ? null : opDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.PREV_CONTENT
     *
     * @return the value of t_operate_record.PREV_CONTENT
     *
     * @mbggenerated
     */
    public String getPrevContent() {
        return prevContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.PREV_CONTENT
     *
     * @param prevContent the value for t_operate_record.PREV_CONTENT
     *
     * @mbggenerated
     */
    public void setPrevContent(String prevContent) {
        this.prevContent = prevContent == null ? null : prevContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.CONTENT
     *
     * @return the value of t_operate_record.CONTENT
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.CONTENT
     *
     * @param content the value for t_operate_record.CONTENT
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.CREATE_TIME
     *
     * @return the value of t_operate_record.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.CREATE_TIME
     *
     * @param createTime the value for t_operate_record.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.LAST_UPD_TIME
     *
     * @return the value of t_operate_record.LAST_UPD_TIME
     *
     * @mbggenerated
     */
    public Date getLastUpdTime() {
        return lastUpdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.LAST_UPD_TIME
     *
     * @param lastUpdTime the value for t_operate_record.LAST_UPD_TIME
     *
     * @mbggenerated
     */
    public void setLastUpdTime(Date lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_operate_record.CLIENT_IP
     *
     * @return the value of t_operate_record.CLIENT_IP
     *
     * @mbggenerated
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_operate_record.CLIENT_IP
     *
     * @param clientIp the value for t_operate_record.CLIENT_IP
     *
     * @mbggenerated
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }
}