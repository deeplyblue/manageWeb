package com.oriental.manage.service.business.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.business.BusiChanStrategyCfgMapper;
import com.oriental.manage.pojo.business.BusiChanStrategyCfg;
import com.oriental.manage.service.business.IBusiChanStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/25.
 * 业务机构路由配置信息
 */
@Service
public class BusiChanStrategyImpl implements IBusiChanStrategyService {

    @Autowired
     private BusiChanStrategyCfgMapper busiChanStrategyCfgMapper;

    @Override
    public void queryPage(Pagination<BusiChanStrategyCfg> pagination, BusiChanStrategyCfg busiChanStrategyCfg) {
        List<BusiChanStrategyCfg> list=busiChanStrategyCfgMapper.queryPage(busiChanStrategyCfg);
        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }

    }

    @Override
    public void addBusiChanStrategyService(ResponseDTO<String> responseDTO, BusiChanStrategyCfg busiChanStrategyCfg) {

        if(busiChanStrategyCfgMapper.insertBusiChanStrategyCfg(busiChanStrategyCfg)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateBusiChanStrategy(ResponseDTO<String> responseDTO, BusiChanStrategyCfg busiChanStrategyCfg) {

        if(busiChanStrategyCfgMapper.updateBusiChanStrategyCfg(busiChanStrategyCfg)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void deleteBusiChanStrategy(ResponseDTO<String> responseDTO, BusiChanStrategyCfg busiChanStrategyCfg) {
        if(busiChanStrategyCfgMapper.delete(busiChanStrategyCfg)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
}
