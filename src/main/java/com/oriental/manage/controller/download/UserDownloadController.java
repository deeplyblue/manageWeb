package com.oriental.manage.controller.download;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.download.UserDownload;
import com.oriental.manage.service.download.IUserDownloadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/7/21.
 * 下载中心
 */
@Controller
@RequestMapping("download/userDownload")
@Slf4j
public class UserDownloadController {

    @Autowired
    private IUserDownloadService userDownloadService;

    @RequestMapping("init")
    @RequiresPermissions("userDownload_search")
    public String init(){return "download/searchUserDownload";}


    @OperateLogger(content = "查询文件列表",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("userDownload_search")
    public ResponseDTO<Pagination<UserDownload>> queryPage(Pagination<UserDownload> pagination, UserDownload userDownload){
        ResponseDTO<Pagination<UserDownload>> responseDTO = new ResponseDTO<Pagination<UserDownload>>();
        try{
            userDownload.setUserName(SessionUtils.getUserName());
            userDownloadService.queryPage(pagination,userDownload);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch (Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }


}
