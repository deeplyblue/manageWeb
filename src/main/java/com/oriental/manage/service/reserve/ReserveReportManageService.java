package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.dao.base.DataDictMapper;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.reserve.api.report.ReserveReportUploadInterface;
import com.oriental.reserve.api.report.ReserverReportManageInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.report.ReserveReportUploadInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shulw
 * @version 1.0
 * @Desc:
 * @Date: 2016/12/29
 * @see:
 */
@Service
@Slf4j
public class ReserveReportManageService {

    @Autowired
    ReserveReportUploadInterface reserveReportUploadInterface;
    @Autowired
    ReserverReportManageInterface reserverReportManageInterface;
    @Autowired
    private DataDictMapper dataDictMapper;

    public void queryReserveReportInfo(Pagination<ReserveReportUploadInfo> pagination, ReserveReportUploadInfo reserveReportUploadInfo) {

        ResponseModel<List<ReserveReportUploadInfo>> responseModel =
                reserverReportManageInterface.queryReserveReportUploadInfo(reserveReportUploadInfo);
        log.info("查询上传信息:{}", responseModel);
        pagination.setRowCount(0);
        if (responseModel.isSuccess()) {
            if (responseModel.getResult().size() > 0) {
                pagination.setRowCount(responseModel.getResult().get(0).getRowCount());
            }
            pagination.setPageNum(reserveReportUploadInfo.getPageNum());
            pagination.setPageSize(reserveReportUploadInfo.getPageSize());
            pagination.setList(responseModel.getResult());
        }

    }


    public void createReserveReportInfoUpload(ResponseDTO<String> responseDTO, ReserveReportUploadInfo reserveReportUploadInfo) {

        ResponseModel<String> responseModel =  reserveReportUploadInterface.parseUploadReport(reserveReportUploadInfo);

        ReserveUtil.responseHandle(responseModel,responseDTO,"文件上传解析成功");
    }

    public List<DataDict> queryReserveReportType() {

        List<String> list=new ArrayList<>();
        list.add("YEAR");
        list.add("QUARTER");
        DataDict dataDict = new DataDict();
        dataDict.setItemName("COMMON_VALUE");
        dataDict.setColName("MESSAGE_TYPE_FOR_RESERVE");
        dataDict.setItemValList(list);
        return dataDictMapper.selectReportTypeByItemType(dataDict);

    }
}
