package com.oriental.manage.service.reserve;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.model.BaseModel;
import com.oriental.reserve.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/6/3
 * Time: 18:32
 * Desc：备付金工具类
 */
@Slf4j
public class ReserveUtil {

    private ReserveUtil(){
    }

    public static void responseHandle(ResponseModel responseModel, ResponseDTO<String> responseDTO, String message){
        if (responseModel.isSuccess()){
            responseDTO.setSuccess(true);
            responseDTO.setObject(message);
        }else {
            responseDTO.setSuccess(false);
            responseDTO.setCode(responseModel.getErrorCode());
            responseDTO.setMsg(responseModel.getErrorMsg());
        }
    }

    public static<T extends BaseModel> void responseQueryHandle(ResponseModel<List<T>> responseModel, Pagination<T> pagination,ResponseDTO<Pagination<T>> responseDTO,T mode, String message){
        if (null != responseModel && responseModel.isSuccess()) {
            pagination.setList(responseModel.getResult());
            pagination.setRowCount(0);
            if (responseModel.getResult().size() > 0) {
                pagination.setRowCount(responseModel.getResult().get(0).getRowCount());
                pagination.setPageNum(mode.getPageNum());
                pagination.setPageSize(mode.getPageSize());
            }
            responseDTO.setObject(pagination);
            responseDTO.setSuccess(true);
        } else if (null != responseModel) {
            responseDTO.setCode(responseModel.getErrorCode());
            responseDTO.setMsg(responseModel.getErrorMsg());
        } else {
            throw new BusiException(message);
        }
    }

}
