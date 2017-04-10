package com.oriental.manage.service.transaction;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.transaction.PayTrans;

import java.util.List;
import java.util.Map;

/**
 * User: hj
 * Date: 2016/6/8 15:37
 * Desc:
 */
public interface IPayTransService {

    void queryPage(Pagination<PayTrans> pagination, PayTrans payTrans);

    List<PayTrans>queryPageDown(PayTrans payTrans);

    Map summaryPayTrans(PayTrans payTrans);

    PayTrans queryPayTransDetail(PayTrans payTrans);

    List<PayTrans> selectRecordByOurTransNo(PayTrans payTrans);
}
