package com.oriental.manage.core.utils;


import com.oriental.manage.core.system.Constants;
import com.oriental.manage.pojo.bank.BankInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

/**
 * 银行代码处理类
 * @author 黄军
 *
 * 2013-8-30 上午10:23:27
 */
public class BankCodeUtils {
	private static Logger LOG = LoggerFactory.getLogger(BankCodeUtils.class);
	
	/**
	 * 得到银行代码
	 * @param bankCode	银行代码
	 * @param bankType	银行类型
	 * @param appendFlag	是否是增加标识，true:增加，false:去掉
	 * @return
	 */
	public static String getBankCode(String bankCode,String bankType,boolean appendFlag){
		if(Constants.getBankCodeRule().containsKey(bankType)){
			if(appendFlag){
				bankCode = bankCode + Constants.getBankCodeRule().get(bankType);
			}else{
				int index = bankCode.lastIndexOf(Constants.getBankCodeRule().get(bankType));
				bankCode = bankCode.substring(0, index);
			}
		}else{
			LOG.error("银行类型不正确");
		}
		return bankCode;
	}
	/**
	 * 得到银行类型
	 * @param bankCode 银行代码
	 * @return
	 */
	public static String getBankType(String bankCode){
		String result = "";
		Set<Entry<String, String>> set = Constants.getBankCodeRule().entrySet();
		String key = null;
		String value = null;
		for (Iterator<Entry<String, String>> iterator = set.iterator(); iterator.hasNext();) {
			Entry<String, String> obj = (Entry<String, String>) iterator.next();
			key = obj.getKey();
			value = obj.getValue();
			if(bankCode.endsWith(value)){
				result = key;
				break;
			}
		}
		return result;
	}
	/**
	 * 得到银行卡类型
	 * @param bankType  银行类型
	 * @return
	 */
	public static String getBankCardType(String bankType){
		String result = "";
		if(bankType.endsWith("_C")){
			result = "01";
		}else if(bankType.endsWith("_D")){
			result = "02";
		}
		return result;
	}
	/**
	 * 得到银行类型的集合
	 * @param bankInfoList
	 * @return
	 */
	public static Map<String,List<BankInfo>> getBankMap(List<BankInfo> bankInfoList){
		Map<String,List<BankInfo>> map = new HashMap<String,List<BankInfo>>();
		String bankCode = null;
		String bankType = null;
		for (int i = 0; i < bankInfoList.size(); i++) {
			BankInfo bankInfo = bankInfoList.get(i);
			bankCode = bankInfo.getBankCode();
			bankType = getBankType(bankCode);
			
			List<BankInfo> banks = null;
			if(map.containsKey(bankType)){
				banks = map.get(bankType);
			}else{
				banks = new ArrayList<BankInfo>();
			}
			banks.add(bankInfo);
			map.put(bankType, banks);
		}
		return map;
	}

	public static List<BankInfo> getBankInfoByBankType(Map<String,List<BankInfo>> bankMap,String bankType){
		List<BankInfo> list = new ArrayList<BankInfo>();
		for (int i = 0; i < Constants.getBankTypeRelationList().size(); i++) {
			if(Constants.getBankTypeRelationList().get(i).getBankTypeCode().equals(bankType)){
				List<BankInfo> listBank = bankMap.get(Constants.getBankTypeRelationList().get(i).getBankCode());
				if(listBank != null){
					list.addAll(listBank);
				}
			}
		}
		return list;
	}
}
