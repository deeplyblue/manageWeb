package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.institution.ContactInfoMapper;
import com.oriental.manage.pojo.institution.ContactInfo;
import com.oriental.manage.service.institution.IContractInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/5.
 */
@Service
public class ContractInfoImpl implements IContractInfoService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @Override
    public void queryPage(Pagination<ContactInfo> pagination,ContactInfo contactInfo) {

        List<ContactInfo> list = contactInfoMapper.queryPage(contactInfo);

        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);

    }

    }
    @Override
   public  void addContactInfo(ContactInfo contactInfo,ResponseDTO<String> responseDTO){
          if (contactInfoMapper.insertContractInfo(contactInfo)>0){
              responseDTO.setSuccess(true);
          }else {
              responseDTO.setSuccess(false);
          }
    }
    @Override
    public  void  updateContactInfo(ContactInfo contactInfo,ResponseDTO<String> responseDTO){
        if(contactInfoMapper.updateContactInfo(contactInfo)>0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }
    @Override
    public  void deleteContactInfo(ContactInfo contactInfo,ResponseDTO<String> responseDTO){
        if(contactInfoMapper.deleteByPrimaryKey(contactInfo)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }

    }


}