package com.oriental.manage.pojo.merchant;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wangjun on 2016/5/16.
 * 合同信息
 */

@Data
public class ContractInfo extends BaseModel {

    //编号
    private String id;
    //合同编号
    private String contCode;
    //修改前合同编号
    private String oldContCode;
    //合同名称
    private String contName;
    //合同概要
    private String contSummary;
    //合同期限（月）
    private String contDuration;
    //合同签订日期
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date contSignedDate;
    //合同生效日期
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date contBgnDate;
    //审核状态
    private String auditStatus;
    //合同开通日期
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date openDatetime;
    //合同结束日期
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date contEndDate;
    //签约机构
    private String signedOrgCode;
    //dict:comm代码类型,商户或机构:
    private String companyType;
    //商户或机构代码
    private String companyCode;
    //营业许可证号
    private String bizLicenseNo;
    //企业代码
    private String enterpriseCode;
    //记录生成日期
    private Date createTime;
    //记录最后更新日期
    private Date lastUpdTime;
    /**合同附件dfs路径*/
    private String dfsContAttach;
    /**DFS开户行许可证*/
    private String dfsOpenBankCert;
    /**DFS纳税人证书*/
    private String dfsRatePayerCert;
    /**DFS税务登记证*/
    private String dfsTaxRegisterCert;
    /**DFS营业执照*/
    private String dfsBizLicenseCert;
    /**DFS组织机构代码证*/
    private String dfsOrganizationCodeCert;
    /**DFS银行基本信息文件*/
    private String dfsBankFile;
    //商户简称
    private String mchntAbbrName;
    //商户代码
    private String mchntCode;
    //联系人姓名
    private String cttName;
    //邮箱
    private String cttEmail;
    //手机号
    private String cttMobile;
    //是否父子商户
    private String parentOrgCode;
    /**修改人*/
    private String modifier;


    /** 纳税人证书到期日*/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date payerCertEndDate;
    /** 税务登记证到期日*/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date taxRegisterEndDate;
    /** 营业执照到期日*/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date bizLicenseEndDate;
    /** 组织机构期日*/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date organizationEndDate;
    /** 税务登记证编号*/
    private String taxRegisterId;
//    /** 营业执照编号*/

    /** 组织机构编号*/
    private String organizationCodeId;







}
