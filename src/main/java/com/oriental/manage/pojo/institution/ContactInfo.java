package com.oriental.manage.pojo.institution;

import com.oriental.manage.pojo.base.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 2016/5/5.
 * 支付机构联系人表
 */
public class ContactInfo extends BaseModel {


    //联系人编号
    private String id;
    //dict:tab联系人类型:00:主联系人01:技术联系人02:业务联系人03:财务联系人04:客服联系人07:申请人
    private String cttType;
    //联系人角色:A角B角C角
    private String cttRole;
    //姓名
    private String cttName;
    //邮箱
    private String cttEmail;
    //座机号
    private String cttPhone;
    //手机号
    private String cttMobile;
    //传真
    private String cttFax;
    //dict:tab联系人状态:0:可用1:不可用
    private String cttStatus;
    //dict:comm代码类型,商户或机构:01:受理机构02:支付机构03:商户04:分润机构
    private String companyType;
    //商户或机构代码
    private String companyCode;
    //记录生成日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //记录最后更新日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdTime;
    //是否是主联系人
    private String isMainContact;
    //是否接收公告通知邮件
    private String acceptNoticeEmailFlag;



    public String getCttType() {
        return cttType;
    }

    public String getId() {
        return id;
    }

    public String getIsMainContact() {
        return isMainContact;
    }

    public String getAcceptNoticeEmailFlag() {
        return acceptNoticeEmailFlag;
    }

    public String getCttName() {
        return cttName;
    }

    public String getCttRole() {
        return cttRole;
    }

    public String getCttEmail() {
        return cttEmail;
    }

    public String getCttPhone() {
        return cttPhone;
    }

    public String getCttMobile() {
        return cttMobile;
    }

    public String getCttFax() {
        return cttFax;
    }

    public String getCttStatus() {
        return cttStatus;
    }

    public String getCompanyType() {
        return companyType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastUpdTime() {
        return lastUpdTime;
    }

    private List<String> list;

    public void setId(String id) {
        this.id = id;
    }

    public void setAcceptNoticeEmailFlag(String acceptNoticeEmailFlag) {
        this.acceptNoticeEmailFlag = acceptNoticeEmailFlag;
    }

    public void setIsMainContact(String isMainContact) {
        this.isMainContact = isMainContact;
    }

    public void setLastUpdTime(Date lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCttType(String cttType) {
        this.cttType = cttType == null ? null : cttType.trim();
    }

    public void setCttRole(String cttRole) {
        this.cttRole = cttRole == null ? null : cttRole.trim();
    }

    public void setCttName(String cttName) {
        this.cttName = cttName == null ? null : cttName.trim();
    }

    public void setCttEmail(String cttEmail) {
        this.cttEmail = cttEmail == null ? null : cttEmail.trim();
    }

    public void setCttPhone(String cttPhone) {
        this.cttPhone = cttPhone == null ? null : cttPhone.trim();
    }

    public void setCttMobile(String cttMobile) {
        this.cttMobile = cttMobile == null ? null : cttMobile.trim();
    }

    public void setCttFax(String cttFax) {
        this.cttFax = cttFax == null ? null : cttFax.trim();
    }

    public void setCttStatus(String cttStatus) {
        this.cttStatus = cttStatus == null ? null : cttStatus.trim();
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }


}
