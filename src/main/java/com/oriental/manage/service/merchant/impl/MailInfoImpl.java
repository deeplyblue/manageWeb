package com.oriental.manage.service.merchant.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.MailInfoMapper;
import com.oriental.manage.pojo.merchant.MailInfo;
import com.oriental.manage.service.merchant.IMailInfoSeivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/16.
 */
@Service
public class MailInfoImpl implements IMailInfoSeivice {

    @Autowired
    private MailInfoMapper mailInfoMapper;

    @Override
    public void queryPage(Pagination<MailInfo> pagination, MailInfo mailInfo){

        List<MailInfo> list=mailInfoMapper.queryPage(mailInfo);

        if(list!=null&&list.size()>0){
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }

    }
    @Override
    public void addMailInfo(ResponseDTO<String> responseDTO, MailInfo mailInfo){
        if(mailInfoMapper.insertMailInfo(mailInfo)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }

    }
    @Override
    public void updateMailInfo(ResponseDTO<String> responseDTO,MailInfo mailInfo){
        if(mailInfoMapper.updateMailInfo(mailInfo)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
    @Override
    public  void deleteMailInfo(ResponseDTO<String> responseDTO,MailInfo mailInfo){
       if(mailInfoMapper.deleteMailInfo(mailInfo)>0){

           responseDTO.setSuccess(true);
       }else{
           responseDTO.setSuccess(false);
       }

    }
}
