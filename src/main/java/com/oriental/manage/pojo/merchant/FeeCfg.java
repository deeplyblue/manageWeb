package com.oriental.manage.pojo.merchant;

import com.oriental.manage.pojo.base.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangjun on 2016/5/13.
 *手续费信息
 */
public class FeeCfg extends BaseModel {
    //手续费方法序号 
    private String feeCfgNo;

    //dict:tab计费方法:01:按笔02:按周期 
    private String feeMethod;

    //dict:tab计费单位:01:按金额02:按笔03:分期数04:按差价 
    private String feeUnit;

    //dict:tab计费类型:01:不收费02:收费03:持卡人支付手续费 
    private String feeType;

    //dict:tab计费周期:00:按星期01:按半月02:按月03:按季度04:按半年05:按年06:按天 
    private String feeCycleType;

    //计费日期 
    private String feeDate;

    //dict:tab手续费计费方式:01:分段02:累进 
    private String feeSegType;

    //dict:tab手续费收取周期:00:按星期01:按半月02:按月03:按季度04:按半年05:按年07:日，周期补收差额08:日，周期补收保底金额 
    private String feeRcvCycle;

    //分段表启用标志 
    private String segmentFlag;

    //dict:tab计费标准:01:标准02:优惠03:基本 
    private String feeStandrad;

    //dict:comm启用标志:0:禁用1:启用 
    private String enableFlag;

    //优惠生效日期 
    private String discountBgnDate;

    //优惠过期日期 
    private String discountEndDate;

    //创建人员 
    private String creator;

    //修改人员 
    private String modifier;

    //同步审核状态 
    private String syncState;

    //优先级 
    private String feePriority;

    //记录生成日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //记录最后更新日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdTime;

    //机构代码 
    private String orgCode;

    //dict:comm交易代码: 
    private String transCode;

    //dict:comm接入渠道: 
    private String connChannel;

    //dict:comm代码类型,商户或机构: 
    private String companyType;

    //银行代码 
    private String bankCode;

    //银行代码 
    private String orgBankCode;

    //分账标识 
    private String splitFlag;

    //模板id 
    private String templateNo;

    //结算精确类型:1:四舍五入2:舍位 
    private String rateRoundType;

    //有效开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String validateBeginDate;

    //有效结束日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String validateEndDate;

    //协议模式：0：不续约，1：自动续约永久（50年） 
    private String protocolMode;

    //银行类型
    private String bankType;

    //机构名称 
    private String orgName;

    //机构子银行名称 
    private String orgBankCodeDesc;

    //手续费 
    private String fixFee;

    //最低手续费金额 
    private BigDecimal segLowLmt;

    //是否返还 
    private String reFeeFlag;

    //手续费收取下限只有当手续费计算方法为02百分比才有意义 
    private String feeLowLmt;

    //分段上限 
    private String segUppLmt;
    //首次交易日期
    private String firstSumDate;

    //手续费计算方法:01:固定02:百分比 
    private String feeSegMethod;

    public String getFeeCfgNo() {
        return feeCfgNo;
    }

    public String getFeeMethod() {
        return feeMethod;
    }

    public String getFeeUnit() {
        return feeUnit;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeCycleType() {
        return feeCycleType;
    }

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
    }

    public String getFeeSegType() {
        return feeSegType;
    }

    public String getFeeRcvCycle() {
        return feeRcvCycle;
    }

    public String getSegmentFlag() {
        return segmentFlag;
    }

