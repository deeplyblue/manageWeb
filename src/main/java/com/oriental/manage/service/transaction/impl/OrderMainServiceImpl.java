package com.oriental.manage.service.transaction.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.utils.BigDecimalUtils;
import com.oriental.manage.dao.transaction.OrderMainMapper;
import com.oriental.manage.pojo.transaction.OrderMain;
import com.oriental.manage.service.transaction.IOrderMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * User: hj
 * Date: 2016/6/8 15:38
 * Desc:
 */
@Slf4j
@Service
public class OrderMainServiceImpl implements IOrderMainService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OrderMainMapper orderMainMapper;

    @Override
    public void queryPage(Pagination<OrderMain> pagination, OrderMain orderMain) {
        List<OrderMain> list = orderMainMapper.queryPage(orderMain);
        if (null != list && list.size() > 0) {
            for (OrderMain orderMain1 : list) {
                orderMain1.setTotalAmt(BigDecimalUtils.changeF2Y(orderMain1.getTotalAmt()));
            }
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public List<OrderMain> queryPageDownload(OrderMain orderMain) {
        return orderMainMapper.queryPageDownload(orderMain);
    }
    public Map summaryOrderMain(OrderMain orderMain) {
        Map map = orderMainMapper.summaryOrderMain(orderMain);
        if (null == map.get("sumTotalAmt")) {
            map.put("sumTotalAmt", "0");
        } else {
            map.put("sumTotalAmt", BigDecimalUtils.changeF2Y((BigDecimal) map.get("sumTotalAmt")));
        }
        return map;
    }

    @Override
    public OrderMain queryOrderMainDetail(OrderMain orderMain) {
        OrderMain resultOrderMain = orderMainMapper.queryOrderMainDetail(orderMain);
        if (null != resultOrderMain) {
            resultOrderMain.setTotalAmt(BigDecimalUtils.changeF2Y(resultOrderMain.getTotalAmt()));
        }
        return resultOrderMain;
    }

    @Override
    public OrderMain queryOrderMainByOurTransNo(OrderMain orderMainQuery) {
        return orderMainMapper.queryOrderMainDetail(orderMainQuery);

    }
}
