package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.api.trans.BankTransDetailInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.trans.BankTransFileInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/6/5
 * Time: 11:59
 * Desc：备付金文件上传
 */
@Service
@Slf4j
public class BankTransFileInfoService {

    @Autowired
    private BankTransDetailInterface bankTransDetailInterface;

    public void queryBankTransFileInfo(Pagination<BankTransFileInfoDto> pagination,BankTransFileInfoDto bankTransFileInfo){
        ResponseModel<List<BankTransFileInfoDto>> responseModel = bankTransDetailInterface.queryBankTransFileInfo (bankTransFileInfo);
        log.info("查询上传信息:{}",responseModel);
        pagination.setRowCount(0);
        if (responseModel.isSuccess()){
            if (responseModel.getResult().size()>0){
                pagination.setRowCount(responseModel.getResult().get(0).getRowCount());
            }
            pagination.setPageNum(bankTransFileInfo.getPageNum());
            pagination.setPageSize(bankTransFileInfo.getPageSize());
            pagination.setList(responseModel.getResult());
        }
    }

    public void createBankTransFileInfo(ResponseDTO<String> responseDTO,BankTransFileInfoDto bankTransFileInfo){
        ResponseModel<String> responseModel = bankTransDetailInterface.createBankTransFileInfo(bankTransFileInfo);
        ReserveUtil.responseHandle(responseModel,responseDTO,"文件上传成功");
    }
}
