package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.manage.ReportUploadInfoManage;
import com.oriental.reserve.model.ReportUploadInfo;
import com.oriental.reserve.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/8/16
 * Time: 15:51
 * Desc??????????
 */
@Service
@Slf4j
public class ReportUploadInfoService {

    @Autowired
    private ReportUploadInfoManage reportUploadInfoManage;

    public void queryReportUploadInfo(Pagination<ReportUploadInfo> pagination, ResponseDTO<Pagination<ReportUploadInfo>> responseDTO, ReportUploadInfo reportUploadInfo){
        ResponseModel<List<ReportUploadInfo>> responseModel = reportUploadInfoManage.queryReportUploadInfo (reportUploadInfo);
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,reportUploadInfo,"????");
    }

}
