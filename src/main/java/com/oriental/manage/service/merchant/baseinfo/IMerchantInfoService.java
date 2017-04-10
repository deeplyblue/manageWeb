package com.oriental.manage.service.merchant.baseinfo;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.BaseModel;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantInfo;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/19
 * Time: 11:02
 * Desc：商户基本信息操作
 */
public interface IMerchantInfoService {

    void searchMerchantInfo(Pagination<MerchantInfo> pagination, MerchantInfo baseModel);

    void createMerchantInfo(MerchantInfo merchantInfo, ResponseDTO<String> responseDTO);

    void updateMerchantInfo(MerchantInfo merchantInfo,ResponseDTO<String> responseDTO);

    List<MerchantInfo> getMerchantInfo();

    void updateMerchantInfoByStatus(MerchantInfo merchantInfo,ResponseDTO<String> responseDTO);

    boolean check(MerchantInfo merchantInfo,String code);
}
