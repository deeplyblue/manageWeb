package com.oriental.manage.controller.settlement.unityFunding;

import com.oriental.clearDubbo.api.clear.detail.AuditorClearInterface;
import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.mchnt.MchntClearModel;
import com.oriental.clearDubbo.model.clear.pay.PayClearModel;
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
@RequestMapping("settlement/unityFunding/orgClearDetailAudit")
public class OrgClearDetailAuditController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AuditorClearInterface auditorClearInterface;

    @RequestMapping("init")
    @RequiresPermissions("orgClearDetailAudit_init")
    public String init() {
        return "settlement/unityFunding/orgClearAudit";
    }

    @OperateLogger(content = "机构周期结算审核查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("orgClearDetailAudit_init")
    public ResponseDTO<Pagination> query(Pagination pagination, PayClearModel payClearModel,
                                         String beginDate, String endDate) {
        ResponseDTO<Pagination> responseDTO = new ResponseDTO<Pagination>();
        try {
            if (StringUtils.isNotBlank(beginDate)) {
                payClearModel.setManageDateBegin(DateUtils.parse(beginDate));
            }
            if (StringUtils.isNotBlank(endDate)) {
                payClearModel.setManageDateEnd(DateUtils.parse(endDate));
            }

            RequestModel requestModel = new RequestModel();
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setRequest(payClearModel);
            log.info("机构周期结算审核查询参数："+requestModel);
            ResponseModel responseModel = auditorClearInterface.selectAuditorPayClear(requestModel);
            log.info("机构周期结算审核查询结果："+responseModel);
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
    @RequiresPermissions("orgClearDetailAudit_toAuditDetail")
    public String toAuditDetail() {

        return "settlement/unityFunding/orgClearAuditDetail";
    }

    @RequestMapping("queryAuditDetail")
    @ResponseBody
    @RequiresPermissions("orgClearDetailAudit_toAuditDetail")
    public ResponseDTO queryAuditDetail(PayClearModel payClearModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {

            RequestModel requestModel = new RequestModel();
            requestModel.setPageSize(10000);
            requestModel.setRequest(payClearModel);
            log.info("机构周期结算审核明细参数："+requestModel);
            ResponseModel responseModel = auditorClearInterface.selectAuditorPayClearDetail(requestModel);
            log.info("机构周期结算审核明细结果："+responseModel);
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
    @RequiresPermissions("orgClearDetail_auditSuccese")
    @OperateLogger(content = "机构周期结算审核操作",operationType = OperateLogger.OperationType.U)
    @RequestMapping("audit")
    @ResponseBody
    public ResponseDTO audit(PayClearModel payClearModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(payClearModel);
            log.info("机构周期结算审核操作参数："+requestModel);
            ResponseModel responseModel = auditorClearInterface.updateAuditorPayClearDetail(requestModel);
            log.info("机构周期结算审核操作结果："+responseModel);
            responseDTO.setSuccess(responseModel.getResponseResult());
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequiresPermissions("orgClearDetail_auditFail")
    @RequestMapping("auditFail")
    @ResponseBody
    public ResponseDTO auditFail(@RequestBody PayClearModel payClearModel) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RequestModel requestModel = new RequestModel();
            payClearModel.setAuditor(SessionUtils.getUserName());
            requestModel.setRequest(payClearModel);
            log.info("机构手续费周期结算审核操作参数："+requestModel);
            ResponseModel responseModel = auditorClearInterface.deleteAuditorPayClearDetail(requestModel);
            log.info("机构手续费周期结算审核操作结果："+responseModel);
            responseDTO.setSuccess(responseModel.getResponseResult());
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

}
