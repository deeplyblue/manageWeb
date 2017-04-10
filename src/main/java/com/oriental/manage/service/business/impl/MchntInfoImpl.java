package com.oriental.manage.service.business.impl;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.business.MchntInfoMapper;
import com.oriental.manage.pojo.business.MchntInfo;
import com.oriental.manage.service.business.IMchntInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/25.
 */
@Service
public class MchntInfoImpl implements IMchntInfoService {

    @Autowired
    private MchntInfoMapper mchntInfoMapper;

    @Override
  public  void queryPage(Pagination<MchntInfo> pagination, MchntInfo mchntInfo){

      List<MchntInfo> list=mchntInfoMapper.getAll(mchntInfo);

      if(list!=null&&list.size()>0){
          pagination.setRowCount(list.get(0).getRowCount());
          pagination.setList(list);
      }
    }

    @Override
    public void addMchntInfo(ResponseDTO<String> responseDTO, MchntInfo mchntInfo) {

        if (mchntInfoMapper.insertMchntInfo(mchntInfo)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateMchntInfo(ResponseDTO<String> responseDTO, MchntInfo mchntInfo) {
        if(mchntInfoMapper.updateMchntInfo(mchntInfo)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void deleteMchntInfo(ResponseDTO<String> responseDTO, MchntInfo mchntInfo) {
        if(mchntInfoMapper.deleteMchntInfo(mchntInfo)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
}
