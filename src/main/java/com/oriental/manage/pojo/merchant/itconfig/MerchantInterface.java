package com.oriental.manage.pojo.merchant.itconfig;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Author: Yang xp
 * Date: 2016/5/30
 * Time: 12:57
 * Desc：商户结算配置
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class MerchantInterface extends BaseModel {

    /**
     *
     */
    private String id;

    /**
     *商户代码
     */
    private String merchantCode;

    /**
     *商户WS地址
     */
    private String merchantWsUrl;

    /**
     *商户IP
     */
    private String merchantIp;

    /**
     *商户回调地址
     */
    private String merchantCallbackUrl;

    /**
     *商户电话接入号
     */
    private String merchantIvrNo;

    /**
     *商户证书路径
     */
    private String certPath;

    /**
     *记录生成日期
     */
    private Date createTime;

    /**
     *记录最后更新日期
     */
    private Date lastUpdTime;

    /**
     *商户退款IP
     */
    private String merchantRefundIp;

    /**
     *商户预授权地址
     */
    private String merchantPreauthUrl;

    /**
     *是否自动补单:0:不需要1:需要
     */
    private String isAutoCheckOrder;

    /**
     *域名
     */
    private String domainName;

    /**
     *商户退款通知地址
     */
    private String merchantRefundNotifyUrl;

    /**
     *是否校验IP:0:不校验1:校验
     */
    private String isIpVal;

    /**
     *类型
     */
    private String companyType;

    /**
     *业务内容
     */
    private String busRemark;

    /**
     *消费方式(01需本人实名消费 02 有实物配送 03 需验证付款人电话、地址)
     */
    private String consumptionPatterns;

    /**
     *客户端渠道接口，01：独立使用安全支付插件，02：内嵌东方金融公众版客户端
     */
    private String clientChannelInterface;

    /**
     *账单支付商品信息校验地址
     */
    private String merchantCheckUrl;

    /**
     *
     */
    private String merchantTransPwd;
    /**
     *
     */
    private String merchantTransKey;


    /**
     *
     */
    private String isRefundIpVal;



}
