package com.oriental.manage.pojo.business;

import com.oriental.manage.pojo.base.BaseModel;
import com.oriental.manage.pojo.institution.ContactInfo;
import com.oriental.manage.pojo.merchant.MailInfo;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商户信息
 * Created by wangjun on 2016/5/25.
 */
@ToString
public class MchntInfo extends BaseModel {

   //商户代码 
    private String mchntCode;

   //商户简称 
    private String mchntAbbrName;

   //商户名称 
    private String mchntName;

   //商户类型码ID 
    private String mccCode;

   //商户类型:01:集团内部商户02:集团外部商户03:省级内部商户04:省级外部商户 
    private String mchntType;

   //dict:tab商户状态:A:初始B:可用C:关闭D:审核通过 
    private String mchntStatus;

   //商户地址 
    private String mchntAddr;

   //商户邮编 
    private String mchntZipcode;

   //商户传真 
    private String mchntFax;

   //商户归属地 
    private String mchntArea;

   //商户级别0顶级1一级。。。。。。 
    private String mchntLvl;

   //上级机构 
    private String parentOrgCode;

   //结算机构 
    private String clrOrgCode;

   //商户URL地址 
    private String mchntUrl;

   //商户电话接入号 
    private String mchntIvrNo;

   //商户介绍 
    private String mchntIntro;

   //优惠规则介绍 
    private String discountRuleIntro;

   //商户LOGO 
    private String mchntLogo;

   //撤销日期 
    private Date revokeDate;

   //dict:tab对账方式:01:自主对账02:非自主对账 
    private String chkMethod;

   //dict:comm删除标识:0:正常1:删除 
    private String deleteFlag;

   //审核通过日期 
    private Date auditDate;

   //dict:tab拓展方式:01:集团拓展02:省级拓展03:外部拓展 
    private String developMode;

   //dict:tab对账服务等级:01:金02:银03:铜 
    private String chkRank;

   //记录生成日期 
    private Date createTime;

   //记录最后更新日期 
    private Date lastUpdTime;

   //结算服务等级01金02银03铜 
    private String clrRank;

   //dict:comm代码类型,商户或机构: 
    private String companyType;

   //商户备注信息 
    private String mchntEtc;

   //审核人 
    private String auditor;

   //商户城市 
    private String cityCode;

   //商户告警级别:0:不告警1:中级告警商户2:高级告警商户 
    private String mchntWarnLvl;

   //商户上线日期 
    private String mchntOnlineDate;

   //是否新老商户:0:否1:是 
    private String oldMchntFlag;

   //模板No 
    private String templateNo;

   //银行联行号 
    private String cnapsCode;

   //差异处理标志:0:调账1:退款
    private String chkDealFlag;

   //商户分类的大类 
    private String mchntCatagory;

   //商户分类的小类 
    private String mchntKind;

   //商户分类的三级类 
    private String mchntClass;

    private ContactInfo contactInfo;

    private MailInfo mailInfo;

   //发展渠道 
    private String depChannelCode;

   //主联系人名字 
    private String mainContactName;

   //主联系人邮件 
    private String mainContactEmail;

   //主联系人电话 
    private String mainContactPhone;

   //主联系人手机 
    private String mainContactMobile;

   //自动分配的用户名 
    private String loginName;

   //是否自定义序列号:0:表示非自定义，由系统生成(控制商户授权号是否自定义)1:表示自定义 
    private String mchntIsdefined;

   //是否担保交易商户:0:否1:是 
    private String guaranteeFlag;

   //交易限额 
    private BigDecimal transAmtLimit;

   //银联快捷二级商户号 
    private String unionPayMchntCode;

   //开通人 
    private String openUser;

   //修改人 
    private String modifyUser;

   //后分账标识 
    private String afterSplitFlag;

    private List<String> list;

   //开通时间 
    private Date openDate;

   //dict:tab暂停付款标识:0:否1:是 
    private String suspendFlag;

   //dict:tab报表类型：01：正常，02：分账，03：积分，04：账单支付 
    private String reportType;

   //服务电话 
    private String serviceTelephone;

   //商户从网关发起纯业务交易（无需支付），结算时网关需要和该商户结算。 是否同步到纯业务的 0：不是  1：是 
    private String mchntPayFlag;

