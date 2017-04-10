package com.oriental.manage.service.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.ContactInfo;

/**
 * Created by wangjun on 2016/5/5.
 */
public interface IContractInfoService {

    //查询机构联系人
    void queryPage (Pagination<ContactInfo> pagination,ContactInfo contactInfo);

    void addContactInfo(ContactInfo contactInfo,ResponseDTO<String> responseDTO);

    void  updateContactInfo(ContactInfo contactInfo,ResponseDTO<String> responseDTO);

    void deleteContactInfo(ContactInfo contactInfo,ResponseDTO<String> responseDTO);



}
