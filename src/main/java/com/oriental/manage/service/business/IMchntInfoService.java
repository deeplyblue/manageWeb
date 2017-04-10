package com.oriental.manage.service.business;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.business.MchntInfo;

/**
 * Created by wangjun on 2016/5/25.
 */
public interface IMchntInfoService {

    void queryPage(Pagination<MchntInfo> pagination, MchntInfo mchntInfo);

    void addMchntInfo(ResponseDTO<String> responseDTO, MchntInfo mchntInfo);

    void updateMchntInfo(ResponseDTO<String> responseDTO, MchntInfo mchntInfo);

    void deleteMchntInfo(ResponseDTO<String> responseDTO, MchntInfo mchntInfo);
}
