package com.oriental.manage.service.transaction;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.transaction.BizTrans;

/**
 * Created by wangjun on 2016/7/6.
 */
public interface IBusiTransService {

    void queryPage(Pagination<BizTrans> pagination, BizTrans bizTrans);
}
