package com.oriental.manage.pojo.institution;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 2016/5/9.
 * 机构银行关系
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrgBank extends BaseModel {

    // 0 未选中 1选中
    private String choose;

    //配置ID
    private String id;

    //银行代码
    private String bankCode;

    //银行类型
    private String bankType;

    private String bankName;

    //机构代码
    private String orgCode;

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

    //银行退款期限
    private String refundDeadline;

    //机构退款限额
    private String orgRefundLimit;

    //是否线上退款
    private String refundOlFlag;

    //机构银行代码
    private String orgBankCode;

    //交易上限
    private String transAmtUpplimit;

    //交易下限
    private String transAmtLowlimit;

    //外系统银行描叙
    private String orgBankCodeDesc;

    //外系统银行分类
    private String bankCatalog;

    //渠道
    private String connChannel;

    //交易代码
    private String transCode;

    //优先策略
    private Integer stgyPriority;

    //账号是否与银行预留手机号一致
    private String payLvl;

    //是否发送至银行预留手机号
    private String phoneLvl;

    //payLvl或者phoneLvl是否被修改
    private String isChanged;

    // 配置模板
    private String templateNo;

    private String transCodeFlag;

    private String companyType;

    private List<String> orgCodeList;

    public OrgBank(){

    }



    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
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

}
