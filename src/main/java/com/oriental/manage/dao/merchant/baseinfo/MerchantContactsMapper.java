package com.oriental.manage.dao.merchant.baseinfo;

import com.oriental.manage.pojo.merchant.baseinfo.MerchantContactInfo;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/23
 * Time: 10:01
 * Desc：商户联系人
 */
public interface MerchantContactsMapper {

    List<MerchantContactInfo> queryPage(MerchantContactInfo merchantContactInfo);

    int createMerchantContactInfo(MerchantContactInfo merchantContactInfo);

    int updateMerchantContactInfo(MerchantContactInfo merchantContactInfo);
}
