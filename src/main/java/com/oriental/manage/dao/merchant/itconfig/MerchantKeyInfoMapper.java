package com.oriental.manage.dao.merchant.itconfig;

import com.oriental.manage.pojo.merchant.itconfig.KeyInfo;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/30
 * Time: 19:19
 * Desc：商户密钥
 */
public interface MerchantKeyInfoMapper {

    List<KeyInfo> queryMerchantKeyInfo(KeyInfo keyInfo);

    int createMerchantKeyInfo(KeyInfo keyInfo);

    int updateMerchantKeyInfo(KeyInfo keyInfo);

    KeyInfo queryRsaKey(KeyInfo keyInfo);

    KeyInfo queryDataKey(KeyInfo keyInfo);
}
