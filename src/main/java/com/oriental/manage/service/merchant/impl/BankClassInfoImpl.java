package com.oriental.manage.service.merchant.impl;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.BankClassCfgMapper;
import com.oriental.manage.pojo.merchant.BankClassCfg;
import com.oriental.manage.service.merchant.IBankClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/23.
 */
@Service
public class BankClassInfoImpl implements IBankClassInfoService {
    @Autowired
    private BankClassCfgMapper bankClassCfgMapper;

    @Override
    public void queryPage(Pagination<BankClassCfg> pagination, BankClassCfg bankClassCfg){
        List<BankClassCfg> list=bankClassCfgMapper.queryPage(bankClassCfg);
        if(list!=null&&list.size()>0){
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }
    }
    @Override
    public  void addBankClassCfg(ResponseDTO<String> responseDTO, BankClassCfg bankClassCfg){
        if(bankClassCfgMapper.insertBankClassCfg(bankClassCfg)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
    @Override
    public void upadteBankClassCfg(ResponseDTO<String> responseDTO, BankClassCfg bankClassCfg){
        if(bankClassCfgMapper.updateBankClassCfg(bankClassCfg)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
    @Override
    public void deleteBankClassCfg(ResponseDTO<String> responseDTO, BankClassCfg bankClassCfg){

        if(bankClassCfgMapper.deleteBankClassCfg(bankClassCfg)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
}