    public String getFeeStandrad() {
        return feeStandrad;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public String getDiscountBgnDate() {
        return discountBgnDate;
    }

    public void setDiscountBgnDate(String discountBgnDate) {
        this.discountBgnDate = discountBgnDate;
    }

    public String getDiscountEndDate() {
        return discountEndDate;
    }

    public void setDiscountEndDate(String discountEndDate) {
        this.discountEndDate = discountEndDate;
    }

    public String getCreator() {
        return creator;
    }

    public String getModifier() {
        return modifier;
    }

    public String getSyncState() {
        return syncState;
    }

    public String getFeePriority() {
        return feePriority;
    }

    public void setFeePriority(String feePriority) {
        this.feePriority = feePriority;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getConnChannel() {
        return connChannel;
    }

    public void setConnChannel(String connChannel) {
        this.connChannel = connChannel;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getOrgBankCode() {
        return orgBankCode;
    }

    public void setOrgBankCode(String orgBankCode) {
        this.orgBankCode = orgBankCode;
    }

    public String getSplitFlag() {
        return splitFlag;
    }

    public void setSplitFlag(String splitFlag) {
        this.splitFlag = splitFlag;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }

    public String getRateRoundType() {
        return rateRoundType;
    }

    public void setRateRoundType(String rateRoundType) {
        this.rateRoundType = rateRoundType;
    }

    public String getValidateBeginDate() {
        return validateBeginDate;
    }

    public void setValidateBeginDate(String validateBeginDate) {
        this.validateBeginDate = validateBeginDate;
    }

    public String getValidateEndDate() {
        return validateEndDate;
    }

    public void setValidateEndDate(String validateEndDate) {
        this.validateEndDate = validateEndDate;
    }

    public String getProtocolMode() {
        return protocolMode;
    }

    public void setProtocolMode(String protocolMode) {
        this.protocolMode = protocolMode;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgBankCodeDesc() {
        return orgBankCodeDesc;
    }

    public void setOrgBankCodeDesc(String orgBankCodeDesc) {
        this.orgBankCodeDesc = orgBankCodeDesc;
    }

    public String getFixFee() {
        return fixFee;
    }

    public void setFixFee(String fixFee) {
        this.fixFee = fixFee;
    }

    public BigDecimal getSegLowLmt() {
        return segLowLmt;
    }

    public void setSegLowLmt(BigDecimal segLowLmt) {
        this.segLowLmt = segLowLmt;
    }

    public String getReFeeFlag() {
        return reFeeFlag;
    }

    public void setReFeeFlag(String reFeeFlag) {
        this.reFeeFlag = reFeeFlag;
    }

    public String getFeeLowLmt() {
        return feeLowLmt;
    }

    public void setFeeLowLmt(String feeLowLmt) {
        this.feeLowLmt = feeLowLmt;
    }

    public String getSegUppLmt() {
        return segUppLmt;
    }

    public void setSegUppLmt(String segUppLmt) {
        this.segUppLmt = segUppLmt;
    }

    public String getFeeSegMethod() {
        return feeSegMethod;
    }

    public void setFeeSegMethod(String feeSegMethod) {
        this.feeSegMethod = feeSegMethod;
    }

    public String getFirstSumDate() {
        return firstSumDate;
    }

    public void setFirstSumDate(String firstSumDate) {
        this.firstSumDate = firstSumDate;
    }



    public void setFeeCfgNo(String feeCfgNo) {
        this.feeCfgNo = feeCfgNo == null ? null : feeCfgNo.trim();
    }

    public void setFeeMethod(String feeMethod) {
        this.feeMethod = feeMethod == null ? null : feeMethod.trim();
    }

    public void setFeeUnit(String feeUnit) {
        this.feeUnit = feeUnit == null ? null : feeUnit.trim();
    }

    public void setFeeCycleType(String feeCycleType) {
        this.feeCycleType = feeCycleType == null ? null : feeCycleType.trim();
    }

    public void setFeeSegType(String feeSegType) {
        this.feeSegType = feeSegType == null ? null : feeSegType.trim();
    }

    public void setFeeRcvCycle(String feeRcvCycle) {
        this.feeRcvCycle = feeRcvCycle == null ? null : feeRcvCycle.trim();
    }

    public void setSegmentFlag(String segmentFlag) {
        this.segmentFlag = segmentFlag == null ? null : segmentFlag.trim();
    }

    public void setFeeStandrad(String feeStandrad) {
        this.feeStandrad = feeStandrad == null ? null : feeStandrad.trim();
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag == null ? null : enableFlag.trim();
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState == null ? null : syncState.trim();
    }
}
