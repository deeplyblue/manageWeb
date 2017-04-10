package com.oriental.manage.service.merchant.itconfig.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.itconfig.MerchantInterfaceMapper;
import com.oriental.manage.pojo.merchant.itconfig.MerchantInterface;
import com.oriental.manage.service.merchant.itconfig.IMerchantInterfaceService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/30
 * Time: 13:32
 * Desc：商户支付接口配置
 */
@Slf4j
@Service
public class MerchantInterfaceServiceImpl implements IMerchantInterfaceService {

    @Autowired
    private MerchantInterfaceMapper merchantInterfaceMapper;

    @Override
    public void search(Pagination<MerchantInterface> pagination, MerchantInterface merchantInterface) {
        List<MerchantInterface> merchantInterfaceList = merchantInterfaceMapper.queryMerchantInterface(merchantInterface);
        if(merchantInterfaceList!=null && merchantInterfaceList.size() > 0){
            pagination.setRowCount(merchantInterfaceList.get(0).getRowCount());
            pagination.setList(merchantInterfaceList);
        }
    }

    @Override
    public void update(MerchantInterface merchantInterface, ResponseDTO<String> responseDTO) {
        int count = merchantInterfaceMapper.updateMerchantInterface(merchantInterface);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void add(MerchantInterface merchantInterface, ResponseDTO<String> responseDTO) {
        merchantInterface.setId(DateUtil.getCurrent().concat(RandomMath.getNum(10)));
        int count = merchantInterfaceMapper.createMerchantInterface(merchantInterface);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }
}
