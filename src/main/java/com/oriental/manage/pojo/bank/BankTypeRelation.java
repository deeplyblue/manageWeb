package com.oriental.manage.pojo.bank;


import com.oriental.manage.pojo.base.BaseModel;

/**
 * 银行类型和银行代码关系
 * @author 黄军
 *
 * 2013-9-8 下午4:28:35
 */
public class BankTypeRelation extends BaseModel {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String bankTypeCode;

    private String bankCode;

    private String bankCodeSuffix;
    
    private String createTime;
    
    private String lastUpdTime;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBankCodeSuffix() {
		return bankCodeSuffix;
	}

	public void setBankCodeSuffix(String bankCodeSuffix) {
		this.bankCodeSuffix = bankCodeSuffix;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(String lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public String getBankTypeCode() {
        return bankTypeCode;
    }

    public void setBankTypeCode(String bankTypeCode) {
        this.bankTypeCode = bankTypeCode == null ? null : bankTypeCode.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }
}