   //业务归属  
    private String bussinessBeLong;

   //自定义发送邮箱  
    private String allEmail;

   //商户来源: 01:系统新增商户或老平台导入商户  02:运营系统自助注册  03:门户网签 04：客户端商户自助注册  
    private String mchntSource;

   //平台标识: 1.管控平台 2.商户自服务平台 
    private String platformFlag;

   //商户监控分类一级核心商户、二级核心商户、三级核心商户、普通商户 
    private String mchntMonType;

   //CA证书申请状态：0：未申请 1：申请成功 2：申请失败 
    private String caApplyState;

   //CA证书申请邮箱 
    private String caApplyEmail;

    // 发送状态
    private String sendResult;

   //支付类型01短信支付;02短信确认支付;03短信再次确认支付 
    private String payType;

   //是否允许修改手机号： 01：否 02：是 
    private String alterMobileFalg;

   //是否显示子商户信息： 01：否 02：是 
    private String showChild;

    private String contractFlag;


    //担保状态 A:未开启 B:开启 C:开启担保失败
    private String guaranteeStatus;
    //担保过期范围(单位天)
    private BigDecimal guaranteeOverdueRange;

    //审核意见
    private String auditOpinion;

    //税务登记证纳税人名称
    private String taxpayerName;

    public BigDecimal getGuaranteeOverdueRange() {
        return guaranteeOverdueRange;
    }

    public void setGuaranteeOverdueRange(BigDecimal guaranteeOverdueRange) {
        this.guaranteeOverdueRange = guaranteeOverdueRange;
    }



    public String getMchntCode() {
        return mchntCode;
    }

    public String getMchntAbbrName() {
        return mchntAbbrName;
    }

    public String getMchntName() {
        return mchntName;
    }

    public void setMccCode(String mccCode) {
        this.mccCode = mccCode;
    }

    public String getMchntType() {
        return mchntType;
    }

    public String getMchntStatus() {
        return mchntStatus;
    }

    public String getMchntAddr() {
        return mchntAddr;
    }

    public String getMchntZipcode() {
        return mchntZipcode;
    }

    public String getMchntFax() {
        return mchntFax;
    }

    public String getMchntArea() {
        return mchntArea;
    }

    public String getMchntLvl() {
        return mchntLvl;
    }

    public String getParentOrgCode() {
        return parentOrgCode;
    }

    public String getClrOrgCode() {
        return clrOrgCode;
    }

    public String getMchntUrl() {
        return mchntUrl;
    }

    public String getMchntIvrNo() {
        return mchntIvrNo;
    }

    public String getMchntIntro() {
        return mchntIntro;
    }

    public String getDiscountRuleIntro() {
        return discountRuleIntro;
    }

    public String getMchntLogo() {
        return mchntLogo;
    }

    public Date getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(Date revokeDate) {
        this.revokeDate = revokeDate;
    }

