package com.oriental.manage.controller.merchant.settleManage;


import com.oriental.clearDubbo.api.settle.fee.FeeCfgTemplateInterface;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.settle.fee.FeeCfgTemplateModel;
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
 * Author: wuxg.
 * Date: 2016/5/30
 * Time: 16:20
 * Desc：商户手续费模板配置
 */
@Slf4j
@Controller
@RequestMapping("/merchant/feeTemplate")
public class MerchantFeeTemplateController  {

    @Autowired
    private FeeCfgTemplateInterface feeCfgTemplateInterface;

    @RequestMapping("/init")
    public String init() {
        return "merchant/settleManage/searchFeeTemplateInfo";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-feeTemplate_add")
    public String toAdd() {
        return "merchant/settleManage/addFeeTemplateInfo";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-feeTemplate_update")
    public String toUpdate() {
        return "merchant/settleManage/updateFeeTemplateInfo";
    }

    @OperateLogger(content = "查询手续费模版", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("merchant-feeTemplate_search")
    public ResponseDTO<Pagination<FeeCfgTemplateModel>> search(Pagination<FeeCfgTemplateModel> pagination, FeeCfgTemplateModel baseModel) {

        ResponseDTO<Pagination<FeeCfgTemplateModel>> responseDTO=new ResponseDTO<Pagination<FeeCfgTemplateModel>>();
        log.info("查询信息:{},{}",baseModel,pagination);
        try{
            ResponseModel<FeeCfgTemplateModel> response = feeCfgTemplateInterface.getFeeCfgTemplate(baseModel);
            log.info("查询模版配置信息:{}", response);
            if (response.getResponseResult()) {
                pagination.setRowCount(response.getList().size());
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

    @OperateLogger(content = "新增手续费模版", operationType = OperateLogger.OperationType.C, tables = "T_FEE_CFG_TEMPLATE")
    @RequestMapping("/addCfgTemp")
    @ResponseBody
    @RequiresPermissions(value = {"merchant-feeTemplate_add", "org-feeTemplate_add"}, logical = Logical.OR)
    public ResponseDTO<String> addBase(@RequestBody FeeCfgTemplateModel feeCfgTemplateModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            feeCfgTemplateModel.setCreator(SessionUtils.getUserName());
            log.info("增加商户清算模版：{}", feeCfgTemplateModel);
            ResponseModel<String> response = feeCfgTemplateInterface.insertFeeCfgTemplate(feeCfgTemplateModel);
            if (response.getResponseResult()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("新增成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getResponseMessage());
            }
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改手续费模版", operationType = OperateLogger.OperationType.U, tables = "T_FEE_CFG_TEMPLATE")
    @RequestMapping("/updateCfgTemp")
    @ResponseBody
    @RequiresPermissions(value = {"merchant-feeTemplate_update", "org-feeTemplate_update"}, logical = Logical.OR)
    public ResponseDTO<String> updateBase(@RequestBody FeeCfgTemplateModel feeCfgTemplateModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            feeCfgTemplateModel.setModifief(SessionUtils.getUserName());
            log.info("修改商户模版配置:{}", feeCfgTemplateModel);
            ResponseModel<String> response = feeCfgTemplateInterface.updateFeeCfgTemplate(feeCfgTemplateModel);
            if (response.getResponseResult()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("修改成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getResponseMessage());
            }
        } catch (Exception e) {
            log.error("跟新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
