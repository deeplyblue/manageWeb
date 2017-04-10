package com.oriental.manage.service.institution;


import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.business.MchntBank;
import com.oriental.manage.pojo.institution.OrgBank;

import java.util.List;
import java.util.Map;

/**
 * 机构银行配置实现类
 * @author 未知
 * @author 蒯越 2015-02-25 优化格式
 */
public interface IOrgBankService {

	/**
	 * 增加机构银行
	 */
	boolean addOrgBank(OrgBank bean);

	/**
	 * 更新机构银行
	 */
	boolean updateOrgBank(OrgBank bean);

    /**
     * 更新机构银行配置
     */
    boolean updatePhoneCfg(OrgBank bean, Map<String, OrgBank> oldMap) throws Exception;

	/**
	 * 通过银行代码得到机构
	 */
	List<OrgBank> getOrgBankByBankCode(OrgBank bean) throws Exception;

	/**
	 * 通过机构代码得到机构银行配置
	 */
	List<OrgBank> getOrgBankByKey(OrgBank bean) throws Exception;

	/**
	 * 通过商户代码得到商户对应的外系统银行配置
	 */
	List<OrgBank> getOrgBankByMchntCode(MchntBank bean) throws Exception;
	
	/**
	 * 通过渠道得到该渠道下面所有的银行信息
    */
	List<OrgBank> getBankInfoByConnChannel(OrgBank bean) throws Exception;

	/**
	 * 查询机构银行关系，用于支付策略配置
	 */
    List<OrgBank> getBankInfoAndOrgCode(OrgBank bean) throws Exception;

	/**
	 * 通过支付机构代码、银行代码、银行类型 (定位唯一机构银行)
	 */
	OrgBank getOrgBankByCodeAndType(OrgBank orgBank) throws Exception;

    /**
     * 根据机构代码获取原机构银行payLvl,phoneLvl相关配置
     */
    Map<String,OrgBank> getOriginOrgBankConfig(String orgCode, String[] bankCodes) throws Exception;

	void updateEnableFlag(ResponseDTO<String> responseDTO,OrgBank orgBank);

	List<OrgBank> getAll();

	List<OrgBank> getBankInfoSelective(OrgBank orgBank);
}
