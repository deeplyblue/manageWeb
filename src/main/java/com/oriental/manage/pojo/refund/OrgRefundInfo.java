package com.oriental.manage.pojo.refund;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * User: hj
 * Date: 2016/5/26 17:06
 * Desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class OrgRefundInfo extends BaseModel {

    /**
     * 编号
     **/
    private String id;

    /**
     * 订单号
     **/
    private String orderNo;

    /**
     * 订单日期
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;

    /**
     * 渠道
     **/
    private String channel;

    /**
     * 支付机构号
     **/
    private String payOrgCode;

    /**
     * 机构银行
     **/
    private String orgBankCode;

    /**
     * 退款金额
     **/
    private String refundAmt;

    /**
     * 订单金额
     **/
    private String totalAmt;

    /**
     * 业务退款申请表ID
     **/
    private String busiRefundId;

    /**
     * 支付流水号
     **/
    private String payTransNo;

    /**
     * 原支付流水号
     **/
    private String oldPayTransNo;

    /**
     * 原平台流水号
     **/
    private String oldOurTransNo;

    /**
     * 原支付请求流水号
     **/
    private String oldPayReqNo;

    /**
     * 调账状态
     **/
    private String orderAdjStatus;

    /**
     * 退款类型 1-线上退款 2-线下退款 3-脱机退款
     **/
    private String refundType;

    /**
     * 差异类型  1-普通退款 2-机构差异退款 3-内部差异退款
     **/
    private String errType;

    /**
     * 交易信息类型:01:正常02:超期退款03:账户异常04:资金部付款 05:调账06:补账07:线下
     **/
    private String transInfoType;

    /**
     * 退款状态:1:申请2:申请审核通过3:申请审核未通过4:申请商户已撤销5:申请平台已撤销6:申请正在处理中7:申请处理成功8:申请处理失败
     **/
    private String refundStatus;

    /**
     * 退款申请人
     **/
    private String refundApplicant;

    /**
     * 申请人IP
     **/
    private String applicantIp;

    /**
     * 审核人
     **/
    private String auditor;

    /**
     * 审核时间
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditDate;

    /**
     * 处理人
     **/
    private String procEmpId;

    /**
     * 处理日期
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date procDatetime;

    /**
     * 记录生成日期
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 记录最后更新日期
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdTime;

    /*****************查询相关参数***********************/

    /**
     * 申请开始时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date refundBeginDate;

    /**
     * 申请结束时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date refundEndDate;

    /**
     * 审核开始时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date auditBeginDate;

    /**
     * 审核结束时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date auditEndDate;

    /**
     * 处理开始时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date proBeginDate;

    /**
     * 处理结束时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date proEndDate;

    /**
     * 商户号
     */
    private String mchntCode;
}
