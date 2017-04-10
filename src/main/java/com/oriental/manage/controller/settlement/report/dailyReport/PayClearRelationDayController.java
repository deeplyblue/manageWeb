package com.oriental.manage.controller.settlement.report.dailyReport;

import com.oriental.clearDubbo.api.clear.report.ClearRelationInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.mchnt.MchntClearRelationModel;
import com.oriental.clearDubbo.model.clear.pay.PayClearRelationModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDetailModel;
import com.oriental.manage.core.excelUtils.ExcelUtils;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by lupf on 2016/6/12.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/daily/payRelation")
public class PayClearRelationDayController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ClearRelationInterface clearRelationInterface;
    @Autowired
    private FileDownAjax fileDownAjax;

    @RequestMapping("init")
    @RequiresPermissions("payRelation_search")
    public String init() {

        return "settlement/report/dailyReport/payClearRelationDaily";
    }

    @OperateLogger(content = "查询机构结算关系--日报", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("payRelation_search")
    @ResponseBody
    public ResponseDTO search(@RequestBody Pagination<OrgClearDetailModel> pagination) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RequestModel requestModel = new RequestModel();
            OrgClearDetailModel orgClearDetailModel = pagination.getQueryBean();
            orgClearDetailModel.setClrType("02");
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setRequest(orgClearDetailModel);
            log.info("机构结算关系报表查询参数：" + requestModel);
            ResponseModel<PayClearRelationModel> responseModel =
                    clearRelationInterface.getPayClearRelation(requestModel);
            log.info("机构结算关系报表查询结果：" + responseModel);
            if (responseModel.getResponseResult()) {
                Pagination page = new Pagination();
                responseDTO.setSuccess(true);
                page.setPageNum(pagination.getPageNum());
                page.setRowCount(responseModel.getOrgSumPageModel().getPageSum());
                page.setPageSize(pagination.getPageSize());
                page.setList(responseModel.getList());
                responseDTO.setSumObject(responseModel.getOrgSumPageModel());
                responseDTO.setObject(page);
            } else {
                responseDTO.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("查询机构结算关系--日报，失败！", e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }

    @RequiresPermissions("payRelation_down")
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestBody OrgClearDetailModel orgClearDetailModel) {

        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            tempFile = fileDownAjax.touch("机构结算关系日报表-" + DateUtils.now() + ".xlsx");
            String beginDate = DateUtils.format(orgClearDetailModel.getClrDateBegin(),DateUtils.DATESHORTFORMAT);
            String endDate = DateUtils.format(orgClearDetailModel.getClrDateEnd(),DateUtils.DATESHORTFORMAT);
            RequestModel<OrgClearDetailModel> requestModel = new RequestModel<>();
            requestModel.setRequest(orgClearDetailModel);
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);
            ResponseModel<PayClearRelationModel> responseModel =
                    clearRelationInterface.getPayClearRelation(requestModel);
            if (responseModel.getResponseResult()) {
                LinkedList<Header> tHeaders = new LinkedList<>();
                List<Object> datas = new ArrayList<>();

                tHeaders.add(new HeaderExt("序号", "index"));
                tHeaders.add(new HeaderExt("机构", "payOrgCode", ExcelContentExt.ORGANIZE));
                tHeaders.add(new HeaderExt("应收本金", "receivableAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("应收日期", "clearDate"));
                tHeaders.add(new HeaderExt("实收日期", "cashTransDateR"));
                tHeaders.add(new HeaderExt("应退本金", "payableAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("实退日期", "cashTransDateP"));
                tHeaders.add(new HeaderExt("商户", "orgCode", ExcelContentExt.MCHNT));
                tHeaders.add(new HeaderExt("应付本金", "transAmtP", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("是否已收", "cashTransFlag", ExcelContentExt.CASH_TRANS_FLAG));

                int i = 0;
                for (PayClearRelationModel relationModel : responseModel.getList()) {
                    i++;
                    for (OrgClearDetailModel detailModel : relationModel.getOrgClearDetailModelList()) {
                        Map map = Bean2MapUtil.convertBean(detailModel);
                        map.put("payOrgCode", relationModel.getPayOrgCode());
                        map.put("receivableAmt", relationModel.getReceivableAmt());
                        map.put("clearDate", relationModel.getClearDate());
                        map.put("cashTransDateR", relationModel.getCashTransDateR());
                        map.put("payableAmt", relationModel.getPayableAmt());
                        map.put("cashTransDateP", relationModel.getCashTransDateP());
                        map.put("index", i);
                        datas.add(map);
                    }
                    if (relationModel.getOrgClearDetailModelList().size() == 0) {
                        Map map = new HashMap();
                        map.put("payOrgCode", relationModel.getPayOrgCode());
                        map.put("receivableAmt", relationModel.getReceivableAmt());
                        map.put("clearDate", relationModel.getClearDate());
                        map.put("cashTransDateR", relationModel.getCashTransDateR());
                        map.put("payableAmt", relationModel.getPayableAmt());
                        map.put("cashTransDateP", relationModel.getCashTransDateP());
                        map.put("index", i);
                        datas.add(map);
                    }
                }

                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                //去掉合并行
//                config.mergeColumn("index", "payOrgCode", "receivableAmt", "clearDate", "cashTransDateR",
//                        "payableAmt", "cashTransDateP");
                ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                POIExcelApi.getInstance().write(outputStream, ca);
                File file = ExcelUtils.addTitle(tempFile, "机构结算关系日报表", fileDownAjax, tHeaders.size(), beginDate, endDate);
                responseEntity = fileDownAjax.getResponseEntity(file);
            } else {
                responseEntity = fileDownAjax.getResponseEntityFail();
            }
        } catch (Exception e) {
            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        } finally {
            fileDownAjax.forceDelete(tempFile);
        }

        return responseEntity;
    }
}
