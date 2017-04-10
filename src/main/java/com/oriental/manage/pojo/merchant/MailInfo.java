package com.oriental.manage.pojo.merchant;

import com.oriental.manage.pojo.base.BaseModel;

import java.util.Date;

/**
 * Created by wangjun on 2016/5/16.
 */
public class MailInfo extends BaseModel {

    //编号
    private String id;

    //姓名
    private String username;

    //邮箱
    private String email;

    //电话
    private String telephone;

    //移动电话
    private String mobile;

    //邮寄地址
    private String postAddr;

    //发票抬头
    private String invoiceHead;

    //邮编
    private String zipcode;

    //单位名称
    private String userCompany;

    //记录生成日期
    private Date createTime;

    //记录最后更新日期
    private Date lastUpdTime;

    //dict:comm代码类型,商户或机构:
    private String companyType;

    //商户或机构代码
    private String companyCode;

    //省编号
    private String areaCode;

    //城市编号
    private String cityCode;

    //纳税人识别号
    private String taxpayerNo;

    //税务登记证地址
    private String certificateAddr;

    //财务联系电话
    private String financialPhone;

    //一般纳税人开户行
    private String taxpayerBankCode;

    //一般纳税人银行账户
    private String taxpayerBankAccount;

    //审核人
    private String auditor;

    //审核日期
    private String auditDate;

    //dict:comm审核状态:01:未处理02:审核成功03:审核失败
    private String auditStatus;

    //客户名称(增值税纳税人名称)
    private String taxpayerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPostAddr() {
        return postAddr;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getUserCompany() {
        return userCompany;
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

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getTaxpayerNo() {
        return taxpayerNo;
    }

    public void setTaxpayerNo(String taxpayerNo) {
        this.taxpayerNo = taxpayerNo;
    }

    public String getCertificateAddr() {
        return certificateAddr;
    }

    public void setCertificateAddr(String certificateAddr) {
        this.certificateAddr = certificateAddr;
    }

    public String getFinancialPhone() {
        return financialPhone;
    }

    public void setFinancialPhone(String financialPhone) {
        this.financialPhone = financialPhone;
    }

    public String getTaxpayerBankCode() {
        return taxpayerBankCode;
    }

    public void setTaxpayerBankCode(String taxpayerBankCode) {
        this.taxpayerBankCode = taxpayerBankCode;
    }

    public String getTaxpayerBankAccount() {
        return taxpayerBankAccount;
    }

    public void setTaxpayerBankAccount(String taxpayerBankAccount) {
        this.taxpayerBankAccount = taxpayerBankAccount;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getTaxpayerName() {
        return taxpayerName;
    }

    public void setTaxpayerName(String taxpayerName) {
        this.taxpayerName = taxpayerName;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public void setPostAddr(String postAddr) {
        this.postAddr = postAddr == null ? null : postAddr.trim();
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead == null ? null : invoiceHead.trim();
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany == null ? null : userCompany.trim();
    }
}
