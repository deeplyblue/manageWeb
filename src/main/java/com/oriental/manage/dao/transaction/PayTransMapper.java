package com.oriental.manage.dao.transaction;

import com.oriental.manage.pojo.transaction.PayTrans;

import java.util.List;
import java.util.Map;

/**
 * User: hj
 * Date: 2016/6/8 15:40
 * Desc:
 */
public interface PayTransMapper {

    List<PayTrans> queryPage(PayTrans payTrans);

    Map summaryPayTrans(PayTrans payTrans);

    PayTrans queryOrderMainDetail(PayTrans payTrans);

    List<PayTrans> queryPay(PayTrans payTrans);

    List<PayTrans> selectRecordByOurTransNo(PayTrans record);
}
