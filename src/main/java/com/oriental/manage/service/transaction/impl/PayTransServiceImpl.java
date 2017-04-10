package com.oriental.manage.service.transaction.impl;

import com.oriental.manage.core.desUtils.CryptTool;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.utils.BigDecimalUtils;
import com.oriental.manage.core.utils.FieldUtils;
import com.oriental.manage.dao.transaction.PayTransMapper;
import com.oriental.manage.pojo.transaction.PayTrans;
import com.oriental.manage.service.transaction.IPayTransService;
import com.oriental.paycenter.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
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
public class PayTransServiceImpl implements IPayTransService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private PayTransMapper payTransMapper;

    @Override
    public void queryPage(Pagination<PayTrans> pagination, PayTrans payTrans) {
        SecretKey key = null;
        String certCode = null;
        String userMobile = null;
        try {
            key = CryptTool.genDESKey();
            if (payTrans.getCertCode() != null && !"".equals(payTrans.getCertCode())) {
                certCode = CryptTool.des3Base64Enc(key, payTrans.getCertCode());
                payTrans.setCertCode(certCode);
            }
            if (payTrans.getPayUserMobile() != null && !"".equals(payTrans.getPayUserMobile())) {
                userMobile = CryptTool.des3Base64Enc(key, payTrans.getPayUserMobile());
                payTrans.setPayUserMobile(userMobile);
            }
            List<PayTrans> list = payTransMapper.queryPage(payTrans);
            if (null != list && list.size() > 0) {
                for (PayTrans payTrans1 : list) {
                    payTrans1.setTransAmt(BigDecimalUtils.changeF2Y(payTrans1.getTransAmt()));
                }
                pagination.setList(list);
                pagination.setRowCount(list.get(0).getRowCount());
            }
        } catch (Exception e) {
            log.error("支付交易查询失败", e);
            throw new BusiException("支付交易分页查询失败");
        }
    }

    @Override
    public List<PayTrans> queryPageDown(PayTrans payTrans) {
        return payTransMapper.queryPage(payTrans);
    }

    @Override
    public Map summaryPayTrans(PayTrans payTrans) {
        Map map = payTransMapper.summaryPayTrans(payTrans);
        if (null == map.get("sumTotalAmt")) {
            map.put("sumTotalAmt", "0");
        } else {
            map.put("sumTotalAmt", BigDecimalUtils.changeF2Y((BigDecimal) map.get("sumTotalAmt")));
        }
        return map;
    }

    @Override
    public PayTrans queryPayTransDetail(PayTrans payTrans) {
        try {
            PayTrans resultPayTrans = payTransMapper.queryOrderMainDetail(payTrans);
            if (null != resultPayTrans) {
                SecretKey key = CryptTool.genDESKey();
                resultPayTrans.setTransAmt(BigDecimalUtils.changeF2Y(resultPayTrans.getTransAmt()));
                //解密&脱敏
                resultPayTrans.setBankAccId(FieldUtils.hideBankCardNo(CryptTool.des3Base64Dec(key, resultPayTrans.getBankAccId())));
                resultPayTrans.setPhone(FieldUtils.hidePhone(CryptTool.des3Base64Dec(key, resultPayTrans.getPhone())));
                resultPayTrans.setCertName(FieldUtils.hideName(CryptTool.des3Base64Dec(key, resultPayTrans.getCertName())));
            }
            return resultPayTrans;
        } catch (Exception e) {
            log.error("查询支付明细异常", e);
            throw new BizException("查询支付明细异常");
        }
    }

    @Override
    public List<PayTrans> selectRecordByOurTransNo(PayTrans payTrans) {
        PayTrans record = new PayTrans();
        record.setOurTransNo(payTrans.getOurTransNo());
        return payTransMapper.selectRecordByOurTransNo(record);
    }
}
