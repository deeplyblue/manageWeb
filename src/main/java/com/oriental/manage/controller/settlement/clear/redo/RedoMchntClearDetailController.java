package com.oriental.manage.controller.settlement.clear.redo;

import com.oriental.clearDubbo.api.clear.redo.RedoClearDetailInterface;
import com.oriental.clearDubbo.model.base.CompanyModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDetailModel;
import com.oriental.manage.core.paging.Pagination;
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
@RequestMapping("settlement/clear/redo/redoMchntClearDetail")
public class RedoMchntClearDetailController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RedoClearDetailInterface redoClearDetailInterface;

    @RequestMapping("init")
    @RequiresPermissions("redoMchntClearDetail_search")
    public String init() {

        return "settlement/clear/redo/redoMchntClearDetail";
    }

    @OperateLogger(content = "商户清结算",operationType = OperateLogger.OperationType.R)
    @RequestMapping("notarize")
    @RequiresPermissions("redoMchntClearDetail_search")
    @ResponseBody
    public ResponseDTO queryPage(CompanyModel companyModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            ResponseModel responseModel = new ResponseModel();
            companyModel.setCompanyType("03");
            companyModel.setSettleType("02");
            log.info("商户清结算参数："+companyModel);
            responseModel = redoClearDetailInterface.redoMchntClearDetail(companyModel);
            log.info("商户清结算结果："+responseModel);
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
