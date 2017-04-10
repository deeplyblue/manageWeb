package com.oriental.manage.service.selfservice.Impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.selfservice.RespCodeMapper;
import com.oriental.manage.pojo.selfservice.RespCode;
import com.oriental.manage.service.selfservice.IRespCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/7/20.
 */
@Slf4j
@Service
public class RespCodeImpl implements IRespCodeService{

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RespCodeMapper respCodeMapper;

    @Override
    public void queryPage(Pagination<RespCode> pagination, RespCode respCode) {
        List<RespCode> list=respCodeMapper.queryAllRespCode(respCode);
        if(list!=null && list.size()>0){
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }
    }

    @Override
    public void addRespCode(ResponseDTO<String> responseDTO, RespCode respCode) {
        if(respCodeMapper.insertModel(respCode)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateRespCode(ResponseDTO<String> responseDTO, RespCode respCode) {
        if(respCodeMapper.update(respCode)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void deleteRespCode(ResponseDTO<String> responseDTO, RespCode respCode) {
        if(respCodeMapper.delete(respCode)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
}
