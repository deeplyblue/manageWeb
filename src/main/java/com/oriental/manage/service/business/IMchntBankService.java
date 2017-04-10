package com.oriental.manage.service.business;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.business.MchntBank;

/**
 * Created by wangjun on 2016/5/24.
 */
public interface IMchntBankService {


    void queryPage (Pagination<MchntBank> pagination, MchntBank mchntBank);


}
