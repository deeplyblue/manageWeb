package com.oriental.manage.service.merchant.baseinfo.impl;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.baseinfo.MerchantInfoMapper;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantInfo;
import com.oriental.manage.service.merchant.baseinfo.IMerchantInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/19
 * Time: 11:03
 * Desc：商户操作实现类
 */
@Slf4j
@Service
public class MerchantInfoServiceImpl implements IMerchantInfoService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Override
    public void searchMerchantInfo(Pagination<MerchantInfo> pagination, MerchantInfo merchantInfo) {
        List<MerchantInfo> merchantInfoList = merchantInfoMapper.queryPage(merchantInfo);
        if(merchantInfoList!=null&&merchantInfoList.size()>0){
            pagination.setRowCount(merchantInfoList.get(0).getRowCount());
            pagination.setList(merchantInfoList);
        }
    }

    @Override
    public void createMerchantInfo(MerchantInfo merchantInfo, ResponseDTO<String> responseDTO) {
        merchantInfo.setCompanyType(CompanyType.MERCHANT.getCode());
        int count = merchantInfoMapper.insertMerchantInfo(merchantInfo);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }


    @Override
    public void updateMerchantInfo(MerchantInfo merchantInfo, ResponseDTO<String> responseDTO) {
        int count = merchantInfoMapper.updateMerchantInfo(merchantInfo);
        if (count > 0){
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public List<MerchantInfo> getMerchantInfo() {
        MerchantInfo merchantInfo = new MerchantInfo();
        //因公用分页查询方法，预先设置查询5000条记录 @TODO 超过5000则应该更改页面拿取方式
        merchantInfo.setStartRow(0);
        merchantInfo.setPageSize(5000);
        return merchantInfoMapper.queryPage(merchantInfo);
    }

    @Override
    public void updateMerchantInfoByStatus(MerchantInfo merchantInfo, ResponseDTO<String> responseDTO) {
        int count = merchantInfoMapper.updateMerchantInfoByStatus(merchantInfo);
        if (count > 0){
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public boolean check(MerchantInfo merchantInfo,String code) {
        MerchantInfo merchant=new MerchantInfo();
        if(code!=null && code!=""){
        merchant.setMerchantCode(code);
        List<MerchantInfo> list=merchantInfoMapper.check(merchant);
          if(list.get(0).getMerchantAbbrName().equals(merchantInfo.getMerchantAbbrName() ) || list.get(0).getMerchantName().equals(merchantInfo.getMerchantName())){
              return false;
          }else if (merchantInfoMapper.check(merchantInfo)!=null && merchantInfoMapper.check(merchantInfo).size()>0){
            return true;
          }
        } else
        {
        if(merchantInfoMapper.check(merchantInfo)!=null && merchantInfoMapper.check(merchantInfo).size()>0){
            return true;
        }else {
            return false;
        }
        }return  false;
    }
}
