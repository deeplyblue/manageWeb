package com.oriental.manage.pojo.merchant;

import com.oriental.manage.pojo.base.BaseModel;

/**
 * Created by wangjun on 2016/5/23.
 */
public class SeqNames extends BaseModel {

    //编号 
    private String id;

    // 唯一 机构号
    private String payOrgCode;

    //序列名称
    private String seqName;


    public String getId() {
        return id;
    }

    public String getPayOrgCode() {
        return payOrgCode;
    }

    public String getSeqName() {
        return seqName;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public void setPayOrgCode(String payOrgCode) {
        this.payOrgCode = payOrgCode == null ? null : payOrgCode.trim();
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName == null ? null : seqName.trim();
    }
}
