package com.oriental.manage.pojo.reserve;

import com.oriental.manage.pojo.base.BaseModel;

import java.io.Serializable;

/**
 * Created by yutao on 2016/12/22.
 */
public class SysParticipantsMsg extends BaseModel implements Serializable{

    private static final long serialVersionUID = 719237105962543312L;

    private String type; //参与者类型 01：支付机构 02：银行

    private String code; // 银行行号/支付机构编号

    private String name; //

    private String deleteFlag; // 0：正常  1：弃用

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }




}
