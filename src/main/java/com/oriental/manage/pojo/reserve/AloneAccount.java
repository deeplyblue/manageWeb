package com.oriental.manage.pojo.reserve;

import com.oriental.manage.pojo.base.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yutao on 2016/12/22.
 */
public class AloneAccount extends BaseModel implements Serializable{


    private static final long serialVersionUID = 8936939245965540797L;

    private String id;

    private String msgId;

    private String OrgNO;//支付机构编号

    private String BankCode;//银行行号

    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date SettleDate;//结算日期

    private String Type;//申请功能   01：结算单核对；02：明细核对

    private String status;//01: 申请中 02 :申请成功 03:申请失败


    private String operateType; //操作类型  00:新增   01：修改


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    private String updateBy;

    private String deleteFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getOrgNO() {
        return OrgNO;
    }

    public void setOrgNO(String orgNO) {
        OrgNO = orgNO;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public Date getSettleDate() {
        return SettleDate;
    }

    public void setSettleDate(Date settleDate) {
        SettleDate = settleDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
