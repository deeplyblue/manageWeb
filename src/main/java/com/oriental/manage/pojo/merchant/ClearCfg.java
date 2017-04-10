package com.oriental.manage.pojo.merchant;

import com.oriental.manage.pojo.base.BaseModel;

import java.util.Date;

/**
 * Created by wangjun on 2016/5/16.
 * 结算信息配置表
 */
public class ClearCfg extends BaseModel{
    //编号
    private String id;

    //dict:comm机构类型:01:受理机构02:支付机构03:商户04:分润机构
    private String orgType;

    //dict:comm交易代码:
    private String transCode;

    //机构代码
    private String orgCode;

    //dict:comm接入渠道:
    private String connChannel;

    //最长划付时间间隔
    private String longestAllocateDays;

    //必须划付日
    private String mustAllocateDate;

    //财务结算日
    private String finClrDate;

    //dict:tab是否返回手续费:0:不返还1:返还
    private String reFeeFlag;

    //dict:comm启用标识:0:不启用1:启用
    private String enableFlag;

    //结算点,参照结算周期,填写星期几或几号,多次结算用逗号分隔
    private String clrPoint;

    //dict:tab结算周期:01:T+N结算02:通知结算03:每周一次结算04:每周两次结算05:每周定期结算06:每月定期结算07:按月结算（月末结算、月初结算）08:按半年结算09:按季度结算10:最低结算金额
    private String clrCycle;

    //dict:comm结算方式:01:全额结算02:净额结算03:固定按结算价结算04:手续费净额结算05:退货净额结算
    private String clrMethod;

    //创建人员
    private String creator;

    //更新人员
    private String modifier;

    //dict:comm审核状态:01:未处理02:审核成功03:审核失败
    private String auditStatus;

    //审核人员
    private String auditor;

    //审核时间
    private Date auditDate;

    //结算账户编号
    private String clrAccount;

    //记录生成日期
    private Date createTime;

    //记录最后更新日期
    private Date lastUpdTime;

    //dict:comm结算类型:01:一级结算02:二级结算
    private String clrType;

    //单笔有效金额
    private String effectAmt;

    //最低划付金额
    private String leastAllocateAmt;

    //是否增加预授权操作费到结算数据中:0:不需要1:需要
    private String isAddPreauthJs;

    //结算开始日期
    private String clrBgnDate;

    //结算范围
    private String clrRange;

    //强制结算日期:用,分割
    private String mandatoryClrDate;

    //商户简称
    private String mchntAbbrName;

    //商户全称
    private String mchntName;

    //手续费计算方式：逐笔、汇总
    private String feeMethod;

    //是否分账商户
    private String isSplit;

    //是否有协议
    private String isContract;

    //分账标识
    private String splitFlag;

    //担保交易标识
    private String guaranteeFlag;

    //后分账标识
    private String afterSplitFlag;

    //合并付款标识
    private String mergeFlag;

    //暂停付款标识
    private String suspendFlag;

    //商户级别
    private String mchntLvl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgType() {
        return orgType;
    }

