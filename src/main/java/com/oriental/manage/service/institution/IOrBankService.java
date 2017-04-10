package com.oriental.manage.service.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.institution.OrgBank;

/**
 * Created by wangjun on 2016/5/9.
 */
public interface IOrBankService {

    //查询机构银行信息
    void queryPage (Pagination<OrgBank>pagination, OrgBank orgBank);

    void queryPageByStrategy(Pagination<OrgBank>pagination, OrgBank orgBank);
}
