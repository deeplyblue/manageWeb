package com.oriental.manage.dao.transaction;

import com.oriental.manage.pojo.transaction.BizTrans;

import java.util.List;
import java.util.Map;

/**
 * User: hj
 * Date: 2016/6/14 20:40
 * Desc:
 */
public interface BizTransMapper {

    List<BizTrans> queryPage(BizTrans bizTrans);

    Map summaryBizTrans(BizTrans bizTrans);

    BizTrans queryBizTransDetail(BizTrans bizTrans);

    BizTrans queryBizTrans(BizTrans bizTrans);
}
