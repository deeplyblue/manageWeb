package com.oriental.manage.service.transaction;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.transaction.BizTrans;

import java.util.List;
import java.util.Map;

/**
 * User: hj
 * Date: 2016/6/13 15:37
 * Desc:
 */
public interface IBizTransService {

    void queryPage(Pagination<BizTrans> pagination, BizTrans bizTrans);

    List<BizTrans> queryPageDown(BizTrans bizTrans);

    Map summaryBizTrans(BizTrans bizTrans);

    BizTrans queryBizTransDetail(BizTrans bizTrans);

    BizTrans queryBizTransByOurTransNo(BizTrans bizTransQuery);
}
