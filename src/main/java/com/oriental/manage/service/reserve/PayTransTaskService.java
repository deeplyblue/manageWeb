package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.api.paid.PayTransTaskInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.paid.PayTransTaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanglu on 2017/1/11 0011.
 * 查看日志
 */
@Slf4j
@Service
public class PayTransTaskService {

    @Autowired
    private PayTransTaskInterface payTransTaskInterface;


    public void query(Pagination<PayTransTaskDto> pagination,PayTransTaskDto payTransTaskDto, ResponseDTO<Pagination<PayTransTaskDto>> responseDTO){
        ResponseModel<List<PayTransTaskDto>> responseModel = payTransTaskInterface.queryPayTransTask(payTransTaskDto);
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,payTransTaskDto,"查询失败");
    }

}
