package com.oriental.manage.controller.opcif;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.LogIdInterceptor;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.opcif.common.model.RequestModel;
import com.oriental.opcif.common.model.Response;
import com.oriental.opcif.common.model.ResponseModel;
import com.oriental.opcif.product.bizModel.request.SysSecruqaQueryReqDto;
import com.oriental.opcif.product.bizModel.request.SysSecruqaReqDto;
import com.oriental.opcif.product.bizModel.response.SysSecrueqaQueryResDto;
import com.oriental.opcif.product.facade.CustomerSecurqaFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 2016/10/25.
 * 客户密保问题列表
 */
@Slf4j
@Controller
@RequestMapping("cif/customerSecurqa")
public class CustomerSecurqaController {
    @Autowired(required = false)
    private CustomerSecurqaFacade customerSecurqaFacade;

    @RequestMapping("init")
    public String init(){
        return "opcif/searchCustomerSecurqa";
    }
    @RequestMapping("toAdd")
    @RequiresPermissions("customerSecurqa_add")
    public String toAdd(){ return "opcif/addCustomerSecurqa";}
    @RequestMapping("toUpdate")
    @RequiresPermissions("customerSecurqa_update")
    public String toUpdate(){ return "opcif/updateCustomerSecurqa";}
    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("customerSecurqa_search")
    public ResponseDTO<Pagination<SysSecrueqaQueryResDto>> queryPage(Pagination<SysSecrueqaQueryResDto>pagination, SysSecruqaQueryReqDto sysSecrueqaQueryResDto){

        ResponseDTO<Pagination<SysSecrueqaQueryResDto>> responseDTO=new ResponseDTO<>();
        try{
            RequestModel<SysSecrueqaQueryResDto> requestModel1 = new RequestModel<SysSecrueqaQueryResDto>();
            requestModel1.setPageNo(pagination.getPageNum());
            requestModel1.setPageSize(pagination.getPageSize());
            Response<List<SysSecrueqaQueryResDto>> response= customerSecurqaFacade.findSysSecurqa(sysSecrueqaQueryResDto);
            pagination.setList(response.getResult());
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("add")
    @ResponseBody
    @RequiresPermissions("customerSecurqa_add")
    public ResponseDTO<String> add(SysSecruqaReqDto sysSecruqaReqDto){
        ResponseDTO<String> responseDTO=new ResponseDTO<>();
        try {
            sysSecruqaReqDto.setCreatedBy(SessionUtils.getUserName());
            sysSecruqaReqDto.setCreatedAt(new Date());
            Response<Boolean> response = customerSecurqaFacade.setSysSecurqa(sysSecruqaReqDto);
            if (response.isSuccess()){
                responseDTO.setSuccess(true);
            }else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getResponseDesc());
            }
        }catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());

        }
        return responseDTO;
    }

    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions("customerSecurqa_update")
    public ResponseDTO<String> update(SysSecruqaReqDto sysSecruqaReqDto){
        ResponseDTO<String> responseDTO=new ResponseDTO<>();
        try {
            sysSecruqaReqDto.setUpdatedBy(SessionUtils.getUserName());
            sysSecruqaReqDto.setUpdatedAt(new Date());
            Response<Boolean> response = customerSecurqaFacade.updateSysSecurqa(sysSecruqaReqDto);
            if (response.isSuccess()){
                responseDTO.setSuccess(true);
            }else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getResponseDesc());
            }
        }catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());

        }
        return responseDTO;
    }
}
