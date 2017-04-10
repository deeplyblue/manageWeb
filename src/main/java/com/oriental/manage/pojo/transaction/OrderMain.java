package com.oriental.manage.pojo.transaction;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: hj
 * Date: 2016/6/8 15:37
 * Desc:
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class OrderMain extends BaseModel {

    /** 主键 */
    private String id;
    /** 商户号  */
    private String merchantCode;
    /** 订单号   */
    private String orderNo;
    /** 订单日期    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;
    /** 订单类型     */
    private String orderType;
    /** 接入渠道  */
    private String channel;
    /** 平台流水号 */
    private String ourTransNo;
    /** 托管协议号 **/
    private String escrowNo;
    /** 订单状态  */
    private String orderStatus;
    /** 总金额（分）  */
    private String totalAmt;
    /** 货币类型 */
    private String curType;
    /** 前台通知地址   */
    private String pgUrl;
    /** 后台通知地址   */
    private String bgUrl;
    /** 回调类型 */
    private String callbackType;
    /** 通知类型 00:同步结果通知 01:异步结果通知   */
    private String notifyType;
    /** 通知状态 A:未通知 B:已通知  */
    private String notifyStatus;
    /** 通知描述  */
    private String notifyDesc;
    /** 商品类型   */
    private String goodsType;
    /** 商品名称   */
    private String goodsName;
    /** 商户类别码   */
    private String mccType;
    /** 商户类别描述   */
    private String mccName;
    /** 产品ID   */
    private String productId;
    /** 验签     */
    private String mac;
    /** 加密类型    */
    private String encryptType;
    /** 清算日期     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleDate;
    /** 用户Ip  */
    private String clientIp;
    /** 受理机器Ip */
    private String acceptSrvIp;
    /** 原平台流水号 */
    private String oldOurTransNo;
    /** 冲正标识   */
    private String reverseFlag;
    /** 附加信息   */
    private String attach;
    /** 流水勾兑状态 C:初始状态 */
    private String chkStatus;
    /** 流水勾兑批次 */
    private String chkBatchNo;
    /** 删除标识 0:正常 1:删除 */
    private String deleteFlag;
    /** 创建时间 **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 最后更新时间 **/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;
    /** 亿付数字用户号 **/
    private String digitalPayAccountNo;
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

    private BigDecimal amount;

}