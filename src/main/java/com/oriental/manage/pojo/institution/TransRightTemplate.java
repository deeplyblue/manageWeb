package com.oriental.manage.pojo.institution;

import com.oriental.manage.pojo.base.BaseModel;

import java.util.Date;

/**
 * Created by wangjun on 2016/5/10.
 * 交易权限模板
 */
public class TransRightTemplate extends BaseModel {

    //唯一id
    private String id;

    //模板No
    private String templateNo;

    //模板名称
    private String templateName;

    //dict:comm接入渠道:
    private String connChannel;

    //交易代码6位分两段,2位大类4位编号
    private String transCode;

    //dict:comm代码类型,商户或机构:
    private String companyType;

    //商户或机构代码")
    private String companyCode;

    //dict:tab是否可退款:0:可退1:不可退
    private String refundFlag;

    //记录生成日期
    private Date createTime;

    //记录最后更新日期
    private Date lastUpdTime;

    //应用系统:0:表示后台管理系统1:表示商户自助注册
    private String useFlag;

    //受理机构
    private String acqOrgCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getConnChannel() {
        return connChannel;
    }

    public String getTransCode() {
        return transCode;
    }

    public String getCompanyType() {
        return companyType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getRefundFlag() {
        return refundFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdTime() {
        return lastUpdTime;
    }

    public void setLastUpdTime(Date lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public String getAcqOrgCode() {
        return acqOrgCode;
    }

    public void setAcqOrgCode(String acqOrgCode) {
        this.acqOrgCode = acqOrgCode;
    }



    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo == null ? null : templateNo.trim();
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public void setConnChannel(String connChannel) {
        this.connChannel = connChannel == null ? null : connChannel.trim();
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode == null ? null : transCode.trim();
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public void setRefundFlag(String refundFlag) {
        this.refundFlag = refundFlag == null ? null : refundFlag.trim();
    }
}


