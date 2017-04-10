package com.oriental.manage.service.institution;

import com.oriental.manage.pojo.institution.PayChanStrategyCfg;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;

import java.util.List;

/**
 * Created by wangjun on 2016/6/14.
 * 支付策略
 */
public interface IPayChanStrategyCfgService {
    void queryPage(Pagination<PayChanStrategyCfg> pagination, PayChanStrategyCfg payChanStrategyCfg);

    void addPayChan(ResponseDTO<String> responseDTO, PayChanStrategyCfg payChanStrategyCfg);

    void updatePayChan(ResponseDTO<String> responseDTO, PayChanStrategyCfg payChanStrategyCfg);

    void deletePayChan(ResponseDTO<String> responseDTO, PayChanStrategyCfg payChanStrategyCfg);

    void PayChanStrategyCfg(ResponseDTO responseDTO, List<PayChanStrategyCfg> list);
}
