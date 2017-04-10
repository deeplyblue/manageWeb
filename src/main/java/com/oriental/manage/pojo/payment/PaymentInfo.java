package com.oriental.manage.pojo.payment;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class PaymentInfo extends BaseModel {

    /**
     * 商户号
     */
    private String mchntCode;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 查询编号
     */
    private String queryOrderNo;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 金额
     */
    private String amount;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 银行预留手机号
     */
    private String phoneNo;

    /**
     * 卡号
     */
    private String bankCardNo;

    /**
     * 身份证号
     */
    private String identityCardNo;

    /**
     * 中奖等级
     */
    private String bonusLevel;

    /**
     * 状态
     */
    private String status;

    /**
     * 请求时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reqTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 审核时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdTime;

    /*****************查询相关参数***********************/

    /**
     * 查询开始时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date beginDate;

    /**
     * 查询结束时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date endDate;

    /**
     * 处理开始时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date operateBeginDate;

    /**
     * 处理结束时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date operateEndDate;

    /**
     * 总笔数
     */
    private String sumNum;

    /**
     * 总金额
     */
    private String sumAmt;

    /**
     * 响应描述
     */
    private String respDesc;

    /**
     * 退款标识
     */
    private String refundFlag;

    /**
     * 交易状态
     */
    private String transStatus;

}