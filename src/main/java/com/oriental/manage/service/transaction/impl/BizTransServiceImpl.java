package com.oriental.manage.service.transaction.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.utils.BigDecimalUtils;
import com.oriental.manage.dao.transaction.BizTransMapper;
import com.oriental.manage.pojo.transaction.BizTrans;
import com.oriental.manage.service.transaction.IBizTransService;
import com.oriental.paycenter.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * User: hj
 * Date: 2016/6/13 15:38
 * Desc:
 */
@Slf4j
@Service
public class BizTransServiceImpl implements IBizTransService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BizTransMapper bizTransMapper;

    @Override
    public void queryPage(Pagination<BizTrans> pagination, BizTrans BizTrans) {
        List<BizTrans> list = bizTransMapper.queryPage(BizTrans);
        if (null != list && list.size() > 0) {
            for (BizTrans bizTrans1 : list) {
                bizTrans1.setTransAmt(BigDecimalUtils.changeF2Y(bizTrans1.getTransAmt()));
            }
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public List<BizTrans> queryPageDown(BizTrans bizTrans) {
        return bizTransMapper.queryPage(bizTrans);
    }

    @Override
    public Map summaryBizTrans(BizTrans bizTrans){
        Map map = bizTransMapper.summaryBizTrans(bizTrans);
        if (null == map.get("sumTotalAmt")) {
            map.put("sumTotalAmt", "0");
        } else {
            map.put("sumTotalAmt", BigDecimalUtils.changeF2Y((BigDecimal) map.get("sumTotalAmt")));
        }
        return map;
    }

    @Override
    public BizTrans queryBizTransDetail(BizTrans BizTrans) {
        try {
            BizTrans resultBizTrans = bizTransMapper.queryBizTransDetail(BizTrans);
            if (null != resultBizTrans) {
                resultBizTrans.setTransAmt(BigDecimalUtils.changeF2Y(resultBizTrans.getTransAmt()));
            }
            return resultBizTrans;
        } catch (Exception e) {
            log.error("查询业务付明细异常", e);
            throw new BizException("查询业务明细异常");
        }
    }

    @Override
    public BizTrans queryBizTransByOurTransNo(BizTrans bizTransQuery) {
        return bizTransMapper.queryBizTrans(bizTransQuery);
    }
}
