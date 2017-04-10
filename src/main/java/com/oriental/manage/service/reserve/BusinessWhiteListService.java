package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.reserve.api.business.BusinessWhiteListInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.business.BusinessWhiteListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/6/3
 * Time: 17:43
 * Desc：备付金操作
 */
@Service
@Slf4j
public class BusinessWhiteListService {

    @Autowired
    private BusinessWhiteListInterface businessWhiteListInterface;

    public void searchBusinessWhiteList(Pagination<BusinessWhiteListDto> pagination,BusinessWhiteListDto businessWhiteListDto, ResponseDTO<Pagination<BusinessWhiteListDto>> responseDTO){
        ResponseModel<List<BusinessWhiteListDto>> responseModel = businessWhiteListInterface.selectByModelPage(businessWhiteListDto);
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,businessWhiteListDto,"查询失败");
    }
    public ResponseModel updateBusinessWhiteList(Pagination<BusinessWhiteListDto> pagination,BusinessWhiteListDto businessWhiteListDto, ResponseDTO<Pagination<BusinessWhiteListDto>> responseDTO){

        return businessWhiteListInterface.updateBusinessMessage(businessWhiteListDto, SessionUtils.getUserName());

    }

}
