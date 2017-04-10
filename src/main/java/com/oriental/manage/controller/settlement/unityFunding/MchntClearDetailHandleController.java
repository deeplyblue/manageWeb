package com.oriental.manage.controller.settlement.unityFunding;

import com.oriental.clearDubbo.api.clear.detail.ManageClearInterface;
import com.oriental.clearDubbo.api.clear.detail.MchntManageClearInterface;
import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.mchnt.MchntClearDetailSplitModel;
import com.oriental.clearDubbo.model.clear.sum.MchntManagerClearModel;
import com.oriental.clearDubbo.model.clear.sum.MchntPayOrgClearDetailModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDetailModel;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lupf on 2016/5/24.
 */
@Slf4j
@Controller
@RequestMapping("settlement/unityFunding/mchntClearDetail")
public class MchntClearDetailHandleController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private MchntManageClearInterface mchntManageClearInterface;
    @Autowired
    private ManageClearInterface manageClearInterface;

    @RequestMapping("init")
    @RequiresPermissions("mchntClearDetail_init")
    public String init() {return "settlement/unityFunding/mchntClearHandle";}


    @OperateLogger(content = "商户周期结算经办查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("mchntClearDetail_init")
    @ResponseBody
    public ResponseDTO<Pagination> query(Pagination pagination, MchntManagerClearModel mchntManagerClearModel,
                                         String beginDate, String endDate) {
        ResponseDTO<Pagination> responseDTO = new ResponseDTO<Pagination>();
        try {

            mchntManagerClearModel.setClrDateBegin(DateUtils.parse(beginDate));
            mchntManagerClearModel.setClrDateEnd(DateUtils.parse(endDate));
//            orgClearDetailModel.setClrDateBegin(DateUtils.parse(beginDate));
//            orgClearDetailModel.setClrDateEnd(DateUtils.parse(endDate));

            RequestModel<MchntManagerClearModel> requestModel = new RequestModel();

            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setRequest(mchntManagerClearModel);
            log.info("商户周期结算经办查询参数："+requestModel);
            ResponseModel<MchntManagerClearModel> responseModel = mchntManageClearInterface.selectManageMchntClear(requestModel);
//            //*********************测试数据
//                List<MchntManagerClearModel> list = responseModel.getList();
//                MchntManagerClearModel mchntManagerClearModel1 = list.get(1);
//                mchntManagerClearModel1.setClrRules("02");
//                MchntManagerClearModel mchntManagerClearModel2 = list.get(2);
//                mchntManagerClearModel2.setClrRules("03");
//                MchntManagerClearModel mchntManagerClearModel3 = list.get(3);
//                mchntManagerClearModel3.setClrType("03");
//                //分账数据
//                 List<MchntClearDetailSplitModel> mcList = new ArrayList<>();
//                    MchntClearDetailSplitModel mc = new MchntClearDetailSplitModel();
//                    mc.setMchntCode(mchntManagerClearModel2.getMchntCode());
//                    mc.setClrDate(new Date());
//                    mc.setEndDate(new Date());
//                    mc.setFeeAmt(new BigDecimal(1555));
//                    mc.setReFeeAmt(new BigDecimal(66666));
//                mcList.add(mc);
//                 mchntManagerClearModel1.setMchntClearDetailSplitModelList(mcList);


            //*********************测试数据
            log.info("商户周期结算经办查询结果："+responseModel);
            OrgSumPageModel orgSumPageModel = responseModel.getOrgSumPageModel();
            //noinspection Duplicates
            if (responseModel.getResponseResult()) {
                pagination.setRowCount(orgSumPageModel.getPageSum());
                pagination.setList(responseModel.getList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
                responseDTO.setSumObject(orgSumPageModel);
            } else {
                responseDTO.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("操作失败", e);
        }

        return responseDTO;
    }

    @RequestMapping("toClearDetail")
    @RequiresPermissions("mchntClearDetail_toAuditDetail")
    public String toClearDetail() {

        return "settlement/unityFunding/mchntClearHandleDetail";
    }

    @RequestMapping("toFeeDetail")
    public String toFeeDetail(){
        return "settlement/unityFunding/mchntFeeHandle";
    }



    @RequestMapping("queryClearDetail")
    @RequiresPermissions("mchntClearDetail_toAuditDetail")
    @ResponseBody
    public ResponseDTO queryClearDetail(OrgClearDetailModel orgClearDetailModel,
                                        String beginDate, String endDate) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            orgClearDetailModel.setClrDateBegin(DateUtils.parse(beginDate));
            orgClearDetailModel.setClrDateEnd(DateUtils.parse(endDate));

            RequestModel requestModel = new RequestModel();
            requestModel.setPageSize(10000);
            requestModel.setRequest(orgClearDetailModel);
            log.info("商户周期结算经办明细查询参数："+requestModel);
            ResponseModel responseModel = manageClearInterface.selectManageMchntClearDetail(requestModel);
            log.info("商户周期结算经办明细查询结果："+responseModel);
            if (responseModel.getResponseResult()) {
                responseDTO.setSuccess(true);
                Pagination pagination = new Pagination();
                pagination.setList(responseModel.getList());
                responseDTO.setObject(pagination);
            } else {
                responseDTO.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }
    @RequiresPermissions("mchntClearDetail_audit")
    @OperateLogger(content = "按支付机构明细经办",operationType = OperateLogger.OperationType.U)
    @RequestMapping("handle")
    @ResponseBody
    public ResponseDTO handle( @RequestBody MchntManagerClearModel mchntManagerClearModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
//            MchntManagerClearModel mchntManagerClearModel = new MchntManagerClearModel();
            RequestModel requestModel = new RequestModel();
            List<MchntPayOrgClearDetailModel> list = new ArrayList<MchntPayOrgClearDetailModel>();
            mchntManagerClearModel.setManager(SessionUtils.getUserName());
            //noinspection Duplicates
//            for (String check : checked) {
//                MchntPayOrgClearDetailModel mchntPayOrgClearDetailModel = new MchntPayOrgClearDetailModel();
////                mchntPayOrgClearDetailModel.setClrBatchNo(clrBatchNo);
//                String[] temp = check.split("#");
//                mchntPayOrgClearDetailModel.setMchntCode(temp[0]);
//                mchntPayOrgClearDetailModel.setClrType(temp[1]);
//                mchntPayOrgClearDetailModel.setClrDate(DateUtils.parse(temp[2]));
//                mchntPayOrgClearDetailModel.setManager(SessionUtils.getUserName());
//                list.add(mchntPayOrgClearDetailModel);
//            }
//            mchntManagerClearModel.setMchntPayOrgClearDetailModelList(list);
            requestModel.setRequest(mchntManagerClearModel);
            log.info("按支付机构明细经办操作参数："+requestModel);
            ResponseModel responseModel = mchntManageClearInterface.updateManageMchntClearDetail(requestModel);
            log.info("按支付机构明细经办操作结果："+responseModel);
            if (responseModel.getResponseResult()) {
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("经办失败", e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }


    @OperateLogger(content = "按分账结算明细经办",operationType = OperateLogger.OperationType.U)
    @RequestMapping("/feeHandle")
    @ResponseBody
    public ResponseDTO FeeHandle( @RequestBody MchntManagerClearModel mchntManagerClearModel
    ) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
//            MchntManagerClearModel mchntManagerClearModel = new MchntManagerClearModel();
            RequestModel requestModel = new RequestModel();
            mchntManagerClearModel.setManager(SessionUtils.getUserName());
            requestModel.setRequest(mchntManagerClearModel);
            log.info("按分账结算明细经办操作参数："+requestModel);
            ResponseModel responseModel = mchntManageClearInterface.updateManageMchntClearDetail(requestModel);
            log.info("按分账结算明细经办操作结果："+responseModel);
            if (responseModel.getResponseResult()) {
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("经办失败", e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }



}
