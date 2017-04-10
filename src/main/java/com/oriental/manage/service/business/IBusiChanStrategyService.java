package com.oriental.manage.service.business;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.business.BusiChanStrategyCfg;

/**
 * Created by wangjun on 2016/5/25.
 */
public interface IBusiChanStrategyService {

    void queryPage(Pagination<BusiChanStrategyCfg> pagination, BusiChanStrategyCfg busiChanStrategyCfg);

    void addBusiChanStrategyService(ResponseDTO<String> responseDTO, BusiChanStrategyCfg busiChanStrategyCfg);

    void updateBusiChanStrategy(ResponseDTO<String> responseDTO, BusiChanStrategyCfg busiChanStrategyCfg);

    void deleteBusiChanStrategy(ResponseDTO<String> responseDTO, BusiChanStrategyCfg busiChanStrategyCfg);
}
