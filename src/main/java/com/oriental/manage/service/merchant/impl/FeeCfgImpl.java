package com.oriental.manage.service.merchant.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.FeeCfgMapper;
import com.oriental.manage.pojo.merchant.FeeCfg;
import com.oriental.manage.service.merchant.IFeeCfgSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/16.
 */
@Service
public class FeeCfgImpl implements IFeeCfgSerivce {

    @Autowired
    private FeeCfgMapper feeCfgMapper;

    @Override
    public void queryPage(Pagination<FeeCfg> pagination, FeeCfg feeCfg){
        feeCfg.setCompanyType("02");

        List<FeeCfg> list=feeCfgMapper.queryPage(feeCfg);
        if (list!=null&&list.size()>0){
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);

        }
    }

    @Override
    public void addFeeCfg(FeeCfg feeCfg, ResponseDTO<String> responseDTO){
        if(feeCfgMapper.insertFeeCfg(feeCfg)>0){
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }
    @Override
    public  void deleteFeeCfg(FeeCfg feeCfg,ResponseDTO<String> responseDTO){
        if(feeCfgMapper.deleteFeeCfg(feeCfg)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }

    }
    @Override
    public void upadteFeeCfg(FeeCfg feeCfg,ResponseDTO<String> responseDTO){
        if(feeCfgMapper.updateFeeCfg(feeCfg)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
}
