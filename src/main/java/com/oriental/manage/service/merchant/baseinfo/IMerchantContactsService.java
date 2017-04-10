package com.oriental.manage.service.merchant.baseinfo;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantContactInfo;

/**
 * Author: Yang xp
 * Date: 2016/5/23
 * Time: 9:58
 * Desc：商户联系人
 */
public interface IMerchantContactsService {

    void searchMerchantContacts(Pagination<MerchantContactInfo> pagination, MerchantContactInfo merchantContactInfo);

    void createMerchantContacts(MerchantContactInfo merchantContactInfo, ResponseDTO<String> responseDTO);

    void updateMerchantContacts(MerchantContactInfo merchantContactInfo,ResponseDTO<String> responseDTO);


}
