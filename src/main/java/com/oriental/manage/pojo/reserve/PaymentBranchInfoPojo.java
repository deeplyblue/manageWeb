package com.oriental.manage.pojo.reserve;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by jinxin on 2016/12/22.
 * 3.5.6	支付机构分公司信息管理
 */
@Setter
@Getter
@ToString
public class PaymentBranchInfoPojo {

    /**
     * id 主键
     */
    private String id;

    /**
     * 机构编号
     */
    private String orgNo;

    /**
     *机构名称
     */
    private String orgName;

    /**
     *分公司编号
     */
    private String branchNO;

    /**
     *分公司名称
     */
    private String branchName;

    /**
     *成立时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String setupDate;

    /**
     *备案完成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String regDate;

    /**
     *备案业务类型
     */
    private String regService;

    /**
     * 住所（省份）
     */
    private String addrProvince;

    /**
     *住所（城市）
     */
    private String addrCity;

    /**
     *住所
     */
    private String addr;

    /**
     *实际经营地（省份）
     */
    private String realProvince;

    /**
     *实际经营地（城市）
     */
    private String realCity;

    /**
     *实际经营地
     */
    private String realAddr;

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
     */
    private String fax;

    /**
     *负责人手机
     */
    private String mobile;

    /**
     *负责人办公电话
     */
    private String tel;

    /**
     *员工人数
     */
    private String peopleNum;

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
     *保送msgId
     */
    private String msgId;

}
