package com.oriental.manage.controller.settlement.unityFunding;

import com.oriental.clearDubbo.api.clear.detail.ManageClearInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.mchnt.MchntAdjustDetailModel;
import com.oriental.clearDubbo.model.clear.pay.PayAdjustDetailModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDetailModel;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * Created by lupf on 2016/7/11.
 */
@Slf4j
@Controller
@RequestMapping("settlement/unityFunding/payClearDetailAdjust")
public class PayClearDetailAdjustController {

    @Autowired(required = false)
    private ManageClearInterface manageClearInterface;

    @RequestMapping("query")
    @ResponseBody
    public ResponseDTO queryAdjust(@RequestBody PayAdjustDetailModel payAdjustDetailModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrgClearDetailModel orgClearDetailModel = new OrgClearDetailModel();
            orgClearDetailModel.setOrgCode(payAdjustDetailModel.getPayOrgCode());
            orgClearDetailModel.setClrDate(payAdjustDetailModel.getClrDate());
            orgClearDetailModel.setClrBatchNo(payAdjustDetailModel.getClrBatchNo());
            orgClearDetailModel.setClrType(payAdjustDetailModel.getClrType());
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(orgClearDetailModel);
            log.info("机构周期结算经办调整查询参数："+requestModel);
            ResponseModel<PayAdjustDetailModel> responseModel = manageClearInterface.selectPayAdjustDetail(requestModel);
            log.info("机构周期结算经办调整查询结果："+responseModel);
            if (responseModel.getResponseResult()) {
                payAdjustDetailModel = responseModel.getList().get(0);
                payAdjustDetailModel.setReceivableAmt(payAdjustDetailModel.getReceivableAmt().divide(new BigDecimal(100)));
                payAdjustDetailModel.setPayableAmt(payAdjustDetailModel.getPayableAmt().divide(new BigDecimal(100)));
                payAdjustDetailModel.setReFeeAmt(payAdjustDetailModel.getReFeeAmt().divide(new BigDecimal(100)));
                payAdjustDetailModel.setFeeAmt(payAdjustDetailModel.getFeeAmt().divide(new BigDecimal(100)));
                responseDTO.setSuccess(true);
                responseDTO.setObject(payAdjustDetailModel);
            } else {
                responseDTO.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("查询经办调整失败", e);
        }

        return responseDTO;
    }

    @RequestMapping("save")
    @ResponseBody
    public ResponseDTO saveAdjust(@RequestBody PayAdjustDetailModel payAdjustDetailModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            payAdjustDetailModel.setManager(SessionUtils.getUserName());
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(payAdjustDetailModel);
            ResponseModel<PayAdjustDetailModel> responseModel = new ResponseModel<PayAdjustDetailModel>();
            log.info("机构周期结算经办调整操作参数："+requestModel);
            if(payAdjustDetailModel.getId() > 0){
                responseModel = manageClearInterface.updatePayAdjustDetail(requestModel);
            }else {
                responseModel = manageClearInterface.insertPayAdjustDetail(requestModel);
            }
            log.info("机构周期结算经办调整操作结果："+responseModel);
            if (responseModel.getResponseResult()) {
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("保存失败！");
            }
        } catch (Exception e) {
            log.error("保存经办调整失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("保存失败！");
        }
        return responseDTO;
    }
}
