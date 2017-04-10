package com.oriental.manage.pojo.institution;

import com.oriental.manage.pojo.bank.BankInfo;
import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 支付通道策略
 * 2016/6/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayChanStrategyCfg extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**编号**/
	private String id;
	
	/**机构代码**/
	private String orgCode;
	
	/**策略优先级,数值最低，级别最高(最高,高,普通,低,最低)**/
	private String stgyPriority;
	
	/**记录生成日期**/
	private Date createTime;
	
	/**记录最后更新日期**/
	private Date lastUpdTime;
	
	/**dict:comm交易代码:**/
	private String transCode;
	
	/**dict:comm接入渠道:**/
	private String connChannel;
	
	/**银行代码**/
	private String bankCode;

	/**状态**/
	private String status;

	private BankInfo bankInfo;
}
