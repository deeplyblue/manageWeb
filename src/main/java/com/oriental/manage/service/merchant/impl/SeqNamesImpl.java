package com.oriental.manage.service.merchant.impl;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.SeqNamesMapper;
import com.oriental.manage.pojo.merchant.SeqNames;
import com.oriental.manage.service.merchant.ISeqNamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/23.
 */
@Service
public class SeqNamesImpl implements ISeqNamesService {

    @Autowired
    private SeqNamesMapper seqNamesMapper;

    @Override
   public  void queryPage(Pagination<SeqNames > pagination , SeqNames seqNames){
        List<SeqNames> list=seqNamesMapper.queryPage(seqNames);
        if(list!=null&&list.size()>0){
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }
   }
    @Override
   public void addSeqNames(ResponseDTO<String> responseDTO, SeqNames seqNames){
        int i=seqNamesMapper.queryByOrg(seqNames);
      if(i==0){
       if(seqNamesMapper.insert(seqNames)>0){
           responseDTO.setSuccess(true);
       }else{
           responseDTO.setSuccess(false);
       }
   }else{
          responseDTO.setMsg("支付机构已经配置");
          responseDTO.setSuccess(false);
      }
    }
    @Override
    public void updateSeqName(ResponseDTO<String> responseDTO, SeqNames seqName){
        if(seqNamesMapper.updateByKey(seqName)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
    @Override
    public void deleteSeqName(ResponseDTO<String> responseDTO, SeqNames seqName){
        if(seqNamesMapper.delete(seqName)>0){

            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }

    }
}

