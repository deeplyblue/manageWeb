package com.oriental.manage.pojo.merchant;

import com.oriental.manage.pojo.base.BaseModel;

import java.util.Date;

/**
 * 银行类实现配置表
 * Created by wangjun on 2016/5/23.
 */
public class BankClassCfg extends BaseModel {

   //唯一id
    private String id;
   //dict-comm交易代码:
    private String transCode;
   //支付机构代码
    private String payOrgCode;
   //类路径
    private String classPath;
   //Action名
    private String actionName;
   //dict:comm银行类型:01:个人网银02:企业网银03:WAP网银04:东方金融自由05:第三方账户06:语音银行借记卡07:语音银行信用卡08:语音银行回呼借记卡09:语音银行一线通借记卡10:语音银行预授权11:代扣代付银行05:第三方支付
    private String bankType;
   //记录生成日期
    private Date createTime;
    //记录最后更新日期
    private Date lastUpdTime;

    public Date getLastUpdTime() {
        return lastUpdTime;
    }

    public void setLastUpdTime(Date lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransCode() {
        return transCode;
    }

    public String getPayOrgCode() {
        return payOrgCode;
    }

    public String getClassPath() {
        return classPath;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getBankType() {
        return bankType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



    public void setTransCode(String transCode) {
        this.transCode = transCode == null ? null : transCode.trim();
    }

    public void setPayOrgCode(String payOrgCode) {
        this.payOrgCode = payOrgCode == null ? null : payOrgCode.trim();
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath == null ? null : classPath.trim();
    }

    public void setBankType(String bankType) {
        this.bankType = bankType == null ? null : bankType.trim();
    }
}
