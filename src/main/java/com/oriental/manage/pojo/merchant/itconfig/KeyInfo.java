package com.oriental.manage.pojo.merchant.itconfig;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Author: Yang xp
 * Date: 2016/5/30
 * Time: 18:08
 * Desc：密钥配置
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KeyInfo extends BaseModel {


    /**
     *
     */
    private String id;

    /**
     * 代码类型,商户或机构:
     */
    private String companyType;

    /**
     * 商户或机构代码
     */
    private String companyCode;

    /**
     *交易密码
     */
    private String transPwd;

    /**
     *交易密码状态:00:初始01:有效02:过期03:销毁
     */
    private String transPwdStatus;

    /**
     *交易密码有效期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date transPwdValidDate;

    /**
     *交易密码更新日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date transPwdUpdDate;

    /**
     *数据加密KEY
     */
    private String dataKey;

    /**
     *数据加密KEY状态:00:初始01:有效02:过期03:销毁
     */
    private String dataKeyStatus;

    /**
     *数据加密KEY有效期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataKeyValidDate;

    /**
     *数据加密KEY更新日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataKeyUpdDate;

    /**
     *RSA公钥（明文）
     */
    private String rsaPublicKey;

    /**
     *RSA私钥（密文）
     */
    private String rsaPrivateKey;

    /**
     *RSA密钥对状态:00:初始01:有效02:过期03:销毁
     */
    private String rsaKeyStatus;

    /**
     *RSA密钥对有效期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rsaKeyValidDate;

    /**
     *RSA密钥对更新日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rsaKeyUpdDate;

    /**
     *记录生成日期
     */
    private Date createTime;

    /**
     *记录最后更新日期
     */
    private Date lastUpdTime;

    /**
     *
     */
    private String issh;

    /**
     *商户公钥文件地址
     */
    private String rsaRemoteAddr;

    /**
     * 信息验证类型：1：mac校验(md5对称加/解密)；9: CA(使用证书签名/延签)
     */
    private String encType;
}
