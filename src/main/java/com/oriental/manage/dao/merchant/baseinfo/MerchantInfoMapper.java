package com.oriental.manage.dao.merchant.baseinfo;

import com.oriental.manage.pojo.base.BaseModel;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantInfo;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/19
 * Time: 11:05
 * Desc：商户信息
 */
public interface MerchantInfoMapper {

    int insertMerchantInfo(MerchantInfo merchantInfo);

    List<MerchantInfo> queryPage(MerchantInfo merchantInfo);

    int updateMerchantInfo(MerchantInfo merchantInfo);

    int updateMerchantInfoByStatus(MerchantInfo merchantInfo);

    List<MerchantInfo> check(MerchantInfo merchantInfo);

}
