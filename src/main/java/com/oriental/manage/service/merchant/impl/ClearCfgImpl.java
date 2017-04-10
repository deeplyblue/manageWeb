package com.oriental.manage.service.merchant.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.ClearCfgMapper;
import com.oriental.manage.pojo.merchant.ClearCfg;
import com.oriental.manage.service.merchant.IClearCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/16.
 */
@Service
public class ClearCfgImpl implements IClearCfgService{
   @Autowired
   private ClearCfgMapper clearCfgMapper;

    @Override
   public void queryPage(Pagination<ClearCfg> pagination, ClearCfg clearCfg){
        clearCfg.setOrgType("02");
       List<ClearCfg> list=clearCfgMapper.queryPage(clearCfg);
       if(list!=null&&list.size()>0){
          pagination.setRowCount(list.get(0).getRowCount());
          pagination.setList(list);
       }
   }
   @Override
    public void addClearCfg(ResponseDTO<String> responseDTO, ClearCfg clearCfg){

       if(clearCfgMapper.insertClearCfg(clearCfg)>0){
           responseDTO.setSuccess(true);
       } else {
           responseDTO.setSuccess(false);
       }

    }
    @Override
    public void updateClearCfg(ResponseDTO<String> responseDTO, ClearCfg clearCfg){

        if(clearCfgMapper.updateClearCfg(clearCfg)>0){

            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }

    }
    @Override
    public void deleteClearCfg(ResponseDTO<String> responseDTO, ClearCfg clearCfg){

        if(clearCfgMapper.deleteOldCfg(clearCfg)>0){
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }
}
