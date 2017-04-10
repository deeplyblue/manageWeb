package com.oriental.manage.pojo.base;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class IplimitCfg extends  BaseModel implements Serializable{

    private static final long serialVersionUID = -3021583855478834240L;

    private String id;

    private String clientIp;

    private String userId;

    private String ipType;

    private String enableFlag;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date ipBeginTime;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date ipEndTime;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date lastUpdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getIpType() {
        return ipType;
    }

    public void setIpType(String ipType) {
        this.ipType = ipType == null ? null : ipType.trim();
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag == null ? null : enableFlag.trim();
    }

    public Date getIpBeginTime() {
        return ipBeginTime;
    }

    public void setIpBeginTime(Date ipBeginTime) {
        this.ipBeginTime = ipBeginTime;
    }

    public Date getIpEndTime() {
        return ipEndTime;
    }

    public void setIpEndTime(Date ipEndTime) {
        this.ipEndTime = ipEndTime;
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