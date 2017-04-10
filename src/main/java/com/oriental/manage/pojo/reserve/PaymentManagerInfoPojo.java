package com.oriental.manage.pojo.reserve;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by jinxin on 2016/12/21.
 * 支付机构董高监信息
 */
@Setter
@Getter
@ToString
public class PaymentManagerInfoPojo extends BaseModel{


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
     * 姓名
     */
    private String name;

    /**
     *身份类型
     * 01：董事
     *02：监事
     *03：高管
     */
    private String type;

    /**
     *职务
     */
    private String rule;

    /**
     * 办公电话
     * 禁止中文
     */
    private String tel;

    /**
     *手机
     */
    private String mobile;

    /**
     *邮件
     */
    private String email;
    /**
     *学历
     */
    private String education;
    /**
     * 专业
     */
    private String proeession;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     *从业经历
     */
    private String workExp;
    /**
     * 离职标识
     * 01：在职
     * 02：离职中
     * 03：已离职
     */
    private String offStatus;


    /**
     * 是否经总行批复同意
     * 当身份类型为03高管时，必须填写。
     * 01：是
     * 02：否
     */
    private String isAgree;

    /**
     * 操作类型
     * 00：新增
     * 01：修改
     * 02：删除
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

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