    public String getTransCode() {
        return transCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public String getConnChannel() {
        return connChannel;
    }

    public String getLongestAllocateDays() {
        return longestAllocateDays;
    }

    public void setLongestAllocateDays(String longestAllocateDays) {
        this.longestAllocateDays = longestAllocateDays;
    }

    public String getMustAllocateDate() {
        return mustAllocateDate;
    }

    public void setMustAllocateDate(String mustAllocateDate) {
        this.mustAllocateDate = mustAllocateDate;
    }

    public String getFinClrDate() {
        return finClrDate;
    }

    public void setFinClrDate(String finClrDate) {
        this.finClrDate = finClrDate;
    }

    public String getReFeeFlag() {
        return reFeeFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public String getClrPoint() {
        return clrPoint;
    }

    public String getClrCycle() {
        return clrCycle;
    }

    public String getClrMethod() {
        return clrMethod;
    }

    public String getCreator() {
        return creator;
    }

    public String getModifier() {
        return modifier;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public String getAuditor() {
        return auditor;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public String getClrAccount() {
        return clrAccount;
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

    public String getClrType() {
        return clrType;
    }

    public String getEffectAmt() {
        return effectAmt;
    }

    public void setEffectAmt(String effectAmt) {
        this.effectAmt = effectAmt;
    }

    public String getLeastAllocateAmt() {
        return leastAllocateAmt;
    }

    public void setLeastAllocateAmt(String leastAllocateAmt) {
        this.leastAllocateAmt = leastAllocateAmt;
    }

    public String getIsAddPreauthJs() {
        return isAddPreauthJs;
    }

    public void setIsAddPreauthJs(String isAddPreauthJs) {
        this.isAddPreauthJs = isAddPreauthJs;
    }

    public String getClrBgnDate() {
        return clrBgnDate;
    }

    public void setClrBgnDate(String clrBgnDate) {
        this.clrBgnDate = clrBgnDate;
    }

    public String getClrRange() {
        return clrRange;
    }

    public void setClrRange(String clrRange) {
        this.clrRange = clrRange;
    }

    public String getMandatoryClrDate() {
        return mandatoryClrDate;
    }

    public void setMandatoryClrDate(String mandatoryClrDate) {
        this.mandatoryClrDate = mandatoryClrDate;
    }

    public String getMchntAbbrName() {
        return mchntAbbrName;
    }

    public void setMchntAbbrName(String mchntAbbrName) {
        this.mchntAbbrName = mchntAbbrName;
    }

    public String getMchntName() {
        return mchntName;
    }

    public void setMchntName(String mchntName) {
        this.mchntName = mchntName;
    }

    public String getFeeMethod() {
        return feeMethod;
    }

    public void setFeeMethod(String feeMethod) {
        this.feeMethod = feeMethod;
    }

    public String getIsSplit() {
        return isSplit;
    }

    public void setIsSplit(String isSplit) {
        this.isSplit = isSplit;
    }

    public String getIsContract() {
        return isContract;
    }

    public void setIsContract(String isContract) {
        this.isContract = isContract;
    }

    public String getSplitFlag() {
        return splitFlag;
    }

    public void setSplitFlag(String splitFlag) {
        this.splitFlag = splitFlag;
    }

    public String getGuaranteeFlag() {
        return guaranteeFlag;
    }

    public void setGuaranteeFlag(String guaranteeFlag) {
        this.guaranteeFlag = guaranteeFlag;
    }

    public String getAfterSplitFlag() {
        return afterSplitFlag;
    }

    public void setAfterSplitFlag(String afterSplitFlag) {
        this.afterSplitFlag = afterSplitFlag;
    }

    public String getMergeFlag() {
        return mergeFlag;
    }

    public void setMergeFlag(String mergeFlag) {
        this.mergeFlag = mergeFlag;
    }

    public String getSuspendFlag() {
        return suspendFlag;
    }

    public void setSuspendFlag(String suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public String getMchntLvl() {
        return mchntLvl;
    }

    public void setMchntLvl(String mchntLvl) {
        this.mchntLvl = mchntLvl;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    // 开通日期时间
    private String openTime;

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode == null ? null : transCode.trim();
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public void setConnChannel(String connChannel) {
        this.connChannel = connChannel == null ? null : connChannel.trim();
    }

    public void setReFeeFlag(String reFeeFlag) {
        this.reFeeFlag = reFeeFlag == null ? null : reFeeFlag.trim();
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag == null ? null : enableFlag.trim();
    }

    public void setClrPoint(String clrPoint) {
        this.clrPoint = clrPoint == null ? null : clrPoint.trim();
    }

    public void setClrCycle(String clrCycle) {
        this.clrCycle = clrCycle == null ? null : clrCycle.trim();
    }

    public void setClrMethod(String clrMethod) {
        this.clrMethod = clrMethod == null ? null : clrMethod.trim();
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public void setClrAccount(String clrAccount) {
        this.clrAccount = clrAccount == null ? null : clrAccount.trim();
    }

    public void setClrType(String clrType) {
        this.clrType = clrType == null ? null : clrType.trim();
    }
}
