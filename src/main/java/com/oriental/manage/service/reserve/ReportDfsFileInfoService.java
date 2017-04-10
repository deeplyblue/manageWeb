package com.oriental.manage.service.reserve;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.manage.ReportManage;
import com.oriental.reserve.model.ReportDfsInfo;
import com.oriental.reserve.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/6/8
 * Time: 17:14
 * Desc：
 */
@Service
@Slf4j
public class ReportDfsFileInfoService {

    @Autowired
    private ReportManage reportManage;

    /**
     * 查询报表信息
     *
     * @param pagination  查询信息
     * @param responseDTO 返回信息
     */
    public void queryReportDfsInfo(Pagination<ReportDfsInfo> pagination, ResponseDTO<Pagination<ReportDfsInfo>> responseDTO) {
        ResponseModel<List<ReportDfsInfo>> responseModel = reportManage.queryReportInfo(pagination.getQueryBean());
        pagination.setRowCount(0);
        if (null != responseModel && responseModel.isSuccess()) {
            pagination.setList(responseModel.getResult());
            if (responseModel.getResult().size() > 0) {
                pagination.setRowCount(responseModel.getResult().get(0).getRowCount());
            }
            responseDTO.setObject(pagination);
            responseDTO.setSuccess(true);
        } else if (null != responseModel) {
            responseDTO.setCode(responseModel.getErrorCode());
            responseDTO.setMsg(responseModel.getErrorMsg());
        } else {
            throw new BusiException("查询失败");
        }
    }

    public void resetGenerateReport(ReportDfsInfo reportDfsInfo,ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = reportManage.resetGenerateReport(reportDfsInfo);
        if (null != responseModel && responseModel.isSuccess()){
            responseDTO.setObject(responseModel.getResult());
            responseDTO.setSuccess(true);
        }else if (null != responseModel){
            responseDTO.setCode(responseModel.getErrorCode());
            responseDTO.setMsg(responseModel.getErrorCode());
            responseDTO.setSuccess(false);
        }else {
            throw new BusiException("重新生成失败");
        }
    }
}
