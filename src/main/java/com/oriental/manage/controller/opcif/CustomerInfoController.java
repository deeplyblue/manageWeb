package com.oriental.manage.controller.opcif;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.LogIdInterceptor;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.opcif.common.model.RequestModel;
import com.oriental.opcif.common.model.Response;
import com.oriental.opcif.common.model.ResponseModel;
import com.oriental.opcif.product.bizModel.manager.TUserBaseDto;
import com.oriental.opcif.product.bizModel.request.ManageCustomerLoginInfoReqDto;
import com.oriental.opcif.product.facade.CustomerRiskControlFacade;
import com.oriental.opcif.product.facade.manager.UserBaseInfoFacade;
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
 * Created by lupf on 2016/8/24.
 */
@Slf4j
@Controller
@RequestMapping("cif/customerInfo")
public class CustomerInfoController {

    @Autowired(required = false)
    private UserBaseInfoFacade userBaseInfoFacade;

    @Autowired(required = false)
    private CustomerRiskControlFacade customerRiskControlFacade;

    @RequestMapping("init")
    public String init(){
        return "opcif/searchCustomerInfo";
    }

    @RequestMapping("toDetail")
    public String toDetail(){ return  "opcif/searchCustomerInfoDetail" ;}

    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("customer-info_search")
    @OperateLogger(content = "客户信息查询",operationType = OperateLogger.OperationType.R)
    public ResponseDTO<Pagination<TUserBaseDto>> queryPage(Pagination<TUserBaseDto>pagination, TUserBaseDto tUserBaseDto){
        //@TODO 调用dubbo实现查询（参考：document\11_客户系统\开发\接口文档）
        //@TODO 菜单建立：开设一级菜单【客户系统】，里面挂客户相关内容
        //@TODO dubbo接口等尚未注入
        ResponseDTO<Pagination<TUserBaseDto>> responseDTO=new ResponseDTO<>();
        try{
            RequestModel<TUserBaseDto> requestModel1 = new RequestModel<TUserBaseDto>();
            requestModel1.setPageNo(pagination.getPageNum());
            requestModel1.setPageSize(pagination.getPageSize());
            requestModel1.setRequest(tUserBaseDto);
            ResponseModel<TUserBaseDto> responseModel1 = userBaseInfoFacade.selectTUserBaseDtoList(requestModel1,MDC.get(LogIdInterceptor.log_id));
            List<TUserBaseDto> tUserBaseDtoList=responseModel1.getList();
            pagination.setList(tUserBaseDtoList);
            pagination.setRowCount(responseModel1.getTotal());
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("freeze")
    @ResponseBody
    @RequiresPermissions("opcif-customer_froze")
    @OperateLogger(content = "冻结操作员",operationType = OperateLogger.OperationType.U)
    public ResponseDTO<String> freezeOperator(TUserBaseDto tUserBaseDto){
        ManageCustomerLoginInfoReqDto manageCustomerLoginInfoReqDto=new ManageCustomerLoginInfoReqDto();
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        manageCustomerLoginInfoReqDto.setOperatorNo(tUserBaseDto.getOperatorNo());
        manageCustomerLoginInfoReqDto.setUpdatedBy(SessionUtils.getUserName());
        manageCustomerLoginInfoReqDto.setUpdatedAt(new Date());
        Response<String> response= customerRiskControlFacade.freeze(manageCustomerLoginInfoReqDto);
        if(response.isSuccess()){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setMsg(response.getResponseDesc());
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequestMapping("defrosting")
    @ResponseBody
    @RequiresPermissions("opcif-customer_lock")
    @OperateLogger(content = "申请解冻操作员",operationType = OperateLogger.OperationType.U)
    public ResponseDTO<String> defrosting(TUserBaseDto tUserBaseDto){
        ManageCustomerLoginInfoReqDto manageCustomerLoginInfoReqDto=new ManageCustomerLoginInfoReqDto();
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        manageCustomerLoginInfoReqDto.setOperatorNo(tUserBaseDto.getOperatorNo());
        manageCustomerLoginInfoReqDto.setUpdatedBy(SessionUtils.getUserName());
        manageCustomerLoginInfoReqDto.setUpdatedAt(new Date());
        Response<String> response= customerRiskControlFacade.unFreeze(manageCustomerLoginInfoReqDto);
        if(response.isSuccess()){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setMsg(response.getResponseDesc());
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequestMapping("unFreeze")
    @ResponseBody
    @RequiresPermissions("opcif-customer_unFreeze")
    @OperateLogger(content = "确认解冻操作员",operationType = OperateLogger.OperationType.U)
    public ResponseDTO<String> unFreezeOperator(TUserBaseDto tUserBaseDto){
        ManageCustomerLoginInfoReqDto manageCustomerLoginInfoReqDto=new ManageCustomerLoginInfoReqDto();
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        manageCustomerLoginInfoReqDto.setOperatorNo(tUserBaseDto.getOperatorNo());
        manageCustomerLoginInfoReqDto.setUpdatedBy(SessionUtils.getUserName());
        manageCustomerLoginInfoReqDto.setUpdatedAt(new Date());
        Response<String> response= customerRiskControlFacade.unFreeze(manageCustomerLoginInfoReqDto);
        if(response.isSuccess()){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setMsg(response.getResponseDesc());
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }
}
