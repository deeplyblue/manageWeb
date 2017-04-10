package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.ReportUploadInfoService;
import com.oriental.reserve.model.ReportUploadInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: Yang xp
 * Date: 2016/8/16
 * Time: 14:40
 * Desc 报表上传信息
 */
@Controller
@Slf4j
@RequestMapping("/reserve/report")
public class ReportUploadInfoController {

    @Autowired
    private ReportUploadInfoService reportUploadInfoService;

    @RequestMapping("/init")
    public String init(){
        return "/reserve/searchBankReportUploadInfo";
    }

    @RequestMapping("/search")
    @RequiresPermissions("reserveReport_search")
    @ResponseBody
    public ResponseDTO<Pagination<ReportUploadInfo>> queryReportUploadInfo(@RequestBody Pagination<ReportUploadInfo> pagination){
        ResponseDTO<Pagination<ReportUploadInfo>> responseDTO = new ResponseDTO<>();
        try{
            reportUploadInfoService.queryReportUploadInfo(pagination,responseDTO,pagination.getQueryBean());
        }catch (Exception e){
            log.error("报表上传查询失败:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

}
