package com.oriental.manage.pojo.business;

import com.oriental.manage.pojo.base.BaseModel;

import java.util.Date;
import java.util.Set;

/**
 * Created by wangjun on 2016/5/25.
 */
public class BusiChanStrategyCfg extends BaseModel {

    //配置id
    private String id;
    //业务机构代码
    private String orgCode;
    //银行代码
    private String bankCode;
    //银行描述
    private String bankDesc;
   //dict:comm启用标识:0:关闭1:启用
    private String enableFlag;
    //业务类型
    private String transCode;
    //业务类型描述
    private String transCodeDesc;
    //优先级
    private String priorityLevel;
    //创建人员
    private String creator;
    //修改人
    private String modifier;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date lastUpdTime;
    //借贷标记:01:借记卡 02:贷记卡
    private String cdFlag;
    //公私标识:00:对私 01:对公
    private String ppFlag;
    //大额、小额标记
    private String sizeLimitFlag;
    private Set<String> ids;
    private String bid;
    public String getSizeLimitFlag() {
        return sizeLimitFlag;
    }

    public void setSizeLimitFlag(String sizeLimitFlag) {
        this.sizeLimitFlag = sizeLimitFlag;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankDesc() {
        return bankDesc;
    }

    public void setBankDesc(String bankDesc) {
        this.bankDesc = bankDesc;
    }

    public String getCdFlag() {
        return cdFlag;
    }

    public void setCdFlag(String cdFlag) {
        this.cdFlag = cdFlag;
    }

    public String getPpFlag() {
        return ppFlag;
    }

    public void setPpFlag(String ppFlag) {
        this.ppFlag = ppFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransCodeDesc() {
        return transCodeDesc;
    }

    public void setTransCodeDesc(String transCodeDesc) {
        this.transCodeDesc = transCodeDesc;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag == null ? null : enableFlag.trim();
    }


    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel == null ? null : priorityLevel.trim();
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

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }


}
