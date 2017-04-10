package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.service.reserve.BankTransFileInfoService;
import com.oriental.reserve.model.trans.BankTransFileInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: Yang xp
 * Date: 2016/6/4
 * Time: 17:19
 * Desc：银行交易文件上传
 */
@Slf4j
@Controller
@RequestMapping("/bank/trans/file")
public class BankTransUploadFileController {

    @Autowired
    private BankTransFileInfoService bankTransFileInfoService;

    @RequestMapping("/init")
    public String inti(){
        return "reserve/searchBankTransUploadFile";
    }

    @RequiresPermissions("reserveBankTransUpload_upload")
    @RequestMapping("/toUpload")
    public String toUpload(){
        return "reserve/addBankTransUploadFile";
    }

    @RequestMapping("/upload")
    @RequiresPermissions("reserveBankTransUpload_upload")
    @ResponseBody
    public ResponseDTO<String> upload(BankTransFileInfoDto bankTransFileInfo){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try{
            bankTransFileInfo.setCreateBy(SessionUtils.getUserName());
            bankTransFileInfo.setUpdateBy(SessionUtils.getUserName());
            bankTransFileInfoService.createBankTransFileInfo(responseDTO,bankTransFileInfo);
        }catch (Exception e){
            log.error("文件上传失败:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("/search")
    @RequiresPermissions("bankTransFile_search")
    @ResponseBody
    public ResponseDTO<Pagination<BankTransFileInfoDto>> query(Pagination<BankTransFileInfoDto> pagination,@RequestBody BankTransFileInfoDto bankTransFileInfo){
        ResponseDTO<Pagination<BankTransFileInfoDto>> responseDTO= new ResponseDTO<>();
        try {
            log.info("上传文件查询:{}",bankTransFileInfo);
            if (bankTransFileInfo.getPageSize()<1){
                bankTransFileInfo.setPageSize(10);
            }
            if (bankTransFileInfo.getPageNum() < 1){
                bankTransFileInfo.setPageNum(1);
            }
            bankTransFileInfoService.queryBankTransFileInfo(pagination,bankTransFileInfo);
            responseDTO.setObject(pagination);
            responseDTO.setSuccess(true);
        }catch (Exception e){
            log.error("查询失败:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
