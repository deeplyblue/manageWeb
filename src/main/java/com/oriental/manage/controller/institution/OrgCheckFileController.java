package com.oriental.manage.controller.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.DfsFileInfo;
import com.oriental.manage.service.base.IDfsFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 支付机构对账文件管理
 *
 * @author wuxg
 * @version 1.0.0 @createTime: 2016/7/4 10:17
 */
@Slf4j
@Controller
@RequestMapping("/orgCheckFile")
public class OrgCheckFileController {

    @Autowired
    private IDfsFileInfoService iDfsFileInfoService;

    @RequestMapping("/init")
    public String init() {
        return "institution/searchOrgCheckFile";
    }

    @RequestMapping("/batch")
    public String batch() {
        return "institution/searchLotTradingFile";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("orgCheckFile_update")
    public String toAdd() {
        return "institution/uploadOrgCheckFile";
    }

    @RequestMapping("/add")
    @RequiresPermissions("orgCheckFile_update")
    @ResponseBody
    public ResponseDTO<String> add(@RequestBody DfsFileInfo dfsFileInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            dfsFileInfo.setBusiType("DZ");
            dfsFileInfo.setCompanyType("02");
            if (iDfsFileInfoService.update(dfsFileInfo) > 0) {
                responseDTO.setSuccess(true);
                responseDTO.setMsg("上传成功！");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("上传失败！");
            }
        } catch (Exception e) {
            log.error("文件上传失败:", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("/search")
    @RequiresPermissions("orgCheckFile_search")
    @ResponseBody
    public ResponseDTO<Pagination<DfsFileInfo>> query(@RequestBody Pagination<DfsFileInfo> pagination) {
        ResponseDTO<Pagination<DfsFileInfo>> responseDTO = new ResponseDTO<>();
        try {
            pagination.getQueryBean().setPageNum(pagination.getPageNum());
            pagination.getQueryBean().setPageSize(pagination.getPageSize());
            DfsFileInfo dfsFileInfo=pagination.getQueryBean();
            dfsFileInfo.setBusiType("DZ");
            iDfsFileInfoService.searchDfsFileInfo1(pagination,dfsFileInfo);
            responseDTO.setObject(pagination);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("查询失败:", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequiresPermissions("orgCheckFile-batch_search")
    @RequestMapping("/searchBatch")
    @ResponseBody
    public ResponseDTO<Pagination<DfsFileInfo>> searchBatch(@RequestBody Pagination<DfsFileInfo> pagination) {
        ResponseDTO<Pagination<DfsFileInfo>> responseDTO = new ResponseDTO<>();
        try {
            pagination.getQueryBean().setPageNum(pagination.getPageNum());
            pagination.getQueryBean().setPageSize(pagination.getPageSize());
            DfsFileInfo dfsFileInfo=pagination.getQueryBean();
            iDfsFileInfoService.searchDfsFileInfo1(pagination,dfsFileInfo);
            responseDTO.setObject(pagination);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("查询失败:", e);
            responseDTO.setMsg(e.getMessage());
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

}
