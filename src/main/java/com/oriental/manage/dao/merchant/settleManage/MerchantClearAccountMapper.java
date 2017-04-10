package com.oriental.manage.dao.merchant.settleManage;

import com.oriental.manage.pojo.merchant.settleManage.ClearAccount;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/31
 * Time: 16:11
 * Desc：商户账户配置
 */
public interface MerchantClearAccountMapper {

    List<ClearAccount> queryClearAccount(ClearAccount clearAccount);

    int addClearAccount(ClearAccount clearAccount);

    int updateClearAccount(ClearAccount clearAccount);
}
