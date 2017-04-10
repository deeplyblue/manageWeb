package com.oriental.manage.controller.settlement.report.formsReport;

import com.oriental.clearDubbo.api.clear.report.ReportFormsInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;

import com.oriental.clearDubbo.model.clear.report.ReportFormsModel;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.pojo.institution.OrganizeInfo;
import com.oriental.manage.service.institution.IOrganizeInfoService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  亿付数字商业银行支付接口支付业务统计明细表
 *  Created by wangjun on 2016/12/22.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/formsReport/payOrgCodePayment")
public class PayOrgCodePaymentReportController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ReportFormsInterface reportFormsInterface;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private IOrganizeInfoService organizeInfoService;
    @Autowired
    private Constants Constants;

    @RequestMapping("init")
    @RequiresPermissions("payOrgCodePayment_search")
    public String init(){ return "settlement/report/formsReport/payOrgCodePaymentReport";}

    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("payOrgCodePayment_search")
    public ResponseDTO<ReportFormsModel> queryPage(@RequestBody Pagination<ReportFormsModel> pagination){
        ResponseDTO responseDTO=new ResponseDTO<>();
        try {

            RequestModel<ReportFormsModel> requestModel =new RequestModel();
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setPageNo(pagination.getPageNum());
            ReportFormsModel reportFormsModel=pagination.getQueryBean();
            reportFormsModel.setSettleDate(pagination.getQueryBean().getSettleDate());
            reportFormsModel.setSettleDate(DateUtils.parse(reportFormsModel.getSettleDate(),DateUtils.DATESHOWFORMAT));
            requestModel.setRequest(reportFormsModel);
            ResponseModel<ReportFormsModel> responseModel= reportFormsInterface.getPayOrgCodePaymentReport(requestModel);
            if(responseModel.getResponseResult()){
                for(int i=0;i<responseModel.getList().size();i++){
                  OrganizeInfo organizeInfo=organizeInfoService.queryChannelType(responseModel.getList().get(i).getPayOrgCode());
                    if(organizeInfo!=null){
                    responseModel.getList().get(i).setBusiType(organizeInfo.getPayBankCode());}
                }
                responseModel.getList().get(responseModel.getList().size()-1).setBusiType(" ");
               pagination.setList(responseModel.getList());
                responseDTO.setObject(pagination);
                responseDTO.setSuccess(true);
            }
        }catch (Exception e){
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("download")
//    @RequiresPermissions("mchntTransSumDay_down")
    public ResponseEntity<byte[]> download(@RequestBody ReportFormsModel reportFormsModel) {
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            InputStream inputStream = new FileInputStream(Constants.getExcelPath() + "/pay_org_code_payment_report.xlsx");

            tempFile = fileDownAjax.touch("支付接口支付业务统计报表-" + DateUtils.now() + ".xlsx");
            String time=new SimpleDateFormat("yyyy年MM月dd日").format(reportFormsModel.getSettleDate());
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(reportFormsModel);
            requestModel.setPageNo(0);
            requestModel.setPageSize(10000);
            reportFormsModel.setSettleDate(DateUtils.parse(reportFormsModel.getSettleDate(),DateUtils.DATESHOWFORMAT));
            ResponseModel<ReportFormsModel> responseModel= reportFormsInterface.getPayOrgCodePaymentReport(requestModel);
            if(responseModel.getResponseResult()){
                for(int i=0;i<responseModel.getList().size();i++){
                    OrganizeInfo organizeInfo=organizeInfoService.queryChannelType(responseModel.getList().get(i).getPayOrgCode());
                    if(organizeInfo!=null){
                        responseModel.getList().get(i).setBusiType(organizeInfo.getPayBankCode());}
                }
            }
            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("支付机构", "busiType",ExcelContentExt.PAY_BANK_CODE));
            tHeaders.add(new HeaderExt("业务种类", "payOrgCode"));
            tHeaders.add(new HeaderExt("借方笔数", "countDayD"));
            tHeaders.add(new HeaderExt("借方金额", "amtDayD",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("贷方笔数", "countDayC"));
            tHeaders.add(new HeaderExt("贷方金额", "amtDayC",ExcelContentExt.CURRENCY));

            tHeaders.add(new HeaderExt("借方笔数", "countMonthD"));
            tHeaders.add(new HeaderExt("借方金额", "amtMonthD",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("贷方笔数", "countMonthC"));
            tHeaders.add(new HeaderExt("贷方金额", "amtMonthC",ExcelContentExt.CURRENCY));

            tHeaders.add(new HeaderExt("借方笔数", "countYearD"));
            tHeaders.add(new HeaderExt("借方金额", "amtYearD",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("贷方笔数", "countYearC"));
            tHeaders.add(new HeaderExt("贷方金额", "amtYearC",ExcelContentExt.CURRENCY));

            List<OrganizeInfo> list = organizeInfoService.getCompnayCode();


            for (int j = 0; j < responseModel.getList().size(); j++) {
                for (OrganizeInfo organizeInfo : list) {
                    if (responseModel.getList().get(j).getPayOrgCode().equals(organizeInfo.getOrgCode())) {
                        responseModel.getList().get(j).setPayOrgCode(organizeInfo.getOrgName());
                    }
                }
            }

            for (int j = 0; j < responseModel.getList().size(); j++) {
                Map map = Bean2MapUtil.convertBean(responseModel.getList().get(j));
                datas.add(map);
            }

            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            config.setStartWith(5);
            FileOutputStream outputStream = new FileOutputStream(tempFile);

//            ExcelTemplateUtil.write(inputStream, outputStream, config);
            ExcelTemplateUtil.writeTitle(inputStream,outputStream,config,time);
            responseEntity = fileDownAjax.getResponseEntity(tempFile);


        } catch (Exception e) {

            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        } finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }
}
