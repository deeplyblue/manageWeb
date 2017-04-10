package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.api.trans.BankTransDetailInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.trans.DownDetailInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/6/4
 * Time: 15:57
 * Desc：
 */
@Service
@Slf4j
public class DownInfoService {

    @Autowired
    private BankTransDetailInterface bankTransDetailInterface;

    public void queryDownInfo(Pagination<DownDetailInfoDto> pagination, ResponseDTO<Pagination<DownDetailInfoDto>> responseDTO,DownDetailInfoDto downDetailInfo){
        downDetailInfo.setDeleteFlag("0");
        ResponseModel<List<DownDetailInfoDto>> responseModel =  bankTransDetailInterface.getBankTransObtainInfo(downDetailInfo);
        log.info("下载信息查询:{}",responseModel);
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,downDetailInfo,"查询成功");
    }

    public void delete(DownDetailInfoDto downDetailInfo, ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = bankTransDetailInterface.deleteBankTransObtainInfo(downDetailInfo);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重置成功");
    }
}
