package com.oriental.manage.service.reserve;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.manage.ReserveManage;
import com.oriental.reserve.model.ReserveInfo;
import com.oriental.reserve.model.ResponseModel;
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
public class ReserveInfoService {

//    @Autowired
//    private ReserveManage reserveManage;

//    public void searchReserve(Pagination<ReserveInfo> pagination, ReserveInfo reserveInfo){
//        log.info("查询备付金信息:{}",reserveInfo);
//        ResponseModel<List<ReserveInfo>> responseModel = reserveManage.getReserveInfo(reserveInfo);
//        log.info("查询备付金信息:{}",responseModel);
//        pagination.setRowCount(0);
//        if (responseModel.isSuccess()){
//            if (responseModel.getResult().size()>0){
//                pagination.setRowCount(responseModel.getResult().get(0).getRowCount());
//            }
//            pagination.setList(responseModel.getResult());
//        }
//    }
//
//    public List<ReserveInfo> searchReserve(){
//        ReserveInfo reserveInfo = new ReserveInfo();
//        reserveInfo.setPageSize(1000);
//        reserveInfo.setPageNum(1);
//        ResponseModel<List<ReserveInfo>> responseModel = reserveManage.getReserveInfo(reserveInfo);
//        if (responseModel.isSuccess()){
//            return responseModel.getResult();
//        }
//        throw new BusiException(responseModel.getErrorCode(),responseModel.getErrorCode());
//    }
//
//    public void updateReserve(ReserveInfo reserveInfo, ResponseDTO<String> responseDTO){
//        ResponseModel<String> responseModel = reserveManage.updateReserveInfo(reserveInfo);
//        ReserveUtil.responseHandle(responseModel,responseDTO,"修改成功");
//    }
//
//    public void createReserve(ReserveInfo reserveInfo,ResponseDTO<String> responseDTO){
//        ResponseModel<String> responseModel = reserveManage.createReserveInfo(reserveInfo);
//        ReserveUtil.responseHandle(responseModel,responseDTO,"新增成功");
//    }
//
//    public void deleteReserve(ReserveInfo reserveInfo,ResponseDTO<String> responseDTO){
//        ResponseModel<String> responseModel = reserveManage.deleteReserveInfo(reserveInfo);
//        ReserveUtil.responseHandle(responseModel,responseDTO,"删除成功");
//    }


}
