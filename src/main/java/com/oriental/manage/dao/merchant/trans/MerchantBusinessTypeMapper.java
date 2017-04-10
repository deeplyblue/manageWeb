package com.oriental.manage.dao.merchant.trans;

import com.oriental.manage.pojo.merchant.trans.MerchantBusinessType;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/23
 * Time: 19:36
 * Desc：商户业务资金源
 */
public interface MerchantBusinessTypeMapper {

    List<MerchantBusinessType> searchMerchantBusinessType(MerchantBusinessType merchantBusinessType);


    int addMerchantBusinessType(MerchantBusinessType merchantBusinessType);


    int updateMerchantBusinessType(MerchantBusinessType merchantBusinessType);

}
