package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.RandomMath;
import com.oriental.manage.dao.institution.PayChanStrategyCfgMapper;
import com.oriental.manage.pojo.institution.PayChanStrategyCfg;
import com.oriental.manage.service.institution.IPayChanStrategyCfgService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 2016/6/14.
 */
@Service
public class PayChanStrategyCfgImpl implements IPayChanStrategyCfgService {

    @Autowired
    private PayChanStrategyCfgMapper payChanStrategyCfgMapper;

    @Override
    public void queryPage(Pagination<PayChanStrategyCfg> pagination, PayChanStrategyCfg payChanStrategyCfg) {
        List<PayChanStrategyCfg> list = payChanStrategyCfgMapper.queryPage(payChanStrategyCfg);
        if (list != null && list.size() > 0) {
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public void addPayChan(ResponseDTO<String> responseDTO, PayChanStrategyCfg payChanStrategyCfg) {

        if (payChanStrategyCfgMapper.insertPayChan(payChanStrategyCfg) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }


    }

    @Override
    public void updatePayChan(ResponseDTO<String> responseDTO, PayChanStrategyCfg payChanStrategyCfg) {

    }

    @Override
    public void deletePayChan(ResponseDTO<String> responseDTO, PayChanStrategyCfg payChanStrategyCfg) {

    }

    @Override
    public void PayChanStrategyCfg(ResponseDTO responseDTO, List<PayChanStrategyCfg> list) {
        PayChanStrategyCfg payChanStrategyCfg = new PayChanStrategyCfg();
        payChanStrategyCfg.setBankCode(list.get(0).getBankCode());
        payChanStrategyCfg.setTransCode(list.get(0).getTransCode());
        payChanStrategyCfg.setConnChannel(list.get(0).getConnChannel());
        payChanStrategyCfgMapper.deletePayChanByBankCode(payChanStrategyCfg);
        for (PayChanStrategyCfg cfg : list) {
            cfg.setId(DateUtils.now() + RandomMath.getNum(5));
            payChanStrategyCfgMapper.insertPayChan(cfg);
        }
    }
}
