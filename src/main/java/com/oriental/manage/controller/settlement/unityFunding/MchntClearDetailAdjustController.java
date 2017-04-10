package com.oriental.manage.controller.settlement.unityFunding;

import com.oriental.clearDubbo.api.clear.detail.ManageClearInterface;
import com.oriental.clearDubbo.api.clear.detail.MchntManageClearInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.mchnt.MchntAdjustDetailModel;
import com.oriental.clearDubbo.model.clear.mchnt.MchntClearDetailSplitModel;
import com.oriental.clearDubbo.model.clear.sum.MchntPayOrgClearDetailModel;
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
@RequestMapping("settlement/unityFunding/mchntClearDetailAdjust")
public class MchntClearDetailAdjustController {

    @Autowired(required = false)
    private MchntManageClearInterface mchntManageClearInterface;



    @RequestMapping("queryFee")
    @ResponseBody
    public ResponseDTO queryAdjustFee(@RequestBody MchntAdjustDetailModel mchntAdjustDetailModel){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            MchntClearDetailSplitModel mchntClearDetailSplitModel = new MchntClearDetailSplitModel();
            mchntClearDetailSplitModel.setMchntCode(mchntAdjustDetailModel.getMchntCode());
            mchntClearDetailSplitModel.setClrDate(mchntAdjustDetailModel.getClrDate());
            mchntClearDetailSplitModel.setClrType(mchntAdjustDetailModel.getClrType());
            mchntClearDetailSplitModel.setMchntCodeSplit(mchntAdjustDetailModel.getMchntCodeSplit());
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(mchntClearDetailSplitModel);
            log.info("分账结算明细调整查询参数："+requestModel);
            ResponseModel<MchntAdjustDetailModel> responseModel = mchntManageClearInterface.selectSplitMchntAdjustDetail(requestModel);
            log.info("分账结算明细调整查询结果："+responseModel);
            if (responseModel.getResponseResult()) {
                mchntAdjustDetailModel = responseModel.getList().get(0);
                mchntAdjustDetailModel.setReceivableAmt(mchntAdjustDetailModel.getReceivableAmt().divide(new BigDecimal(100)));
                mchntAdjustDetailModel.setPayableAmt(mchntAdjustDetailModel.getPayableAmt().divide(new BigDecimal(100)));
                mchntAdjustDetailModel.setReFeeAmt(mchntAdjustDetailModel.getReFeeAmt().divide(new BigDecimal(100)));
                mchntAdjustDetailModel.setFeeAmt(mchntAdjustDetailModel.getFeeAmt().divide(new BigDecimal(100)));
                responseDTO.setSuccess(true);
                responseDTO.setObject(mchntAdjustDetailModel);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("未查询到分账结算明细调整数据！");
            }
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询分账结算明细调整数据异常！");
            log.error("查询分账结算明细调整数据异常", e);
        }

        return responseDTO;
    }



    @RequestMapping("query")
    @ResponseBody
    public ResponseDTO queryAdjust(@RequestBody MchntAdjustDetailModel mchntAdjustDetailModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            MchntPayOrgClearDetailModel mchntPayOrgClearDetailModel = new MchntPayOrgClearDetailModel();
            mchntPayOrgClearDetailModel.setMchntCode(mchntAdjustDetailModel.getMchntCode());
            mchntPayOrgClearDetailModel.setClrDate(mchntAdjustDetailModel.getClrDate());
            mchntPayOrgClearDetailModel.setClrType(mchntAdjustDetailModel.getClrType());
            mchntPayOrgClearDetailModel.setPayOrgCode(mchntAdjustDetailModel.getPayOrgCode());
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(mchntPayOrgClearDetailModel);
            log.info("支付机构明细调整查询参数："+requestModel);
            ResponseModel<MchntAdjustDetailModel> responseModel = mchntManageClearInterface.selectPayOrgCodeMchntAdjustDetail(requestModel);
            log.info("支付机构明细调整查询结果："+responseModel);
            if (responseModel.getResponseResult()) {
                mchntAdjustDetailModel = responseModel.getList().get(0);
                mchntAdjustDetailModel.setReceivableAmt(mchntAdjustDetailModel.getReceivableAmt().divide(new BigDecimal(100)));
                mchntAdjustDetailModel.setPayableAmt(mchntAdjustDetailModel.getPayableAmt().divide(new BigDecimal(100)));
                mchntAdjustDetailModel.setReFeeAmt(mchntAdjustDetailModel.getReFeeAmt().divide(new BigDecimal(100)));
                mchntAdjustDetailModel.setFeeAmt(mchntAdjustDetailModel.getFeeAmt().divide(new BigDecimal(100)));
                responseDTO.setSuccess(true);
                responseDTO.setObject(mchntAdjustDetailModel);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("未查询到商户调整数据！");
            }
        } catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询商户调整数据异常！");
            log.error("查询商户调整数据异常", e);
        }

        return responseDTO;
    }

    @RequestMapping("save")
    @ResponseBody
    public ResponseDTO saveAdjust(@RequestBody MchntAdjustDetailModel mchntAdjustDetailModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            mchntAdjustDetailModel.setManager(SessionUtils.getUserName());
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(mchntAdjustDetailModel);
            ResponseModel<MchntAdjustDetailModel> responseModel = new ResponseModel<MchntAdjustDetailModel>();
            if(mchntAdjustDetailModel.getId() > 0){
                responseModel = mchntManageClearInterface.updateMchntAdjustDetail(requestModel);
            }else {
                responseModel = mchntManageClearInterface.insertMchntAdjustDetail(requestModel);
            }
            if (responseModel.getResponseResult()) {
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("保存支付机构明细调整数据失败！");
            }
        } catch (Exception e) {
            log.error("保存支付机构明细调整数据异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("保存支付机构明细调整数据异常");
        }
        return responseDTO;
    }


    @RequestMapping("saveFee")
    @ResponseBody
    public ResponseDTO saveAdjustFee(@RequestBody MchntAdjustDetailModel mchntAdjustDetailModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            mchntAdjustDetailModel.setManager(SessionUtils.getUserName());
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(mchntAdjustDetailModel);
            ResponseModel<MchntAdjustDetailModel> responseModel = new ResponseModel<MchntAdjustDetailModel>();
            if(mchntAdjustDetailModel.getId() > 0){
                responseModel = mchntManageClearInterface.updateMchntAdjustDetail(requestModel);
            }else {
                responseModel = mchntManageClearInterface.insertMchntAdjustDetail(requestModel);
            }
            if (responseModel.getResponseResult()) {
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("保存分账结算明细调整数据失败！");
            }
        } catch (Exception e) {
            log.error("保存分账结算明细调整数据异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("保存分账结算明细调整数据异常");
        }
        return responseDTO;
    }
}
