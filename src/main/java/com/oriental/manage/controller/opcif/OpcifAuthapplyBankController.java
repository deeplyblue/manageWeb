package com.oriental.manage.controller.opcif;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.LogIdInterceptor;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.opcif.common.model.RequestModel;
import com.oriental.opcif.common.model.ResponseModel;
import com.oriental.opcif.product.bizModel.manager.TAuthapplyBankDto;
import com.oriental.opcif.product.facade.manager.AuthapplyBankInfoFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangxinhai on 2016/8/25.
 */
@Slf4j
@Controller
@RequestMapping("opcif/opcifAuthapplyBank")
public class OpcifAuthapplyBankController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired(required = false)
    private AuthapplyBankInfoFacade authapplyBankInfoFacade;
    @RequestMapping("init")
    public String init() {
        return "opcif/opcifAuthapplyBank";
    }

    @OperateLogger(content = "客户认证申请信息查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("opcifAuthapplyBank_search")
    public ResponseDTO<Pagination<TAuthapplyBankDto>> queryPage(@RequestBody Pagination<TAuthapplyBankDto> pagination) {
        ResponseDTO<Pagination<TAuthapplyBankDto>> responseDTO = new ResponseDTO<Pagination<TAuthapplyBankDto>>();
        try {
            RequestModel<TAuthapplyBankDto> requestModel = new RequestModel<TAuthapplyBankDto>();
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setRequest(pagination.getQueryBean());
            requestModel.getRequest().setAuthMethod("BANKCARD");
            ResponseModel<TAuthapplyBankDto> responseModel = authapplyBankInfoFacade.selectTAuthapplyBankDtoList(requestModel, MDC.get(LogIdInterceptor.log_id));
            if (responseModel.getResponseResult()) {
                pagination.setRowCount(responseModel.getTotal());
                pagination.setList(responseModel.getList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getResponseMessage());
            }
        } catch (Exception e) {
            log.error("查询异常！", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败！");
        }
        return responseDTO;
    }
}