    public String getChkMethod() {
        return chkMethod;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getDevelopMode() {
        return developMode;
    }

    public String getChkRank() {
        return chkRank;
    }

    public void setChkRank(String chkRank) {
        this.chkRank = chkRank;
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

    public String getClrRank() {
        return clrRank;
    }

    public void setClrRank(String clrRank) {
        this.clrRank = clrRank;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getMchntEtc() {
        return mchntEtc;
    }

    public void setMchntEtc(String mchntEtc) {
        this.mchntEtc = mchntEtc;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getMchntWarnLvl() {
        return mchntWarnLvl;
    }

    public void setMchntWarnLvl(String mchntWarnLvl) {
        this.mchntWarnLvl = mchntWarnLvl;
    }

    public String getMchntOnlineDate() {
        return mchntOnlineDate;
    }

    public void setMchntOnlineDate(String mchntOnlineDate) {
        this.mchntOnlineDate = mchntOnlineDate;
    }

    public String getOldMchntFlag() {
        return oldMchntFlag;
    }

    public void setOldMchntFlag(String oldMchntFlag) {
        this.oldMchntFlag = oldMchntFlag;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }

    public String getCnapsCode() {
        return cnapsCode;
    }

    public void setCnapsCode(String cnapsCode) {
        this.cnapsCode = cnapsCode;
    }

    public String getChkDealFlag() {
        return chkDealFlag;
    }

    public void setChkDealFlag(String chkDealFlag) {
        this.chkDealFlag = chkDealFlag;
    }

    public String getMchntCatagory() {
        return mchntCatagory;
    }

    public void setMchntCatagory(String mchntCatagory) {
        this.mchntCatagory = mchntCatagory;
    }

    public String getMchntKind() {
        return mchntKind;
    }

    public void setMchntKind(String mchntKind) {
        this.mchntKind = mchntKind;
    }

    public String getMchntClass() {
        return mchntClass;
    }

    public void setMchntClass(String mchntClass) {
        this.mchntClass = mchntClass;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public MailInfo getMailInfo() {
        return mailInfo;
    }

    public void setMailInfo(MailInfo mailInfo) {
        this.mailInfo = mailInfo;
    }

    public String getDepChannelCode() {
        return depChannelCode;
    }

    public void setDepChannelCode(String depChannelCode) {
        this.depChannelCode = depChannelCode;
    }

    public String getMainContactName() {
        return mainContactName;
    }

    public void setMainContactName(String mainContactName) {
        this.mainContactName = mainContactName;
    }

    public String getMainContactEmail() {
        return mainContactEmail;
    }

    public void setMainContactEmail(String mainContactEmail) {
        this.mainContactEmail = mainContactEmail;
    }

    public String getMainContactPhone() {
        return mainContactPhone;
    }

    public void setMainContactPhone(String mainContactPhone) {
        this.mainContactPhone = mainContactPhone;
    }

    public String getMainContactMobile() {
        return mainContactMobile;
    }

    public void setMainContactMobile(String mainContactMobile) {
        this.mainContactMobile = mainContactMobile;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMchntIsdefined() {
        return mchntIsdefined;
    }

    public void setMchntIsdefined(String mchntIsdefined) {
        this.mchntIsdefined = mchntIsdefined;
    }

    public String getGuaranteeFlag() {
        return guaranteeFlag;
    }

    public void setGuaranteeFlag(String guaranteeFlag) {
        this.guaranteeFlag = guaranteeFlag;
    }

    public BigDecimal getTransAmtLimit() {
        return transAmtLimit;
    }

    public void setTransAmtLimit(BigDecimal transAmtLimit) {
        this.transAmtLimit = transAmtLimit;
    }

    public String getUnionPayMchntCode() {
        return unionPayMchntCode;
    }

    public void setUnionPayMchntCode(String unionPayMchntCode) {
        this.unionPayMchntCode = unionPayMchntCode;
    }

    public String getOpenUser() {
        return openUser;
    }

    public void setOpenUser(String openUser) {
        this.openUser = openUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getAfterSplitFlag() {
        return afterSplitFlag;
    }

    public void setAfterSplitFlag(String afterSplitFlag) {
        this.afterSplitFlag = afterSplitFlag;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getSuspendFlag() {
        return suspendFlag;
    }

    public void setSuspendFlag(String suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getServiceTelephone() {
        return serviceTelephone;
    }

    public void setServiceTelephone(String serviceTelephone) {
        this.serviceTelephone = serviceTelephone;
    }

    public String getMchntPayFlag() {
        return mchntPayFlag;
    }

    public void setMchntPayFlag(String mchntPayFlag) {
        this.mchntPayFlag = mchntPayFlag;
    }

    public String getBussinessBeLong() {
        return bussinessBeLong;
    }

    public void setBussinessBeLong(String bussinessBeLong) {
        this.bussinessBeLong = bussinessBeLong;
    }

    public String getAllEmail() {
        return allEmail;
    }

    public void setAllEmail(String allEmail) {
        this.allEmail = allEmail;
    }

    public String getMchntSource() {
        return mchntSource;
    }

    public void setMchntSource(String mchntSource) {
        this.mchntSource = mchntSource;
    }

    public String getPlatformFlag() {
        return platformFlag;
    }

    public void setPlatformFlag(String platformFlag) {
        this.platformFlag = platformFlag;
    }

    public String getMchntMonType() {
        return mchntMonType;
    }

    public void setMchntMonType(String mchntMonType) {
        this.mchntMonType = mchntMonType;
    }

    public String getCaApplyState() {
        return caApplyState;
    }

    public void setCaApplyState(String caApplyState) {
        this.caApplyState = caApplyState;
    }

    public String getCaApplyEmail() {
        return caApplyEmail;
    }

    public void setCaApplyEmail(String caApplyEmail) {
        this.caApplyEmail = caApplyEmail;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAlterMobileFalg() {
        return alterMobileFalg;
    }

    public void setAlterMobileFalg(String alterMobileFalg) {
        this.alterMobileFalg = alterMobileFalg;
    }

    public String getShowChild() {
        return showChild;
    }

    public void setShowChild(String showChild) {
        this.showChild = showChild;
    }

    public String getContractFlag() {
        return contractFlag;
    }

    public void setContractFlag(String contractFlag) {
        this.contractFlag = contractFlag;
    }

    public String getGuaranteeStatus() {
        return guaranteeStatus;
    }

    public void setGuaranteeStatus(String guaranteeStatus) {
        this.guaranteeStatus = guaranteeStatus;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getTaxpayerName() {
        return taxpayerName;
    }

    public void setTaxpayerName(String taxpayerName) {
        this.taxpayerName = taxpayerName;
    }

    public String getTaxpayerNo() {
        return taxpayerNo;
    }

    public void setTaxpayerNo(String taxpayerNo) {
        this.taxpayerNo = taxpayerNo;
    }

    //纳税人识别号
    private String taxpayerNo;


    public String getCityCode() {
        return cityCode == null ? "" : cityCode.trim();
    }

    public void setMchntCode(String mchntCode) {
        this.mchntCode = mchntCode == null ? null : mchntCode.trim();
    }

    public void setMchntAbbrName(String mchntAbbrName) {
        this.mchntAbbrName = mchntAbbrName == null ? null : mchntAbbrName.trim();
    }

    public void setMchntName(String mchntName) {
        this.mchntName = mchntName == null ? null : mchntName.trim();
    }

    public String getMccCode() {
        return mccCode == null ? "" : mccCode.trim();
    }

    public void setMchntType(String mchntType) {
        this.mchntType = mchntType == null ? null : mchntType.trim();
    }

    public void setMchntStatus(String mchntStatus) {
        this.mchntStatus = mchntStatus == null ? null : mchntStatus.trim();
    }

    public void setMchntAddr(String mchntAddr) {
        this.mchntAddr = mchntAddr == null ? null : mchntAddr.trim();
    }

    public void setMchntZipcode(String mchntZipcode) {
        this.mchntZipcode = mchntZipcode == null ? null : mchntZipcode.trim();
    }

    public void setMchntFax(String mchntFax) {
        this.mchntFax = mchntFax == null ? null : mchntFax.trim();
    }

    public void setMchntArea(String mchntArea) {
        this.mchntArea = mchntArea == null ? null : mchntArea.trim();
    }

    public void setMchntLvl(String mchntLvl) {
        this.mchntLvl = mchntLvl;
    }

    public void setParentOrgCode(String parentOrgCode) {
        this.parentOrgCode = parentOrgCode == null ? null : parentOrgCode.trim();
    }

    public void setClrOrgCode(String clrOrgCode) {
        this.clrOrgCode = clrOrgCode == null ? null : clrOrgCode.trim();
    }

    public void setMchntUrl(String mchntUrl) {
        this.mchntUrl = mchntUrl == null ? null : mchntUrl.trim();
    }

    public void setMchntIvrNo(String mchntIvrNo) {
        this.mchntIvrNo = mchntIvrNo == null ? null : mchntIvrNo.trim();
    }

    public void setMchntIntro(String mchntIntro) {
        this.mchntIntro = mchntIntro == null ? null : mchntIntro.trim();
    }

    public void setDiscountRuleIntro(String discountRuleIntro) {
        this.discountRuleIntro = discountRuleIntro == null ? null : discountRuleIntro.trim();
    }

    public void setMchntLogo(String mchntLogo) {
        this.mchntLogo = mchntLogo == null ? null : mchntLogo.trim();
    }

    public void setChkMethod(String chkMethod) {
        this.chkMethod = chkMethod == null ? null : chkMethod.trim();
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }

    public void setDevelopMode(String developMode) {
        this.developMode = developMode == null ? null : developMode.trim();
    }
    
}
