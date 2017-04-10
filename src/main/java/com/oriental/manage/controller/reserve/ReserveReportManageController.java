package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.service.reserve.ReserveReportManageService;
import com.oriental.reserve.model.report.ReserveReportUploadInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shulw
 * @version 1.0
 * @Desc: 季报，年报上传
 * @Date: 2016/12/21
 * @see:
 */
@Slf4j
@Controller
@RequestMapping("/reserve/reserveReport")
public class ReserveReportManageController {

    @Autowired
    private ReserveReportManageService reserveReportManageService;

    @Autowired
    private Constants Constants;

    @RequestMapping("/init")
    public String init() {
        return "reserve/report/searchReserveReportUploadInfo";
    }


    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<ReserveReportUploadInfo>> query(Pagination<ReserveReportUploadInfo> pagination, @RequestBody ReserveReportUploadInfo reserveReportUploadInfo) {
        ResponseDTO<Pagination<ReserveReportUploadInfo>> responseDTO = new ResponseDTO<Pagination<ReserveReportUploadInfo>>();
        try {
            log.info("上传文件查询:{}", reserveReportUploadInfo);
            if (reserveReportUploadInfo.getPageSize() < 1) {
                reserveReportUploadInfo.setPageSize(10);
            }
            if (reserveReportUploadInfo.getPageNum() < 1) {
                reserveReportUploadInfo.setPageNum(1);
            }

            reserveReportManageService.queryReserveReportInfo(pagination, reserveReportUploadInfo);
            responseDTO.setObject(pagination);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("查询失败:", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequiresPermissions("reserveReport_upload")
    @RequestMapping("/toUpload")
    public String toUpload() {
        return "reserve/report/addReserveReportUploadFile";
    }

    @RequestMapping("/upload")
    @RequiresPermissions("reserveReport_upload")
    @ResponseBody
    public ResponseDTO<String> upload(ReserveReportUploadInfo reserveReportUploadInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            reserveReportUploadInfo.setCreateBy(SessionUtils.getUserName());
            reserveReportManageService.createReserveReportInfoUpload(responseDTO, reserveReportUploadInfo);

        } catch (Exception e) {
            log.error("文件上传失败:", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }


    @RequestMapping("/searchReportType")
    @ResponseBody
    public ResponseDTO<Map<String, String>> initReserveReportType() {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();
        try {
            List<DataDict> dataDictList = reserveReportManageService.queryReserveReportType();
            Map<String, String> map = new HashMap<>();
            if (dataDictList != null && dataDictList.size() > 0) {
                for (DataDict dataDict : dataDictList) {
                    if (StringUtils.isNotBlank(dataDict.getItemVal())) {
                        if (StringUtils.equals(dataDict.getItemType(), "YEAR")) {
                            dataDict.setItemDesc(dataDict.getItemDesc() + "（年报）");
                        } else if (StringUtils.equals(dataDict.getItemType(), "QUARTER")) {
                            dataDict.setItemDesc(dataDict.getItemDesc() + "（季报）");
                        }
                        map.put(dataDict.getItemVal(), dataDict.getItemDesc());
                    }
                }
            }
            responseDTO.setSuccess(true);
            responseDTO.setObject(map);
            log.info("报表类型响应信息:{}", responseDTO);
        } catch (BusiException e) {
            log.error("系统异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getDesc());
        } catch (Exception e) {
            log.error("初始化失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("初始化失败");
        }
        return responseDTO;
    }

    @RequestMapping("/downReport")
    @ResponseBody
    public ResponseEntity<byte[]> downReport() {
        ResponseEntity<byte[]> responseEntity;
        File file = new File(Constants.getExcelPath() + "/备付金季报报表模版.xlsx");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setContentDispositionFormData("attachment",new String(file.getName().getBytes("UTF-8"),"ISO-8859-1"));

            responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (BusiException e) {
            responseEntity = new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
            log.error("dfs文件未找到", e);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<byte[]>(HttpStatus.EXPECTATION_FAILED);
            log.error("dfs文件下载失败", e);
        }
        return responseEntity;
    }

}
