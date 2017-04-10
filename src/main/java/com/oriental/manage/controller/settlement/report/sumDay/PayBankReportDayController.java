package com.oriental.manage.controller.settlement.report.sumDay;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.oriental.check.commons.util.DateUtil;
import com.oriental.clearDubbo.api.clear.report.ReportDayInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.report.ReportDayModel;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.excelUtils.ExcelUtils;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.paycenter.commons.mode.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 商业银行支付业务报表
 * Created by zhangxinhai on 2016/6/29.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/sumDay/payBankReportDay")
public class PayBankReportDayController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ReportDayInterface reportDayInterface;
    @Autowired
    private FileDownAjax fileDownAjax;

    @Autowired
    private Constants Constants;
    @RequestMapping("init")
    @RequiresPermissions("payBankReportDay_search")
    public String init() {
        return "settlement/report/sumDay/payBankReportDay";
    }

    @OperateLogger(content = "查询商业银行支付业务报表",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("payBankReportDay_search")
    @ResponseBody
    public ResponseDTO<Pagination<ReportDayModel>> queryPage(@RequestBody Pagination<ReportDayModel> pagination) {
        ResponseDTO<Pagination<ReportDayModel>> responseDTO = new ResponseDTO<Pagination<ReportDayModel>>();
        try {
            RequestModel<ReportDayModel> requestModel = new RequestModel<ReportDayModel>();
            requestModel.setRequest(pagination.getQueryBean());
            log.info("商业银行支付业务报表查询参数："+requestModel);
            ResponseModel<ReportDayModel> responseModel = reportDayInterface.getPayBankReportDay(requestModel);
            log.info("商业银行支付业务报表查询结果："+responseModel);
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

    @RequiresPermissions("payBankReportDay_down")
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestBody ReportDayModel ReportDayModel){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try{
            InputStream inputStream = new FileInputStream(Constants.getExcelPath()+"/商业银行支付报表模板.xlsx");
            tempFile = fileDownAjax.touch("商业银行支付业务报表-" + DateUtils.now() + ".xlsx");
            String beginDate = DateUtils.format(ReportDayModel.getSumDate(),DateUtils.DATESHORTFORMAT);
            RequestModel<ReportDayModel> requestModel = new RequestModel<ReportDayModel>();
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);
            requestModel.setRequest(ReportDayModel);
            ResponseModel<ReportDayModel> responseModel = reportDayInterface.getPayBankReportDay(requestModel);

            if(responseModel.getResponseResult()){
                //表头
                LinkedList<Header> tHeaders = new LinkedList<>();
                //内容
                List<Object> datas = new ArrayList<>();
                //添加表头
                tHeaders.add(new HeaderExt("银行名称", "payBankCode",ExcelContentExt.PAY_BANK_CODE));
                tHeaders.add(new HeaderExt("机构代码", "orgCode", ExcelContentExt.ORGANIZE));
                tHeaders.add(new HeaderExt("借/笔", "countDayD"));
                tHeaders.add(new HeaderExt("借（元）", "amtDayD", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("贷/笔", "countDayC"));
                tHeaders.add(new HeaderExt("贷（元）", "amtDayC", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("总计/笔", "countDay"));
                tHeaders.add(new HeaderExt("总计（元）", "amtDay", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("借/笔", "countMonthD"));
                tHeaders.add(new HeaderExt("借（元）", "amtMonthD", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("贷/笔", "countMonthC"));
                tHeaders.add(new HeaderExt("贷（元）", "amtMonthC", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("总计/笔", "countMonth"));
                tHeaders.add(new HeaderExt("总计（元）", "amtMonth", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("借/笔", "countYearD"));
                tHeaders.add(new HeaderExt("借（元）", "amtYearD", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("贷/笔", "countYearC"));
                tHeaders.add(new HeaderExt("贷（元）", "amtYearC", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("总计/笔", "countYear"));
                tHeaders.add(new HeaderExt("总计（元）", "amtYear", ExcelContentExt.CURRENCY));

                 int i=1;
                for(ReportDayModel reportDayModel : responseModel.getList()){
                    if(StringUtils.isEmpty(reportDayModel.getOrgCode())){
                        reportDayModel.setPayBankCode("");
                        reportDayModel.setOrgCode("小计");
                    }
                    if(responseModel.getList().size()==i){
                        reportDayModel.setOrgCode("总计");
                    }
//                    if(reportDayModel.getOrgCode().equals("小计")){
//                        reportDayModel.setPayBankCode("");
//                    }
                    Map map = Bean2MapUtil.convertBean(reportDayModel);
                    i++;
                    datas.add(map);
                }
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                config.setStartWith(2);
                config.mergeColumn("payBankCode");

                FileOutputStream outputStream = new FileOutputStream(tempFile);

                ExcelTemplateUtil.write(inputStream, outputStream, config);
                File file = ExcelUtils.addTitle(tempFile, "商业银行支付业务报表", fileDownAjax, tHeaders.size(), beginDate, "");
                responseEntity = fileDownAjax.getResponseEntity(file);
            }else{
                responseEntity = fileDownAjax.getResponseEntityFail();
            }

        }catch(Exception e){

            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }

}
