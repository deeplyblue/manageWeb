package com.oriental.manage.controller.settlement.clear.redo;

import com.oriental.clearDubbo.api.clear.detail.ClearDetailInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDetailModel;
import com.oriental.manage.core.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 银行强制结算
 * Created by zhangxinhai on 2016/7/29.
 */
@Slf4j
@Controller
@RequestMapping("settlement/clear/redo/coercePayClearDetail")
public class CoercePayClearDetailController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ClearDetailInterface clearDetailInterface;

    @RequestMapping("init")
    @RequiresPermissions("coercePayClearDetail_search")
    public String init() {

        return "settlement/clear/redo/coercePayClearDetail";
    }


    @RequiresPermissions("coercePayClearDetail_search")
    @RequestMapping("notarize")
    @ResponseBody
    public ResponseDTO queryPage(@RequestBody OrgClearDetailModel orgClearDetailModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            RequestModel<OrgClearDetailModel> requestModel = new RequestModel<OrgClearDetailModel>();
            requestModel.setRequest(orgClearDetailModel);
            ResponseModel<OrgClearDetailModel> responseModel = new ResponseModel<OrgClearDetailModel>();
            log.info("机构强制结算参数："+requestModel);
            responseModel = clearDetailInterface.coercePayClearDetailModel(requestModel);
            log.info("机构强制结算结果："+responseModel);
            if (responseModel.getResponseResult()) {
                responseDTO.setSuccess(true);
                responseDTO.setMsg("操作成功！");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getResponseMessage());
            }
        }catch (Exception e){
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败！");
        }
        return responseDTO;
    }

}
