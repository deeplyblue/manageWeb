package com.oriental.manage.controller.settlement.report.sumDay;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.oriental.clearDubbo.api.clear.report.ReportDayInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.report.ReportDayModel;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 业务类型报表
 * Created by zhangxinhai on 2016/6/29.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/sumDay/busiTypeReportDay")
public class BusiTypeReportDayController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ReportDayInterface reportDayInterface;
    @Autowired
    private FileDownAjax fileDownAjax;
    @RequestMapping("init")
    @RequiresPermissions("busiTypeReportDay_search")
    public String init() {
        return "settlement/report/sumDay/busiTypeReportDay";
    }

    @OperateLogger(content = "查询业务种类报表",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("busiTypeReportDay_search")
    @ResponseBody
    public ResponseDTO<Pagination<ReportDayModel>> queryPage(@RequestBody Pagination<ReportDayModel> pagination) {
        ResponseDTO<Pagination<ReportDayModel>> responseDTO = new ResponseDTO<Pagination<ReportDayModel>>();
        try {
            RequestModel<ReportDayModel> requestModel = new RequestModel<ReportDayModel>();
            requestModel.setRequest(pagination.getQueryBean());
            log.info("业务种类报表查询参数："+requestModel);
            ResponseModel<ReportDayModel> responseModel = reportDayInterface.getBusiTypeReportDay(requestModel);
            log.info("业务种类报表查询结果："+responseModel);
            if (responseModel.getResponseResult()) {
                pagination.setList(responseModel.getList());
                responseDTO.setSuccess(true);
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

    @RequiresPermissions("busiTypeReportDay_down")
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestBody ReportDayModel reportDay) {

        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            tempFile = fileDownAjax.touch("业务种类报表-" + DateUtils.now() + ".xlsx");
            String beginDate = DateUtils.format(reportDay.getSumDate(),DateUtils.DATESHORTFORMAT);
            RequestModel<ReportDayModel> requestModel = new RequestModel<ReportDayModel>();
            requestModel.setRequest(reportDay);
            ResponseModel<ReportDayModel> responseModel = reportDayInterface.getBusiTypeReportDay(requestModel);
            if (responseModel.getResponseResult()) {
                LinkedList<Header> tHeaders = new LinkedList<>();
                List<Object> datas = new ArrayList<>();

                tHeaders.add(new HeaderExt("业务种类", "busiType",ExcelContentExt.MCHNT_BUSI_TYPE));
                tHeaders.add(new HeaderExt("商户代码", "orgCode", ExcelContentExt.MCHNT));
                tHeaders.add(new HeaderExt("当日业务笔数", "countDay"));
                tHeaders.add(new HeaderExt("当日业务金额（元）", "amtDay", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("当月业务笔数", "countMonth"));
                tHeaders.add(new HeaderExt("当月业务金额（元）", "amtMonth", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("当年业务笔数", "countYear"));
                tHeaders.add(new HeaderExt("当年业务金额（元）", "amtYear", ExcelContentExt.CURRENCY));
                for (ReportDayModel reportDayModel : responseModel.getList()) {
                    if(StringUtils.isEmpty(reportDayModel.getBusiType())){
                        reportDayModel.setOrgCode("合计");
                    }
                    if(StringUtils.isEmpty(reportDayModel.getOrgCode())){
                        reportDayModel.setOrgCode("小计");
                    }
                    Map map = Bean2MapUtil.convertBean(reportDayModel);
                    datas.add(map);
                }
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setSheetName("业务种类报表");
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                config.mergeColumn("busiType","orgCode");

                ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);

                FileOutputStream outputStream = new FileOutputStream(tempFile);
                POIExcelApi.getInstance().write(outputStream, ca);
                File file = ExcelUtils.addTitle(tempFile, "业务种类报表", fileDownAjax, tHeaders.size(), beginDate, "");
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
