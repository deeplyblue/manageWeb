package com.oriental.manage.dao.transaction;

import com.oriental.manage.pojo.transaction.OrderMain;

import java.util.List;
import java.util.Map;

/**
 * User: hj
 * Date: 2016/6/8 15:40
 * Desc:
 */
public interface OrderMainMapper {

    List<OrderMain> queryPage(OrderMain orderMain);
    List<OrderMain> queryPageDownload(OrderMain orderMain);
    Map summaryOrderMain(OrderMain orderMain);

    OrderMain queryOrderMainDetail(OrderMain orderMain);
}
