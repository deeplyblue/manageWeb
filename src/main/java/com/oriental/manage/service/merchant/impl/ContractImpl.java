package com.oriental.manage.service.merchant.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.ContractMapper;
import com.oriental.manage.pojo.institution.ContactInfo;
import com.oriental.manage.pojo.merchant.ContractInfo;
import com.oriental.manage.service.merchant.IContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/16.
 */
@Service
public class ContractImpl implements IContractService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ContractMapper contractMapper;

    @Override
    public void queryPage(Pagination<ContractInfo> pagination, ContractInfo contractInfo) {

        List<ContractInfo> list = contractMapper.queryPage(contractInfo);

        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);

        }
    }

    @Override
    public void addContract(ResponseDTO<String> responseDTO, ContractInfo contractInfo) {
        if (contractMapper.insertContractInfo(contractInfo) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateContract(ResponseDTO<String> responseDTO, ContractInfo contractInfo) {
        if (contractMapper.updateContractInfo(contractInfo) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void deleteContract(ResponseDTO<String> responseDTO, ContractInfo contractInfo) {
        if (contractMapper.deleteContract(contractInfo) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }

    }

    @Override
    public ContractInfo queryById(String id) {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setId(id);
        List<ContractInfo> contractInfoList = contractMapper.queryPage(contractInfo);
        if (null != contractInfoList && contractInfoList.size() >0){
            return contractInfoList.get(0);
        }
        return null;
    }

    @Override
    public boolean check(ContractInfo contractInfo) {
        List<ContractInfo> contractInfoList = contractMapper.queryPage(contractInfo);
        if (null != contractInfoList && contractInfoList.size() >0){
               return true;
        }else{
        return false;
        }
    }
}
