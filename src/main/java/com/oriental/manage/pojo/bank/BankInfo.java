package com.oriental.manage.pojo.bank;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 银行信息
 * @author 黄军 2013-03-06 创建
 * @author 蒯越 2015-02-27 使用Lombok
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BankInfo extends BaseModel {
    
	private String id;
	//银行代码
    private String bankCode;
	//银行名称
    private String bankName;
	//银行Logo
    private String bankLogo;
	//银行说明文件
    private String bankMemo;
	//备注
    private String spare1;
	//记录生成日期
    private Date createTime;
	//记录最后更新日期
    private Date lastUpdTime;
	//dict:comm银行类型:01:个人网银02:企业网银03:东方金融账户04:积点卡05:第三方支付
    private String bankType;
	//dict:tab卡类型:01:信用卡02:借记卡03:行业卡
	private String bankCardType;
	//状态
	private String enableFlag;
	// 自增列 商户、机构代码
	//自增列 商户、机构代码
	private String companyCode;
	//类型
	private String companyType;
	//老平台银行代码
	private String oldBankCode;
	//银行简称
	private String bankAbbrName;

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo == null ? null : bankLogo.trim();
    }

    public void setBankMemo(String bankMemo) {
        this.bankMemo = bankMemo == null ? null : bankMemo.trim();
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1 == null ? null : spare1.trim();
    }
}
