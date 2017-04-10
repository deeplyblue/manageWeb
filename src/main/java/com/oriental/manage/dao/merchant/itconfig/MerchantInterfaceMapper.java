package com.oriental.manage.dao.merchant.itconfig;

import com.oriental.manage.pojo.merchant.itconfig.MerchantInterface;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/30
 * Time: 13:33
 * Desc：商户支付接口配置
 */
public interface MerchantInterfaceMapper {

    List<MerchantInterface> queryMerchantInterface(MerchantInterface merchantInterface);

    int updateMerchantInterface(MerchantInterface merchantInterface);

    int createMerchantInterface(MerchantInterface merchantInterface);
}
