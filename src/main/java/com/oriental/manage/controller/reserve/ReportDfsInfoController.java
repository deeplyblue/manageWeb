package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.dfsUtils.FastDFSPoolUtil;
import com.oriental.manage.core.dfsUtils.FastDFSUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.ReportDfsFileInfoService;
import com.oriental.reserve.model.BankTransFileInfo;
import com.oriental.reserve.model.ReportDfsInfo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

/**
 * Author: Yang xp
 * Date: 2016/6/8
 * Time: 16:24
 * Desc：备付金报表数据
 */
@Controller
@Slf4j
@RequestMapping("/report/file")
public class ReportDfsInfoController {

    @Autowired
    private ReportDfsFileInfoService reportDfsFileInfoService;

    @Autowired
    private FastDFSPoolUtil fastDFSPoolUtil;

    @Value("#{cfgProperties['downloadTempDir']}")
    private String downloadTempDir;

    @RequestMapping("/init")
    public String init(){
        return "reserve/searchReportFile";
    }

    @RequestMapping("/search")
    @RequiresPermissions("reportFile_search")
    @ResponseBody
    public ResponseDTO<Pagination<ReportDfsInfo>> search(@RequestBody Pagination<ReportDfsInfo> pagination){
        ResponseDTO<Pagination<ReportDfsInfo>> responseDTO = new ResponseDTO<Pagination<ReportDfsInfo>>();
        try{
            reportDfsFileInfoService.queryReportDfsInfo(pagination,responseDTO);
        }catch (Exception e){
            log.error("查询报表生成信息",e);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }


    @ResponseBody
    @RequestMapping("/reset")
    public ResponseDTO<String> reset(@RequestBody ReportDfsInfo reportDfsInfo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            reportDfsFileInfoService.resetGenerateReport(reportDfsInfo,responseDTO);
        }catch (Exception e){
            log.error("重新生成报表失败:Exception",e);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequiresPermissions("reportFile_down")
    @RequestMapping("/downloadReportFile")
    public ResponseEntity<byte []> downReportFile(ReportDfsInfo reportDfsInfo){
        try {
            String localFileName = downloadTempDir.concat(File.separator).concat(reportDfsInfo.getOrgFileName());
            int count = fastDFSPoolUtil.download(reportDfsInfo.getGroupName(),reportDfsInfo.getDfsFilePath(),localFileName);
            if (count != -1){
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", new String(reportDfsInfo.getOrgFileName().getBytes("UTF-8"),"ISO-8859-1"));
                return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(localFileName)), headers, HttpStatus.CREATED);
            }
        } catch (IOException e) {
            log.error("下载文件异常:",e);
        } catch (Exception e){
            log.error("下载文件异常:",e);
        }
        return null;
    }

}
