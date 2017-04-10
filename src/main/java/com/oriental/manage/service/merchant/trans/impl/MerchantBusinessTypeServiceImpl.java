package com.oriental.manage.service.merchant.trans.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.trans.MerchantBusinessTypeMapper;
import com.oriental.manage.pojo.merchant.trans.MerchantBusinessType;
import com.oriental.manage.service.merchant.trans.IMerchantBusinessTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/23
 * Time: 19:34
 * Desc：商户资金源实现类
 */
@Slf4j
@Service
public class MerchantBusinessTypeServiceImpl implements IMerchantBusinessTypeService {

    @Autowired
    private MerchantBusinessTypeMapper merchantBusinessTypeMapper;

    @Override
    public void queryPage(Pagination<MerchantBusinessType> pagination, MerchantBusinessType merchantBusinessType) {
        List<MerchantBusinessType> merchantBusinessTypeList = merchantBusinessTypeMapper.searchMerchantBusinessType(merchantBusinessType);
        if(merchantBusinessTypeList!=null && merchantBusinessTypeList.size() > 0){
            pagination.setRowCount(merchantBusinessTypeList.get(0).getRowCount());
            pagination.setList(merchantBusinessTypeList);
        }
    }

    @Override
    public void addMerchantBusinessType(MerchantBusinessType merchantBusinessType, ResponseDTO<String> responseDTO) {
        int count = merchantBusinessTypeMapper.addMerchantBusinessType(merchantBusinessType);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateMerchantBusinessType(MerchantBusinessType merchantBusinessType, ResponseDTO<String> responseDTO) {
        int count = merchantBusinessTypeMapper.updateMerchantBusinessType(merchantBusinessType);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }
}
