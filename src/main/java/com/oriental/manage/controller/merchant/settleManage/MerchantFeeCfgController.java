package com.oriental.manage.controller.merchant.settleManage;

import com.oriental.clearDubbo.api.settle.fee.FeeCfgInterface;
import com.oriental.clearDubbo.api.settle.fee.FeeCfgTemplateInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.settle.fee.FeeCfgModel;
import com.oriental.clearDubbo.model.settle.fee.FeeCfgTemplateModel;
import com.oriental.manage.core.exception.BusiException;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Author: wuxg
 * Date: 2016/5/30
 * Time: 16:20
 * Desc：商户手续费配置
 */
@Slf4j
@Controller
@RequestMapping("/merchant/feeCfg")
public class MerchantFeeCfgController {

    @Autowired
    private FeeCfgInterface feeCfgInterface;

    @Autowired
    private FeeCfgTemplateInterface feeCfgTemplateInterface;

    @RequestMapping("/init")
    public String init() {
        return "merchant/settleManage/searchFeeCfg";
    }


    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-feeCfg_add")
    public String toAdd() {
        return "merchant/settleManage/addFeeCfg";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-feeCfg_update")
    public String toUpdate() {
        return "merchant/settleManage/updateFeeCfg";
    }

    @OperateLogger(content = "查询商户手续费", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions(value = {"merchant-feeCfg_search", "org-feeCfg_select"}, logical = Logical.OR)
    public ResponseDTO<Pagination<FeeCfgModel>> search(Pagination<FeeCfgModel> pagination, FeeCfgModel baseModel) {
        ResponseDTO<Pagination<FeeCfgModel>> responseDTO=new ResponseDTO<Pagination<FeeCfgModel>>();
        log.info("查询信息:{},{}",baseModel,pagination);
        try{
            RequestModel<FeeCfgModel> requestModel = new RequestModel<FeeCfgModel>();
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setRequest(baseModel);
            log.info("查询商户手续费配置:{}", baseModel);
            ResponseModel<FeeCfgModel> response = feeCfgInterface.getFeeCfg(requestModel);
            if (response.getResponseResult()) {
                pagination.setRowCount(response.getOrgSumPageModel().getPageSum());
                pagination.setList(response.getList());
            }
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;


    }

    @OperateLogger(content = "修改商户手续费", operationType = OperateLogger.OperationType.U, tables = "T_FEE_CFG_TEMPLATE")
    @RequiresPermissions("merchant-feeCfg_update")
    protected void update(ResponseDTO<String> responseDTO, FeeCfgModel baseModel) {
        throw new BusiException("", "不能修改");
    }

    @OperateLogger(content = "修改商户手续费", operationType = OperateLogger.OperationType.U, tables = "T_FEE_CFG_TEMPLATE")
    @RequestMapping("/updateFeeCfg")
    @ResponseBody
    public ResponseDTO<String> updateBase(@RequestBody FeeCfgModel feeCfgModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            feeCfgModel.setModifier(SessionUtils.getUserName());
            ResponseModel<String> response = feeCfgInterface.updateFeeCfg(feeCfgModel);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("跟新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }


    @OperateLogger(content = "新增商户手续费", operationType = OperateLogger.OperationType.C, tables = "T_FEE_CFG_TEMPLATE")
    @RequestMapping("/addFeeCfg")
    @ResponseBody
    @RequiresPermissions("merchant-feeCfg_add")
    public ResponseDTO<String> addBase(@RequestBody FeeCfgModel feeCfgModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            feeCfgModel.setCreator(SessionUtils.getUserName());
            ResponseModel<String> response = feeCfgInterface.insertFeeCfg(feeCfgModel);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }


    @OperateLogger(content = "初始化手续费模版", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/initTemp")
    @ResponseBody
    public ResponseDTO<Map<String, FeeCfgTemplateModel>> initTemp(String companyType) {
        ResponseDTO<Map<String, FeeCfgTemplateModel>> responseDTO = new ResponseDTO<Map<String, FeeCfgTemplateModel>>();
        try {
            FeeCfgTemplateModel feeCfgTemplateModel = new FeeCfgTemplateModel();
            feeCfgTemplateModel.setCompanyType(companyType);
            ResponseModel<FeeCfgTemplateModel> response = feeCfgTemplateInterface.getFeeCfgTemplate(feeCfgTemplateModel);
            if (response.getResponseResult()) {
                responseDTO.setSuccess(true);
                Map<String, FeeCfgTemplateModel> modelMap = new HashMap<String, FeeCfgTemplateModel>();
                for (FeeCfgTemplateModel item : response.getList()) {
                    modelMap.put(String.valueOf(item.getId()), item);
                }
                responseDTO.setObject(modelMap);
                return responseDTO;
            }
        } catch (Exception e) {
            log.error("初始化手续费模版失败", e);
        }
        responseDTO.setSuccess(false);
        return responseDTO;
    }

    @RequestMapping("/updateItemEnableFlag")
    @ResponseBody
    @RequiresPermissions(value = {"merchant-feeCfg_open", "merchant-feeCfg_close"}, logical = Logical.OR)
    public ResponseDTO<String> updateItemEnableFlag(@RequestBody FeeCfgModel baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            baseModel.setModifier(SessionUtils.getUserName());
            ResponseModel<String> response = feeCfgInterface.updateFeeCfgEnableFlag(baseModel);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("开启关闭状态失败:", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败！");
        }
        return responseDTO;
    }


}
