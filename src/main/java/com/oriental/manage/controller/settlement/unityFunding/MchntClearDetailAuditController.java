package com.oriental.manage.controller.settlement.unityFunding;

import com.oriental.clearDubbo.api.clear.detail.AuditorClearInterface;
import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.mchnt.MchntClearModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDetailModel;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lupf on 2016/5/24.
 */
@Slf4j
@Controller
@RequestMapping("settlement/unityFunding/mchntClearDetailAudit")
public class MchntClearDetailAuditController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AuditorClearInterface auditorClearInterface;

    @RequestMapping("init")
    @RequiresPermissions("mchntClearDetailAudit_search")
    public String init() {
        return "settlement/unityFunding/mchntClearAudit";
    }

    @OperateLogger(content = "商户周期结算审核查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("mchntClearDetailAudit_search")
    @ResponseBody
    public ResponseDTO<Pagination> query(Pagination pagination, MchntClearModel mchntClearModel,
                                         String beginDate, String endDate) {
        ResponseDTO<Pagination> responseDTO = new ResponseDTO<Pagination>();
        try {
            if (StringUtils.isNotBlank(beginDate)) {
                mchntClearModel.setManageDateBegin(DateUtils.parse(beginDate));
            }
            if (StringUtils.isNotBlank(endDate)) {
                mchntClearModel.setManageDateEnd(DateUtils.parse(endDate));
            }

            RequestModel requestModel = new RequestModel();
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setRequest(mchntClearModel);
            log.info("商户周期结算查询参数："+requestModel);
            ResponseModel responseModel = auditorClearInterface.selectAuditorMchntClear(requestModel);
            log.info("商户周期结算查询结果："+responseModel);
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

    @RequestMapping("toAuditDetail")
    @RequiresPermissions("mchntClearDetailAudit_toAuditDetail")
    public String toAuditDetail() {

        return "settlement/unityFunding/mchntClearAuditDetail";
    }

    @OperateLogger(content = "商户周期结算审核明细查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryAuditDetail")
    @RequiresPermissions("mchntClearDetailAudit_toAuditDetail")
    @ResponseBody
    public ResponseDTO queryAuditDetail(MchntClearModel mchntClearModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {

            RequestModel requestModel = new RequestModel();
            requestModel.setPageSize(10000);
            requestModel.setRequest(mchntClearModel);
            log.info("商户周期结算审核明细查询参数："+requestModel);
            ResponseModel responseModel = auditorClearInterface.selectAuditorMchntClearDetail(requestModel);
            log.info("商户周期结算审核明细查询结果："+responseModel);
            //noinspection Duplicates
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

    @RequiresPermissions("mchntClearDetailAudit_auditSuccese")
    @OperateLogger(content = "商户周期结算明细审核操作",operationType = OperateLogger.OperationType.U)
    @RequestMapping("audit")
    @ResponseBody
    public ResponseDTO audit(MchntClearModel mchntClearModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RequestModel requestModel = new RequestModel();
            mchntClearModel.setAuditor(SessionUtils.getUserName());
            requestModel.setRequest(mchntClearModel);
            log.info("商户周期结算审核明细审核参数："+requestModel);
            ResponseModel responseModel = auditorClearInterface.updateAuditorMchntClearDetail(requestModel);
            log.info("商户周期结算审核明细审核结果："+responseModel);
            responseDTO.setSuccess(responseModel.getResponseResult());
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequiresPermissions("mchntClearDetailAudit_auditFail")
    @RequestMapping("auditFail")
    @ResponseBody
    public ResponseDTO auditFail(@RequestBody MchntClearModel mchntClearModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RequestModel requestModel = new RequestModel();
            mchntClearModel.setAuditor(SessionUtils.getUserName());
            requestModel.setRequest(mchntClearModel);
            log.info("商户手续费周期结算审核明细审核参数："+requestModel);
            ResponseModel responseModel = auditorClearInterface.deleteAuditorMchntClear(requestModel);
            log.info("商户手续费周期结算审核明细审核结果："+responseModel);
            responseDTO.setSuccess(responseModel.getResponseResult());
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

}
