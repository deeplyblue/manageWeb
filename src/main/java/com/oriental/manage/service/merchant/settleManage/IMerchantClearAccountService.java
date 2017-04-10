package com.oriental.manage.service.merchant.settleManage;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.settleManage.ClearAccount;

/**
 * Author: Yang xp
 * Date: 2016/5/31
 * Time: 16:35
 * Desc：商户账户配置
 */
public interface IMerchantClearAccountService {

    void searchMerchantClearAccount(Pagination<ClearAccount> pagination, ClearAccount clearAccount);

    void addMerchantClearAccount(ClearAccount clearAccount, ResponseDTO<String> responseDTO);


    void updateMerchantClearAccount(ClearAccount clearAccount, ResponseDTO<String> responseDTO);
}
