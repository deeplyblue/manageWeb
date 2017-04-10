package com.oriental.manage.service.merchant.itconfig;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.itconfig.MerchantInterface;

/**
 * Author: Yang xp
 * Date: 2016/5/30
 * Time: 13:29
 * Desc：商户接口配置
 */
public interface IMerchantInterfaceService {

    void search(Pagination<MerchantInterface> pagination,MerchantInterface merchantInterface);

    void update(MerchantInterface merchantInterface,ResponseDTO<String> responseDTO);

    void add(MerchantInterface merchantInterface,ResponseDTO<String> responseDTO);
}
