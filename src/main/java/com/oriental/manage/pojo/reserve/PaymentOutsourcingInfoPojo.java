package com.oriental.manage.pojo.reserve;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jinxin on 2016/12/22.
 * 3.5.8	支付机构业务外包信息管理
 */
@Setter
@Getter
@ToString
public class PaymentOutsourcingInfoPojo {

    /**
     * 主键id
     */
    private  String id;

    /**
     * 机构编号
     */
    private String orgNo;

    /**
     *机构名称
     */
    private String orgName;

    /**
     * 外包服务机构名称
     */
    private String serviceOrgName;

    /**
     *营业执照编号
     */
    private String businessCode;

    /**
     *经营范围
     */
    private String businessScope;

    /**
     *组织机构代码
     */
    private String orgCode;

    /**
     *外包服务内容
     */
    private String serviceInfo;

    /**
     *成立时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String setupDate;

    /**
     *注册资本
     * {18,2}D
     */
    private BigDecimal capital;

    /**
     *注册地
     */
    private String address;

    /**
     * 实际经营地
     */
    private String realAddr;

    /**
     *所在城市（省份）
     */
    private String localProvince;

    /**
     *所在城市（城市）
     */
    private String localCity;

    /**
     *所在城市
     */
    private String city;

    /**
     *主要负责人
     */
    private String manager;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     *传真
     * 禁止中文
     */
    private String fax;

    /**
     * 负责人办公电话
     * 禁止中文
     */
    private String tel;

    /**
     *负责人手机
     */
    private String mobile;

    /**
     * 分润
     * 支付机构和外包机构间的利润分配约定
     */
    private String profit;

    /**
     *保证金
     *{18,2}D
     * 外包服务商给支付机构的保证金
     */
    private BigDecimal bond;

    /**
     *企业资质评估
     */
    private String assess;

    /**
     *操作类型
     * 00：新增
     * 01：修改
     * 02：删除
     */
    private String operateType;

    /**
     *操作人
     */
    private String operator;

    /**
     *操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String operateTime;
/*------------以下为数据操作流字段---------------*/
    /**
     * 数据状态 01：生效 02：未生效
     */
    private String dataStatus;

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
     *报送msgId
     */
    private String msgId;


}
