package com.oriental.manage.service.bank;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.bank.BankInfo;

import java.util.List;


/**
 * 银行信息实现类
 * @author 黄军
 *
 * 2013-3-7 下午1:54:21
 */
public interface IBankInfoService {
	/**
	 * 得到所有的银行信息
	 */
	public List<BankInfo> getAllBankInfo() throws Exception;
	/**
	 * 增加银行信息
	 */
	public boolean addBankInfo(BankInfo bean) throws Exception;
	/**
	 * 更新银行信息
	 */
	public boolean updateBankInfo(BankInfo bean)throws Exception;
	/**
	 * 得到机构已配置的银行信息
	 */
	public List<BankInfo> getOrgBankInfo() throws Exception;
	
	/**
	 * 获取银行信息
	 */
	public BankInfo getModels(String bankCode) throws Exception;
	
	/**
	 * 获取银行信息
	 */
	public BankInfo getByParams(BankInfo bean) throws Exception;

	void queryPage (Pagination<BankInfo> pagination, BankInfo bankInfo);

	void addBnakInfo(ResponseDTO<String> responseDTO, BankInfo bankInfo);

	void updateBankInfo(ResponseDTO<String> responseDTO, BankInfo bankInfo);

	void deleteBankInfo(ResponseDTO<String> responseDTO, BankInfo bankInfo);
}
