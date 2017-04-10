package com.oriental.manage.controller.settlement.unityFunding;

import com.oriental.clearDubbo.api.clear.detail.ManageClearInterface;
import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDetailModel;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by lupf on 2016/5/24.
 */
@Slf4j
@Controller
@RequestMapping("settlement/unityFunding/orgClearDetail")
public class OrgClearDetailHandleController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ManageClearInterface manageClearInterface;
    @Autowired
    private FileDownAjax fileDownAjax;

    @RequestMapping("init")
    @RequiresPermissions("orgClearDetail_init")
    public String init() {
        return "settlement/unityFunding/orgClearHandle";
    }

    @OperateLogger(content = "机构周期结算经办查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("orgClearDetail_init")
    @ResponseBody
    public ResponseDTO<Pagination> query(Pagination pagination, OrgClearDetailModel orgClearDetailModel,
                                         String beginDate, String endDate) {
        ResponseDTO<Pagination> responseDTO = new ResponseDTO<Pagination>();
        try {
            orgClearDetailModel.setClrDateBegin(DateUtils.parse(beginDate));
            orgClearDetailModel.setClrDateEnd(DateUtils.parse(endDate));

            RequestModel requestModel = new RequestModel();
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setRequest(orgClearDetailModel);
            log.info("机构周期结算经办查询参数："+requestModel);
            ResponseModel<OrgClearDetailModel> responseModel
                    = manageClearInterface.selectManagePayClear(requestModel);
            log.info("机构周期结算经办查询结果："+responseModel);
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
    @RequiresPermissions("orgClearDetail_toClearDetail")
    public String toClearDetail() {

        return "settlement/unityFunding/orgClearHandleDetail";
    }
    @OperateLogger(content = "机构周期结算明细查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryClearDetail")
    @RequiresPermissions("orgClearDetail_toClearDetail")
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
            log.info("机构周期结算经办明细查询参数："+requestModel);
            ResponseModel responseModel = manageClearInterface.selectManagePayClearDetail(requestModel);
            log.info("机构周期结算经办明细查询结果："+responseModel);
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

    @RequiresPermissions("orgClear_audit")
    @OperateLogger(content = "机构周期结算经办操作",operationType = OperateLogger.OperationType.U)
    @RequestMapping("handle")
    @ResponseBody
    public ResponseDTO handle(String clrBatchNo,
                              @RequestParam(value = "checked[]") String[] checked) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RequestModel requestModel = new RequestModel();
            List<OrgClearDetailModel> list = new ArrayList<OrgClearDetailModel>();
            for (String check : checked) {
                OrgClearDetailModel orgClearDetailModel = new OrgClearDetailModel();
                orgClearDetailModel.setClrBatchNo(clrBatchNo);
                String[] temp = check.split("#");
                orgClearDetailModel.setOrgCode(temp[0]);
                orgClearDetailModel.setClrType(temp[1]);
                orgClearDetailModel.setClrDate(DateUtils.parse(temp[2]));
                orgClearDetailModel.setManager(SessionUtils.getUserName());
                list.add(orgClearDetailModel);
            }
            requestModel.setRequest(list);
            log.info("机构周期结算经办操作参数："+requestModel);
            ResponseModel responseModel = manageClearInterface.updateManagePayClearDetail(requestModel);
            log.info("机构周期结算经办操作结果："+responseModel);
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

    @RequiresPermissions("orgClear_down")
    @RequestMapping("download")
    @ResponseBody
    public ResponseEntity<byte[]> download(OrgClearDetailModel orgClearDetailModel,
                                           String beginDate, String endDate){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try{
            tempFile = fileDownAjax.touch("结算明细-" + DateUtils.now() + ".xlsx");
            orgClearDetailModel.setClrDateBegin(DateUtils.parse(beginDate));
            orgClearDetailModel.setClrDateEnd(DateUtils.parse(endDate));

            RequestModel requestModel = new RequestModel();
            requestModel.setPageSize(10000);
            requestModel.setRequest(orgClearDetailModel);
            ResponseModel responseModel = manageClearInterface.selectManagePayClearDetail(requestModel);
                //表头
                LinkedList<Header> tHeaders = new LinkedList<>();
                //内容
                List<Object> datas = new ArrayList<>();
                tHeaders.add(new HeaderExt("序号", "index"));
                tHeaders.add(new HeaderExt("商户", "orgCode"));
                tHeaders.add(new HeaderExt("结算类型", "clrType"));
                tHeaders.add(new HeaderExt("应付金额", "transAmtP"));
                tHeaders.add(new HeaderExt("笔数", "transCountP"));
                tHeaders.add(new HeaderExt("应收金额", "transAmtR"));
                tHeaders.add(new HeaderExt("笔数", "transCountR"));
                tHeaders.add(new HeaderExt("结算净额", "index"));
                tHeaders.add(new HeaderExt("应收佣金", "feeAmtR"));
                tHeaders.add(new HeaderExt("应付佣金", "feeAmtP"));
                tHeaders.add(new HeaderExt("应付净额", "index"));
                tHeaders.add(new HeaderExt("开始时间", "settleDateBegin"));
                tHeaders.add(new HeaderExt("结束时间", "settleDateEnd"));
                tHeaders.add(new HeaderExt("报表类型", "reportType"));
                tHeaders.add(new HeaderExt("经办批次号", "handleBatchNo"));
                tHeaders.add(new HeaderExt("经办人", "manager"));
                tHeaders.add(new HeaderExt("调整", "clrAdjustFlag"));
                tHeaders.add(new HeaderExt("结算", "clrFlag"));

                Map map = Bean2MapUtil.convertBean(responseModel);
                map.put("index", 1);
                datas.add(map);
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);

                ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                POIExcelApi.getInstance().write(outputStream, ca);

                responseEntity = fileDownAjax.getResponseEntity(tempFile);
        }catch (Exception e){
            log.error("下载失败",e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }

}
