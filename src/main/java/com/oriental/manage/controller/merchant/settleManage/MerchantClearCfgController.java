package com.oriental.manage.controller.merchant.settleManage;

import com.oriental.clearDubbo.api.clear.cfg.ClearCfgInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.cfg.BankClearCfgModel;
import com.oriental.clearDubbo.model.clear.cfg.ClearCfgModel;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: wuxg
 * Date: 2016/5/30
 * Time: 16:20
 * Desc：商户结算配置
 */
@Slf4j
@Controller
@RequestMapping("/merchant/clearCfg")
public class MerchantClearCfgController {

    @Autowired
    private ClearCfgInterface clearCfgInterface;

    @RequestMapping("/init")
    public String init() {
        return "merchant/settleManage/searchClearCfg";
    }


    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-clearCfg_add")
    public String toAdd() {
        return "merchant/settleManage/addClearCfg";
    }

    @RequestMapping("toDetail")
    public String toDetail() {
        return "merchant/settleManage/clearCfgDetail";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-clearCfg_update")
    public String toUpdate() {
        return "merchant/settleManage/updateClearCfg";
    }

    @OperateLogger(content = "查询结算信息", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("merchant-clearCfg_search")
    public ResponseDTO<Pagination<ClearCfgModel>> search(Pagination<ClearCfgModel> pagination, ClearCfgModel baseModel) {
        RequestModel<ClearCfgModel> requestModel = new RequestModel();
        ResponseDTO<Pagination<ClearCfgModel>> responseDTO = new ResponseDTO<Pagination<ClearCfgModel>>();
        log.info("查询信息:{},{}", baseModel, pagination);
        try {
            requestModel.setRequest(baseModel);
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            ResponseModel<ClearCfgModel> response = clearCfgInterface.selectClearCfg(requestModel);
            log.info("查询商户结算配置：{}", response);
            if (response.getResponseResult()) {
                pagination.setRowCount(response.getOrgSumPageModel().getPageSum());
                pagination.setList(response.getList());
            }
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;

    }

    @OperateLogger(content = "修改结算信息", operationType = OperateLogger.OperationType.U, tables = "T_CLEAR_CFG")
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("merchant-clearCfg_update")
    public ResponseDTO<String> update(ResponseDTO<String> responseDTO, @RequestBody ClearCfgModel baseModel) {
        String errMsg = "";
        if(baseModel.getBankClearCfgList().size()>0){
            for(BankClearCfgModel bank:baseModel.getBankClearCfgList()){
                if(bank.getClrCycle()=="" || bank.getClrCycle() == null){
                    responseDTO.setSuccess(false);
                    responseDTO.setMsg("结算周期为空");
                    return responseDTO;
                }
            }
        }
        try {
            baseModel.setModifier(SessionUtils.getUserName());
            if ("03".equals(baseModel.getAuditStatus())) {
                baseModel.setAuditStatus("01");
            }
            ResponseModel<String> response = clearCfgInterface.updateClearCfg(baseModel);
            errMsg = response.getResponseMessage();
            responseDTO.setSuccess(response.getResponseResult());
            responseDTO.setMsg(errMsg);
            log.info("修改信息:{}", baseModel);
        } catch (Exception e) {
            log.error("更新失败", e);
            //responseDTO.setSuccess(false);
            responseDTO.setMsg(errMsg);
        }
        return responseDTO;

    }

    @RequestMapping("/updateItemEnableFlag")
    @RequiresPermissions(value = {"merchant-clearCfg_open", "merchant-clearCfg_close"}, logical = Logical.OR)
    @ResponseBody
    public ResponseDTO<String> updateItemEnableFlag(@RequestBody ClearCfgModel clearCfgModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();

        clearCfgModel.setAuditor(SessionUtils.getUserName());
        try {
            log.info("修改结算配置参数：", clearCfgModel);
            ResponseModel<String> response = clearCfgInterface.updateClearCfgEnableFlag(clearCfgModel);
            log.info("修改结果：", response);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("修改状态错误", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "新增结算信息", operationType = OperateLogger.OperationType.C, tables = "T_CLEAR_CFG")
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("merchant-clearCfg_add")
    public ResponseDTO<String> add(@RequestBody ClearCfgModel baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(baseModel.getBankClearCfgList().size()>0){
            for(BankClearCfgModel bank:baseModel.getBankClearCfgList()){
                if(bank.getClrCycle()=="" || bank.getClrCycle() == null){
                    responseDTO.setSuccess(false);
                    responseDTO.setMsg("结算周期为空");
                    return responseDTO;
                }
            }
        }
        baseModel.setCreator(SessionUtils.getUserName());
        log.info("新增信息:{}", baseModel);
        String errMsg = "";
        try {
            ResponseModel<String> response = clearCfgInterface.insertClearCfg(baseModel);
            errMsg = response.getResponseMessage();
            responseDTO.setSuccess(response.getResponseResult());
            responseDTO.setMsg(errMsg);
        } catch (Exception e) {
            log.error("新增失败", e);
            //responseDTO.setSuccess(false);
            responseDTO.setMsg(errMsg);
        }
        return responseDTO;

    }
}
