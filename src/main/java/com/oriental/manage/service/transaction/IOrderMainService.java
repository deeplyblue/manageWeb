package com.oriental.manage.service.transaction;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.transaction.OrderMain;

import java.util.Map;
import java.util.List;

/**
 * User: hj
 * Date: 2016/6/8 15:37
 * Desc:
 */
public interface IOrderMainService {

    void queryPage(Pagination<OrderMain> pagination, OrderMain orderMain);

    Map summaryOrderMain(OrderMain orderMain);
    List<OrderMain> queryPageDownload(OrderMain orderMain);

    OrderMain queryOrderMainDetail(OrderMain orderMain);


    OrderMain queryOrderMainByOurTransNo(OrderMain orderMainQuery);
}
