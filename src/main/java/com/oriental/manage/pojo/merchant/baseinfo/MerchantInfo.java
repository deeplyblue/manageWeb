package com.oriental.manage.pojo.merchant.baseinfo;

import com.oriental.manage.pojo.base.BaseModel;
import com.oriental.manage.pojo.merchant.MailInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Yang xp
 * Date: 2016/5/19
 * Time: 9:58
 * Desc：商户信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class MerchantInfo extends BaseModel {


    private static final long serialVersionUID = 1L;

    /**
     * 商户代码
     */
    private String merchantCode;

    /**
     * 商户简称
     */
    private String merchantAbbrName;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商户类型码
     */
    private String mccCode;

    /**
     * 商户类型:<br/>
     * 01:集团内部商户<br/>
     * 02:集团外部商户<br/>
     * 03:省级内部商户<br/>
     * 04:省级外部商户<br/>
     */
    private String merchantType;

    /**
     * 商户状态:<br/>
     * A:初始<br/>
     * B:可用<br/>
     * C:关闭<br/>
     * D:审核通过<br/>
     */
    private String merchantStatus;

    /**
     * 商户地址
     */
    private String merchantAddress;
    
    /**
     * 商户邮编
     */
    private String merchantZipCode;

    /**
     * 商户传真
     */
    private String merchantFax;

    /**
     * 商户归属地
     */
    private String merchantArea;

    /**
     * 商户级别<br/>
     * 0顶级<br/>
     * 1一级<br/>
     */
    private String merchantLvl;

    /**
     * 上级机构
     */
    private String parentOrgCode;

    /**
     * 结算机构
     */
    private String clrOrgCode;

    /**
     * 商户URL地址
     */
    private String merchantUrl;

    /**
     * 商户电话接入号
     */
    private String merchantIvrNo;

    /**
     * 商户介绍
     */
    private String merchantIntro;
    
    /**
     * 优惠规则介绍
     */
    private String discountRuleIntro;

    /**
     * 商户LOGO
     */
    private String merchantLogo;

    /**
     * 撤销日期
     */
    private Date revokeDate;

    /**
     * 对账方式:<br/>
     * 01:自主对账<br/>
     * 02:非自主对账<br/>
     */
    private String chkMethod;

    /**
     * 删除标识:<br/>
     * 0:正常<br/>
     * 1:删除<br/>
     */
    private String deleteFlag;

    /**
     * 审核通过日期
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date auditDate;

    /**
     * 拓展方式:<br/>
     * 01:集团拓展<br/>
     * 02:省级拓展<br/>
     * 03:外部拓展<br/>
     */
    private String developMode;

    /**
     * 记录生成日期
     */
    private Date createTime;

    /**
     * 记录最后更新日期
     */
    private Date lastUpdTime;

    // TODO: 2016/5/19 类型先加代码后加
    /**
     * 代码类型,
     * 商户
     * 业务机构:
     * 支付机构
     */
    private String companyType;

    /**
     * 商户备注信息
     */
    private String merchantEtc;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 商户城市
     */
    private String cityCode;


    /**
     * 商户上线日期
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date merchantOnlineDate;

    /**
     * 对账文件模板编号
     */
    private String checkTemplateNo;

    /**
     * 银行联行号
     */
    private String cnapsCode;

    /**
     * 差异处理标志:
     * 0:调账
     * 1:退款
     */
    private String chkDealFlag;


    /**
     *
     */
    private MerchantContactInfo contactInfo;

    /**
     *
     */
    private MailInfo mailInfo;

    /**
     * 发展渠道
     */
    private String depChannelCode;

    /**
     * 主联系人名字
     */
    private String mainContactName;

    /**
     * 主联系人邮件
     */
    private String mainContactEmail;

    /**
     * 主联系人电话
     */
    private String mainContactPhone;

    /**
     * 主联系人手机
     */
    private String mainContactMobile;

    /**
     * 自动分配的用户名
     */
    private String loginName;

    /**
     * 是否自定义序列号:<br/>
     * 0:表示非自定义，由系统生成(控制商户授权号是否自定义)<br/>
     * 1:表示自定义<br/>
     */
    private String merchantIsdefined;


    /**
     * 交易限额
     */
    private BigDecimal transAmtLimit;

    /**
     * 银联快捷二级商户号
     */
    private String unionPayMerchantCode;

    /**
     * 开通人
     */
    private String openUser;

    /**
     * 开通时间
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date openDate;


    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 暂停付款标识:<br/>
     * 0:否<br/>
     * 1:是<br/>
     */
    private String suspendFlag;

    /**
     * 报表类型：<br/>
     * 01：正常 <br/>
     * 02：分账 <br/>
     * 03：积分 <br/>
     */
    private String reportType;

    /**
     * 服务电话
     */
    private String serviceTelephone;


    /**
     * 业务归属
     */
    private String businessBeLong;

    /**
     * 自定义发送邮箱
     */
    private String allEmail;

    // TODO: 2016/5/19 字段需要后定义类别
    /**
     * 商户来源:
     * 01:系统新增商户或老平台导入商户
     * 02:运营系统自助注册
     * 03:门户网签
     * 04：客户端商户自助注册
     */
    private String merchantSource;

    /**
     * 平台标识: <br/>
     * 1.管控平台 <br/>
     * 2.商户自服务平台<br/>
     */
    private String platformFlag;


    /**
     * CA证书申请状态：<br/>
     * 0：未申请 <br/>
     * 1：申请成功 <br/>
     * 2：申请失败<br/>
     */
    private String caApplyState;

    /**
     * CA证书申请邮箱
     */
    private String caApplyEmail;


    /**
     * 发送状态
     */
    private String sendResult;


    /**
     * 是否允许修改手机号： <br/>
     * 01：否<br/>
     * 02：是<br/>
     */
    private String alterMobileFlag;

    /**
     * 是否显示子商户信息： <br/>
     * 01：否 <br/>
     * 02：是<br/>
     */
    private String showChild;

    /**
     * 合同标志
     */
    private String contractFlag;

    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 税务登记证纳税人名称
     */
    private String taxpayerName;

    /**
     * 纳税人识别号
     */
    private String taxpayerNo;

    /**
     * 开始时间 临时不入库
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    /**
     * 结束时间 临时不入库
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String mchntMonType;

}
