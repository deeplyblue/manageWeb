package com.oriental.manage.service.merchant.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.bank.BankTypeRelationMapper;
import com.oriental.manage.pojo.bank.BankTypeRelation;
import com.oriental.manage.pojo.redis.Response;
import com.oriental.manage.service.merchant.IBankTypeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/23.
 */
@Service
public class BankTypeRelationImpl implements IBankTypeRelationService {

    @Autowired
    private BankTypeRelationMapper bankTypeRelationMapper;

    @Override
    public List<BankTypeRelation> getAll() throws Exception {
        return bankTypeRelationMapper.searchAll();
    }

    @Override
    public void queryPage(Pagination<BankTypeRelation> pagination, BankTypeRelation bankTypeRelation) {
        List<BankTypeRelation> list = bankTypeRelationMapper.queryPage(bankTypeRelation);
        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }

    }

    @Override
    public List<String> getAllBankCode() {
        return bankTypeRelationMapper.getBankCode();
    }

    @Override
    public void addBankTypeRelation(ResponseDTO<String> responseDTO, BankTypeRelation bankTypeRelation) {
        if(bankTypeRelationMapper.insertBankTypeRelation(bankTypeRelation)>0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setMsg("新增数据异常");
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void deleteBankTypeRelation(ResponseDTO<String> responseDTO, BankTypeRelation bankTypeRelation) {
        if(bankTypeRelationMapper.deleteBankTypeRelation(bankTypeRelation)>0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setMsg("删除数据异常");
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public int checkBankType(BankTypeRelation bankTypeRelation) {
        return bankTypeRelationMapper.checkBankType(bankTypeRelation);
    }

}
