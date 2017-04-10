package com.oriental.manage.pojo.transaction;

import com.oriental.manage.pojo.base.BaseModel;
import com.oriental.paycenter.commons.utils.LogFilter;
import com.oriental.paycenter.commons.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * User: hj
 * Date: 2016/6/8 15:37
 * Desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayTrans extends BaseModel {

    /** 交易流水号 */
    private String payTransNo;
    /** 订单号    */
    private String orderNo;
    /** 订单日期    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;
    /** 接入渠道:01:WEB02:WAP03:SMS04:IVR05:客户端*/
    private String channel;
    /** 平台流水号     */
    private String ourTransNo;
    /** 银行编码   */
    private String bankCode;
    /** 银行类型   */
    private String bankType;
    /** 支付机构代码     */
    private String payOrgCode;
    /** 机构银行代码     */
    private String orgBankCode;
    /** 交易代码    */
    private String transCode;
    /** 交易金额    */
    private String transAmt;
    /** 交易状态:A:交易请求B:交易成功C:交易失败     */
    private String transStatus;
    /** 支付请求流水号     */
    private String payReqNo;
    /*** 支付请求日期     */
    private String payReqDate;
    /** 支付响应流水号     */
    private String payRespNo;
    /** 支付响应日期     */
    private String payRespDate;
    /** 支付响应码  */
    private String payRespCode;
    /** 支付响应信息   */
    private String payRespDesc;
    /** 支付清算信息   */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paySettleDate;
    /** 银行授权码     */
    private String authorizationCode;
    /** 支付人姓名     */
    private String payUserName;
    /** 支付人手机号 支付账号 并非银行预留手机号     */
    private String payUserMobile;
    /** 银行账号     */
    private String bankAccId;
    /** 密码     */
    private String bankAccPwd;
    /** 银行预留手机号   */
    private String phone;
    /** 总行     */
    private String headBank;
    /** 分行     */
    private String branchBank;
    /** 开户支行    */
    private String subBank;
    /** 开户省份    */
    private String province;
    /** 开户城市     */
    private String city;
    /** 公私标识     */
    private String ppFlag;
    /** 借贷标识    */
    private String cdFlag;
    /**  有效期     */
    private String validDate;
    /** CVV2     */
    private String cvv2;
    /** 证件类型    */
    private String certType;
    /** 证件号码     */
    private String certCode;
    /** 证件姓名     */
    private String certName;
    /** 原交易流水号:退款交易时：记录对应的正常交易的TRANS_NO值 */
    private String oldPayTransNo;
    /** 清算日期    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleDate;
    /** 清算状态:A:未清算B:已清算C:清算失败T:正在清算（中间状态）     */
    private String settleStatus;
    /** 清算批次号    */
    private String settleBatchNo;
    /** 是否参与清算:0:否1:是     */
    private String settleFlag;
    /** 正反交易标识:0:反交易1:正交易     */
    private String reverseFlag;
    /** 流水勾兑状态:C:初始状态A:勾兑成功F:勾兑失败B:金额不符 Q:查询成功     */
    private String chkStatus;
    /** 退款标识:0:未退款1:已退款2:部分退款3:已冲正*/
    private String refundFlag;
    /** 受理机器IP     */
    private String acceptSrvIp;
    /** 摘要     */
    private String digest;
    /** 删除标识:0:正常1:删除  */
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

    @Override
    public String toString() {
        return "PayTrans{" +
                "payTransNo='" + payTransNo + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderDate=" + orderDate +
                ", ourTransNo='" + ourTransNo + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankType='" + bankType + '\'' +
                ", payReqDate='" + payReqDate + '\'' +
                ", payReqNo='" + payReqNo + '\'' +
                ", payRespDate='" + payRespDate + '\'' +
                ", payRespNo='" + payRespNo + '\'' +
                ", payRespCode='" + payRespCode + '\'' +
                ", payRespDesc='" + payRespDesc + '\'' +
                ", transCode='" + transCode + '\'' +
                ", transAmt=" + transAmt +
                ", transStatus='" + transStatus + '\'' +
                ", channel='" + channel + '\'' +
                ", settleDate=" + settleDate +
                ", settleStatus='" + settleStatus + '\'' +
                ", settleBatchNo='" + settleBatchNo + '\'' +
                ", settleFlag='" + settleFlag + '\'' +
                ", orgBankCode='" + orgBankCode + '\'' +
                ", payOrgCode='" + payOrgCode + '\'' +
                ", reverseFlag='" + reverseFlag + '\'' +
                ", chkStatus='" + chkStatus + '\'' +
                ", refundFlag='" + refundFlag + '\'' +
                ", deleteFlag='" + deleteFlag + '\'' +
                ", acceptSrvIp='" + acceptSrvIp + '\'' +
                ", authorizationCode='" + authorizationCode + '\'' +
                ", payUserName='" + payUserName + '\'' +
                ", payUserMobile='" + LogFilter.encryptStr(payUserMobile) + '\'' +
                ", oldPayTransNo='" + oldPayTransNo + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", ppFlag='" + ppFlag + '\'' +
                ", cdFlag='" + cdFlag + '\'' +
                ", bankAccId='" + LogFilter.encryptStr(bankAccId) + '\'' +
                ", bankAccPwd='" + "********" + '\'' +
                ", phone='" + LogFilter.encryptStr(phone) + '\'' +
                ", headBank='" + headBank + '\'' +
                ", branchBank='" + branchBank + '\'' +
                ", subBank='" + subBank + '\'' +
                ", validDate='" + validDate + '\'' +
                ", cvv2='"+ "***" + '\'' +
                ", certType='" + certType + '\'' +
                ", certCode='" + StringUtil.hideCertNo(certCode) + '\'' +
                ", certName='" + StringUtil.hideName(certName) + '\'' +
                ", digest='" + digest + '\'' +
                ", paySettleDate=" + paySettleDate +
                '}';
    }
}
