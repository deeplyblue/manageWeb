package com.oriental.manage.pojo.institution;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wangjun on 2016/5/4.
 *  机构信息表
 */
@Data
public class OrganizeInfo extends BaseModel {

    private String orgCode;//机构代码
    private String orgCategory;//机构种类:01:受理机构02:支付机构03:商户04:分润机构
    @NotBlank(message = "机构类型不能为空")
    private String orgType;//机构类型:10:核心交易平台20:金融行业（银行、银联）30:第三方支付40:其他行业卡发卡机构50:其他分润方机构
    @NotBlank(message = "机构全称不能为空")
    private String orgName;//机构全称f
    @NotBlank(message = "机构简称不能为空")
    private String orgAbbrName;//机构简称
    private String orgIntro;//机构介绍
    @NotBlank(message = "机构归属地不能为空")
    private String orgArea;//机构归属地
    private String orgInOrOutFlag;//内外部类型 1.内部 2.外部
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date orgConnDate;//机构接入日期
    private String handler;//操作员号
    private String orgStatus;//当前状态:01:初始02:正常03:锁定04:注销05:停用
    private String orgOrganizationCode;//组织机构代码
    private String orgLegalProxy;//法定代表人
    private String bizLicenseNo;//营业执照注册号
    private String orgRegAddr;//机构注册地址
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date createTime;//记录生成日期
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date lastUpdTime;//记录最后更新日期
    private String orgFax;//机构传真
    private String orgZipcode;//机构邮编
    private String orgEtc;//机构备注信息
    private String auditDate;//审核日期
    private String auditor;//审核人
    private String isAlarm;//是否告警  0：表示不告警  1：表示告警
    private String isfullsettlement;//是否全额结算商户 0：表示否 1：表示是
    private String certEndDate;//证书到期日期
    private String intervalTime;//补单查询间隔时间 单位为分钟
    private String orgRemark;//备注
    private String verifyCycle;//T+1  T+2
    private String fileSource;//1接口下载对账文件 2查询接口下载对账文件 3银行推送 4手工上传对账文件
    private String refundMode;//1接口退款 2接口隔日退款 3接口当天退款 4银行平台退款 5线下退款
    private String refundCycle;//是指银行退款到账需要的天数
    private String certificateFlg;//是否有验签证书 Y：有  N：无
    private String successRate;//2小时成功率基准值
    private String orgRunStatus;//机构运行状态
    private String refundHasCheck;//退款交易是否需要对账：0：不需要对账；1：需要对账
    private String payOrgMchnt;//支付机构商户代码
    private String payOrgLevel;//支付机构级别
    private String partRefundFlag;//是否支持部分退款:0:不支持1:支持
    private String feeCollectWay;//手续费收取方式:01:逐笔差额按日抵扣,02:汇总差额按日抵扣,03:全额按年抵扣,04:全额按年后付," +"05:全额按半年抵扣,06:全额按半年后付,07:全额按季抵扣,08:全额按季后付,09:全额次月抵扣,10:全额按月后付,11:无手续费
    private String mchntFlag;//是否具有商户身份，用户受理机构做交易时同步数据
    private String payBankCode  ; //PAY_BANK_CODE  支付银行代码
//    private String  accountNO;//ACCOUNT_NO       备付金账号
    /**通道类别**/
    private String channelType;


}
