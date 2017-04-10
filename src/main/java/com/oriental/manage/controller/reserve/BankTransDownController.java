package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.service.reserve.DownInfoService;
import com.oriental.reserve.model.trans.DownDetailInfoDto;
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
 * Time: 15:51
 * Desc：银行交易下载情况
 */
@Controller
@RequestMapping("/bank/trans/down")
@Slf4j
public class BankTransDownController {


    @Autowired
    private DownInfoService downInfoService;

    @RequestMapping("/init")
    public String init(){
        return "reserve/searchBankTransDownInfo";
    }

    @OperateLogger(content = "查询备付金银行交易信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @RequiresPermissions("bankTransDown_search")
    @ResponseBody
    public ResponseDTO<Pagination<DownDetailInfoDto>> queryPage(Pagination<DownDetailInfoDto> pagination, @RequestBody DownDetailInfoDto baseModel){
        ResponseDTO<Pagination<DownDetailInfoDto>> responseDTO=new ResponseDTO<>();
        try{
            log.info("银行流水文件查询:{}",baseModel);
            if (baseModel.getPageNum() < 1){
                baseModel.setPageNum(1);
            }
            if (baseModel.getPageSize() < 1){
                baseModel.setPageSize(10);
            }
            downInfoService.queryDownInfo(pagination,responseDTO,baseModel);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseDTO<String> deleteDownInfo(@RequestBody DownDetailInfoDto downDetailInfo){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            downInfoService.delete(downDetailInfo,responseDTO);
        }catch (Exception e){
            log.error("下载信息重置失败:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
