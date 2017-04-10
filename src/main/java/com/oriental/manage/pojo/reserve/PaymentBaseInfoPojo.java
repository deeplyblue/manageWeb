package com.oriental.manage.pojo.reserve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oriental.manage.pojo.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jinxin on 2016/12/21.
 * 支付机构基本信息
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentBaseInfoPojo extends BaseModel{

    /**
     * 主键
     */
    private String id;

    /**
     * 机构编号
     */
    private String orgNo;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     *机构类型
     * 01：支付机构
     *02：分公司
     */
    private String orgType;

    /**
     * 组织机构代码
     */
    private String orgCode;

    /**
     * 许可证有效期
     * yyyymmdd
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date licenseDate;

    /**
     * 许可证编号
     */
    private String licenseNO;

    /**
     * 许可证发放日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date licenseGrantDate;

    /**
     * 营业执照编号
     */
    private String businessCode;

    /**
     * 住所（省份）
     */
    private String addrProvince;
    /**
     * 住所（城市）
     */
    private String addrCity;
    /**
     * 住所
     */
    private String addr;
    /**
     * 实际经营地（省份）
     */
    private String realProvince;
    /**
     * 实际经营地（城市）
     */
    private String realCity;

    /**
     * 实际经营地
     */
    private String realAddr;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 注册资本
     * 整数位16位，小数位2位。
     */
    private String registeredCapital;

    /**
     * 组织形式
     */
    private String orgForm;

    /**
     * 实缴货币资本
     */
    private String moneyCapital;

    /**
     *法定代表人
     */
    private String legalPerson;

    /**
     * 投诉电话
     * 禁止中文
     */
    private String complainTel;

    /**
     * 传真
     * 禁止中文
     */
    private String fax;

    /**
     * 出资人及持股比例
     * 前台获取数据
     */
    private List<SponsorPojo> sponsorList= new ArrayList<>();

    private  String sponsor;

    /**
     * 主要出资人及持股比例集合
     * 前台获取数据
     */
    private List<SponsorPojo> mastereSponsorList= new ArrayList<>();

    private  String mastereSponsor;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 审计机构名称
     */
    private String auditName;

    /**
     * 操作类型
     * 01：修改
     *02：删除
     */
    private String operateType;

    /**
     * 操作人
     * 操作用户
     */
    private String operator;

    /**
     * 操作时间
     * yyyymmddHHMMSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date operateTime;

    /**
     * 开始时间
     * yyyymmddHHMMSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

     /**
     * 结束时间
     * yyyymmddHHMMSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 业务种类及范围明细
     */
    private List<ServiceType> serviceList=new ArrayList<>();


/*------------以下为数据操作流字段---------------*/
    /**
     * 数据状态 01：生效 02：未生效
     */
    private String dataStatus;

    /**
     * 审核状态   01 ：未修改    02 ： 审核中   03 ：审核成功   04 ：审核失败
     */
    private String  auditStatus;

    /**
     * 是否人行下发数据  01是   00  否
     */
    private String centerStatus;


    /**
     *修改人
     */
    private String updateBy;

    /**
     *修改时间  yyyymmddHHMMSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    /**
     * 备注
     */
    private String remarkDesc;

    /**
     * 删除标示
     */
    private String deleteFlag;

    /**
     *保送msgId
     */
    private String msgId;

}
