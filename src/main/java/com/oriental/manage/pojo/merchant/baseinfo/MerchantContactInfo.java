package com.oriental.manage.pojo.merchant.baseinfo;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Author: Yang xp
 * Date: 2016/5/19
 * Time: 10:07
 * Desc：联系人
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class MerchantContactInfo extends BaseModel {

    /**
     *
     */
    private String id;

    /**
     * 联系人类型:<br/>
     * 00:主联系人<br/>
     * 01:技术联系人<br/>
     * 02:业务联系人<br/>
     * 03:财务联系人<br/>
     * 04:客服联系人<br/>
     * 07:申请人<br/>
     */
    private String cttType;

    /**
     * 联系人角色:<br/>
     * A角<br/>
     * B角<br/>
     * C角<br/>
     */
    private String cttRole;
    /**
     * 姓名
     */
    private String cttName;
    /**
     * 邮箱
     */
    private String cttEmail;
    /**
     * 座机号
     */
    private String cttPhone;
    /**
     * 手机号
     */
    private String cttMobile;
    /**
     * 传真
     */
    private String cttFax;
    /**
     * 联系人状态:<br/>
     * 0:可用<br/>
     * 1:不可用<br/>
     */
    private String
            cttStatus;

    /**
     * 代码类型,商户或机构:<br/>
     * 01:支付机构<br/>
     * 02:商户<br/>
     * 03:分润机构<br/>
     */
    private String companyType;
    /**
     * 商户或机构代码
     */
    private String companyCode;
    /**
     * 记录生成日期
     */
    private Date createTime;
    /**
     * 记录最后更新日期
     */
    private Date lastUpdTime;
    /**
     * 是否是主联系人
     */
    private String isMainContact;

    /**
     * 是否接收公告通知邮件
     */
    private String acceptNoticeEmailFlag;

}
