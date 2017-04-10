package com.oriental.manage.pojo.transaction;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * User: hj
 * Date: 2016/6/8 15:37
 * Desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class BizTrans extends BaseModel {

    /** 业务流水号  */
    private String busiNo;
    /** 商户号 */
    private String merchantCode;
    /** 商户订单号  */
    private String orderNo;
    /** 订单日期  */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;
    /** 接入渠道 */
    private String channel;
    /** 平台流水号  */
    private String ourTransNo;
    /** 交易代码   */
    private String transCode;
    /** 交易金额   */
    private String transAmt;
    /** 交易状态  */
    private String transStatus;
    /** 业务请求流水号  */
    private String busiReqNo;
    /** 业务请求日期    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date busiReqDate;
    /**  业务响应流水号 */
    private String busiRespNo;
    /** 业务响应日期   */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date busiRespDate;
    /**  业务响应码  */
    private String busiRespCode;
    /**  业务响应信息  */
    private String busiRespDesc;
    /**  清算日期  */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleDate;
    /**  清算状态  */
    private String settleStatus;
    /**  清算批次号  */
    private String settleBatchNo;
    /**  是否参与清算:0:否1:是   */
    private String settleFlag;
    /**  流水勾兑状态:C:初始状态 A:勾兑成功 F:勾兑失败 B:金额不符  */
    private String chkStatus;
    /**  正反交易标识:0:反交易1:正交易  */
    private String reverseFlag;
    /**  退款标识:0:未退款1:已退款2:部分退款3:已冲正  */
    private String refundFlag;
    /**  原业务流水号:退款交易时：记录对应的正常交易的BUSI_No  */
    private String oldBusiNo;
    /** 受理机器IP   */
    private String acceptSrvIp;
    /**  主业务域  */
    private String mainBusiField;
    /**  删除标识:0:正常1:删除  */
    private String deleteFlag;
    /** 同步状态:0:未同步 1:已同步  */
    private String syncStatus;
    /** 创建时间 **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 最后更新时间 **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;
    /***********查询参数**************/
    /** 订单开始时间 **/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date beginOrderDate;
    /** 订单结束时间 **/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date endOrderDate;
    /** 清算开始时间 **/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date beginSettleDate;
    /** 清算结束时间 **/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date endSettleDate;

    private String  payReqNo;

    private String  payRespNo;

    private String bankCode;
}
