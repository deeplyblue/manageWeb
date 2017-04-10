package com.oriental.manage.service.merchant.trans;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.TransRight;

import java.util.List;
import java.util.Map;

/**
 * Author: Yang xp
 * Date: 2016/5/25
 * Time: 10:50
 * Desc：商户交易权限
 */
public interface IMerchantTransRightService {

    void searchMerchantTransRight(Pagination<TransRight> pagination, TransRight transRight);

    List<Map<String, String>> initResource(String merchantCode,String companyType);

    void updateMerchantTransRight(List<TransRight> transRightList);

    void addMerchantTransRight(List<TransRight> transRightList , ResponseDTO<String> responseDTO);

    void deleteMerchantTransRight(TransRight transRight , ResponseDTO<String> responseDTO);
}
