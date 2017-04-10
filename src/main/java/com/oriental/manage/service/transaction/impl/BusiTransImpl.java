package com.oriental.manage.service.transaction.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.utils.BigDecimalUtils;
import com.oriental.manage.dao.transaction.BizTransMapper;
import com.oriental.manage.dao.transaction.PayTransMapper;
import com.oriental.manage.pojo.transaction.BizTrans;
import com.oriental.manage.pojo.transaction.PayTrans;
import com.oriental.manage.service.transaction.IBusiTransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/7/6.
 */
@Slf4j
@Service
public class BusiTransImpl implements IBusiTransService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BizTransMapper bizTransMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private PayTransMapper payTransMapper;

    @Override
    public void queryPage(Pagination<BizTrans> pagination, BizTrans bizTrans) {
        List<BizTrans> list = bizTransMapper.queryPage(bizTrans);

        PayTrans payTransQuery = new PayTrans();
        for (BizTrans biz : list) {
            payTransQuery.setOrderNo(biz.getOrderNo());
            payTransQuery.setDeleteFlag("0");
            List<PayTrans> listPay = payTransMapper.queryPay(payTransQuery);
//            基于目前支付、业务唯一
            if (listPay != null && listPay.size() > 0) {

                biz.setPayReqNo(listPay.get(0).getPayReqNo());
                biz.setPayRespNo(listPay.get(0).getPayRespNo());
                biz.setBankCode(listPay.get(0).getBankCode());
            }

            biz.setTransAmt(BigDecimalUtils.changeF2Y(biz.getTransAmt()));
        }

        pagination.setList(list);
        pagination.setRowCount(list.size() == 0 ? 0 : list.get(0).getRowCount());

    }
}
