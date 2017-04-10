package com.oriental.manage.dao.institution;

import com.oriental.manage.pojo.institution.PayChanStrategyCfg;

import java.util.List;


/**
 * Created by wangjun on 2016/6/14.
 * 支付策略
 */
public interface PayChanStrategyCfgMapper {

    int insertPayChan(PayChanStrategyCfg payChanStrategyCfg);

    int deletePayChanByBankCode(PayChanStrategyCfg payChanStrategyCfg);

    int updatePayChan(PayChanStrategyCfg payChanStrategyCfg);

    List<PayChanStrategyCfg> queryPage (PayChanStrategyCfg payChanStrategyCfg);
}
