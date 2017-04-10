package com.oriental.manage.service.merchant.baseinfo.impl;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.baseinfo.MerchantContactsMapper;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantContactInfo;
import com.oriental.manage.service.merchant.baseinfo.IMerchantContactsService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/23
 * Time: 10:00
 * Desc：商户联系人
 */
@Slf4j
@Service
public class MerchantContactsServiceImpl implements IMerchantContactsService {

    @Autowired
    private MerchantContactsMapper merchantContactsMapper;

    @Override
    public void searchMerchantContacts(Pagination<MerchantContactInfo> pagination, MerchantContactInfo merchantContactInfo) {
        merchantContactInfo.setCompanyType(CompanyType.MERCHANT.getCode());
        List<MerchantContactInfo> merchantContactInfoList = merchantContactsMapper.queryPage(merchantContactInfo);
        if(merchantContactInfoList!=null&&merchantContactInfoList.size()>0){
            pagination.setRowCount(merchantContactInfoList.get(0).getRowCount());
            pagination.setList(merchantContactInfoList);
        }
    }

    @Override
    public void createMerchantContacts(MerchantContactInfo merchantContactInfo, ResponseDTO<String> responseDTO) {
        merchantContactInfo.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
        merchantContactInfo.setCttStatus("0");
        merchantContactInfo.setCompanyType(CompanyType.MERCHANT.getCode());
        int count = merchantContactsMapper.createMerchantContactInfo(merchantContactInfo);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateMerchantContacts(MerchantContactInfo merchantContactInfo, ResponseDTO<String> responseDTO) {
        int count = merchantContactsMapper.updateMerchantContactInfo(merchantContactInfo);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

}
