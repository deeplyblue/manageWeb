package com.oriental.manage.service.reserve;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.manage.ReserveBalanceInterface;
import com.oriental.reserve.model.ReserveBalance;
import com.oriental.reserve.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/8/19
 * Time: 9:17
 * Desc 备付金余额查询
 */
@Slf4j
@Service
public class ReserveBalanceService {

    @Autowired
    private ReserveBalanceInterface reserveBalanceInterface;

    public void queryReserveBalance(Pagination<ReserveBalance> pagination, ResponseDTO<Pagination<ReserveBalance>> responseDTO){
        ResponseModel<List<ReserveBalance>> responseModel = reserveBalanceInterface.queryReserveBalance(pagination.getQueryBean());
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,pagination.getQueryBean(),"查询成功");
    }

    public void updateReserveBalance(ResponseDTO<String> responseDTO,ReserveBalance reserveBalance){
        ResponseModel<String> responseModel = reserveBalanceInterface.updateReserveBalance(reserveBalance);
        ReserveUtil.responseHandle(responseModel,responseDTO,"操作成功");
    }
}
