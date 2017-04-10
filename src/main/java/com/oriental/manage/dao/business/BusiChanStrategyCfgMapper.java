package com.oriental.manage.dao.business;

import com.oriental.manage.pojo.business.BusiChanStrategyCfg;

import java.util.List;

/**
 * Created by wangjun on 2016/5/25.
 */
public interface BusiChanStrategyCfgMapper {

    List<BusiChanStrategyCfg> queryPage(BusiChanStrategyCfg busiChanStrategyCfg);

    int insertBusiChanStrategyCfg(BusiChanStrategyCfg busiChanStrategyCfg);

    int updateBusiChanStrategyCfg(BusiChanStrategyCfg busiChanStrategyCfg);

    int delete(BusiChanStrategyCfg busiChanStrategyCfg);
}
