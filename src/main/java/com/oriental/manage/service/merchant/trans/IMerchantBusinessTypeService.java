package com.oriental.manage.service.merchant.trans;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.trans.MerchantBusinessType;

/**
 * Author: Yang xp
 * Date: 2016/5/23
 * Time: 19:29
 * Desc：商户业务资金源
 */
public interface IMerchantBusinessTypeService {

    void queryPage(Pagination<MerchantBusinessType> pagination,MerchantBusinessType merchantBusinessType);

    void addMerchantBusinessType(MerchantBusinessType merchantBusinessType, ResponseDTO<String> responseDTO);

    void updateMerchantBusinessType(MerchantBusinessType merchantBusinessType, ResponseDTO<String> responseDTO);
}
