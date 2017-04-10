package com.oriental.manage.pojo.business;

import com.oriental.manage.pojo.base.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 2016/5/24.
 */
public class MchntBank extends BaseModel {

    //配置ID
    private String id;

    //商户代码
    private String mchntCode;

    //分支机构ID
    private String mchntBrCode;

    //dict:comm接入渠道:
    private String connChannel;

    //dict:comm启用标识:0:关闭1:启用
    private String enableFlag;

    //创建人员
    private String creator;

    //修改人员
    private String modifier;

    //记录生成日期
    private Date createTime;

    //记录最后更新日期
    private Date lastUpdTime;

    //类型
    private String companyType;

    //银行ID
    private String bankCode;

    //银行类型
    private String bankType;

    //模板ID
    private String templateNo;

    //模板名称
    private String templateName;

    private String whiteMchntFlag;//是否添加白名单:0否  1是

    public List<String> getMchntList() {
        return mchntList;
    }

    public void setMchntList(List<String> mchntList) {
        this.mchntList = mchntList;
    }

    public String getWhiteMchntFlag() {
        return whiteMchntFlag;
    }

    public void setWhiteMchntFlag(String whiteMchntFlag) {
        this.whiteMchntFlag = whiteMchntFlag;
    }

    private List<String> mchntList;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMchntCode() {
        return mchntCode;
    }

    public void setMchntCode(String mchntCode) {
        this.mchntCode = mchntCode == null ? null : mchntCode.trim();
    }

    public String getMchntBrCode() {
        return mchntBrCode;
    }

    public void setMchntBrCode(String mchntBrCode) {
        this.mchntBrCode = mchntBrCode == null ? null : mchntBrCode.trim();
    }

    public String getConnChannel() {
        return connChannel;
    }

    public void setConnChannel(String connChannel) {
        this.connChannel = connChannel == null ? null : connChannel.trim();
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag == null ? null : enableFlag.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
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

}
