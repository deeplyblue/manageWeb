package com.oriental.manage.pojo.reserve;

import com.oriental.manage.pojo.base.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yutao on 2016/12/22.
 */
public class DayCut extends BaseModel implements Serializable {


    private static final long serialVersionUID = -3825153318865861768L;

    private String id;

    @DateTimeFormat(pattern="yyyyMMdd")
    private Date preWorkDate; //前一工作日期

    @DateTimeFormat(pattern="yyyyMMdd")
    private Date currWorkDate;//当前工作日期

    private String isHoliday; //节假日标志  01：非节假日  02：节假日

    @DateTimeFormat(pattern="yyyyMMdd")
    private Date nextWorkDate;//下一工作日期

    /**
     * 删除标识  0：未删除  1：删除
     */
    private String deleteFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 创建修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPreWorkDate() {
        return preWorkDate;
    }

    public void setPreWorkDate(Date preWorkDate) {
        this.preWorkDate = preWorkDate;
    }

    public Date getCurrWorkDate() {
        return currWorkDate;
    }

    public void setCurrWorkDate(Date currWorkDate) {
        this.currWorkDate = currWorkDate;
    }

    public Date getNextWorkDate() {
        return nextWorkDate;
    }

    public void setNextWorkDate(Date nextWorkDate) {
        this.nextWorkDate = nextWorkDate;
    }

    public String getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(String isHoliday) {
        this.isHoliday = isHoliday;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
