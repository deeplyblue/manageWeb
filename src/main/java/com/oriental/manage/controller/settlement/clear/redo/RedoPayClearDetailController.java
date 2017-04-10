package com.oriental.manage.controller.settlement.clear.redo;

import com.oriental.clearDubbo.api.clear.redo.RedoClearDetailInterface;
import com.oriental.clearDubbo.model.base.CompanyModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangxinhai on 2016/5/23.
 */
@Slf4j
@Controller
@RequestMapping("settlement/clear/redo/redoPayClearDetail")
public class RedoPayClearDetailController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RedoClearDetailInterface redoClearDetailInterface;

    @RequestMapping("init")
    @RequiresPermissions("redoPayClearDetail_search")
    public String init() {

        return "settlement/clear/redo/redoPayClearDetail";
    }

    @OperateLogger(content = "机构清结算",operationType = OperateLogger.OperationType.R)
    @RequestMapping("notarize")
    @RequiresPermissions("redoPayClearDetail_search")
    @ResponseBody
    public ResponseDTO queryPage(CompanyModel companyModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            ResponseModel responseModel = new ResponseModel();
            companyModel.setCompanyType("01");
            companyModel.setSettleType("02");
            log.info("机构清结算参数："+companyModel);
            responseModel = redoClearDetailInterface.redoPayClearDetail(companyModel);
            log.info("机构清结算结果："+responseModel);
            if (responseModel.getResponseResult()) {
                responseDTO.setSuccess(true);
                responseDTO.setMsg("操作成功！");
            } else {
                responseDTO.setSuccess(false);
            }
        }catch (Exception e){
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败！");
        }
        return responseDTO;
    }


}
