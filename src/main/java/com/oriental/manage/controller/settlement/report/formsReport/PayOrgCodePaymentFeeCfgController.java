package com.oriental.manage.controller.settlement.report.formsReport;

import com.oriental.clearDubbo.api.clear.report.ReportFormsInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.report.FeeCfgReportModel;
import com.oriental.clearDubbo.model.clear.report.ReportFormsModel;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  亿付数字支付业务手续费支出结算明细表
 *  Created by wangjun on 2016/12/22.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/formsReport/payOrgCodePaymentFeeCfg")
public class PayOrgCodePaymentFeeCfgController {

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
    @RequiresPermissions("payOrgCodePaymentFeeCfg_search")
    public String init(){ return "settlement/report/formsReport/payOrgCodePaymentFeeCfg";}

    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("payOrgCodePaymentFeeCfg_search")
    public ResponseDTO<ReportFormsModel> queryPage(@RequestBody Pagination<ReportFormsModel> pagination){
        ResponseDTO responseDTO=new ResponseDTO<>();
        try {

            RequestModel<ReportFormsModel> requestModel =new RequestModel();
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setPageNo(pagination.getPageNum());
            ReportFormsModel reportFormsModel=pagination.getQueryBean();
            reportFormsModel.setSettleDate(pagination.getQueryBean().getSettleDate());
            requestModel.setRequest(reportFormsModel);
            reportFormsModel.setSettleDate(DateUtils.parse(reportFormsModel.getSettleDate(),DateUtils.DATESHOWFORMAT));
            ResponseModel<ReportFormsModel> responseModel= reportFormsInterface.getPayOrgCodePaymentFeeReport(requestModel);

            ResponseModel<FeeCfgReportModel> responseModelFeecfg = reportFormsInterface.getFeeCfgReport();
            Map<String,FeeCfgReportModel> map = new HashMap<String, FeeCfgReportModel>();
            for(FeeCfgReportModel cfgReportModel : responseModelFeecfg.getList()){
                map.put(cfgReportModel.getCompanyCode(),cfgReportModel);
            }
            double moneyD=0.00;
            double moneyY=0.00;
            if(responseModel.getResponseResult()){

                for(ReportFormsModel reportFormsModel1 : responseModel.getList()){
                    FeeCfgReportModel cfgReportModel = map.get(reportFormsModel1.getPayOrgCode());
                    if(cfgReportModel!=null){
                    reportFormsModel1.setMchntCode(cfgReportModel.getFixFeeShow());

                    if(cfgReportModel.getFeeSegMethod().equals("01")){
                        //计算当月
                        reportFormsModel1.setAmtDay(new BigDecimal(reportFormsModel1.getCountMonth()).multiply(cfgReportModel.getFixFee()).multiply(new BigDecimal(100)));
                        moneyD+=reportFormsModel1.getAmtDay().doubleValue();
                        //计算当年
                        reportFormsModel1.setFeeDay(new BigDecimal(reportFormsModel1.getCountYear()).multiply(cfgReportModel.getFixFee()).multiply(new BigDecimal(100)));
                        moneyY+=reportFormsModel1.getFeeDay().doubleValue();
                    }else {
                        reportFormsModel1.setAmtDay(reportFormsModel1.getAmtMonth().multiply(cfgReportModel.getFixFee()).divide(new BigDecimal(100)));
                        moneyD+=reportFormsModel1.getAmtDay().doubleValue();
                        reportFormsModel1.setFeeDay(reportFormsModel1.getAmtYear().multiply(cfgReportModel.getFixFee()).divide(new BigDecimal(100)));
                        moneyY+=reportFormsModel1.getFeeDay().doubleValue();
                    }
                }
                }
                for(int i=0;i<responseModel.getList().size();i++){
                    if("合计".equals(responseModel.getList().get(i).getPayOrgCode())){
                        responseModel.getList().get(i).setMchntCode(" ");
                        responseModel.getList().get(i).setAmtDay(new BigDecimal(moneyD));
                        responseModel.getList().get(i).setFeeDay(new BigDecimal(moneyY));
                    }
                  OrganizeInfo organizeInfo=organizeInfoService.queryChannelType(responseModel.getList().get(i).getPayOrgCode());
                    if(organizeInfo!=null){
                    responseModel.getList().get(i).setBusiType(organizeInfo.getChannelType());}
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
            InputStream inputStream = new FileInputStream(Constants.getExcelPath() + "/pay_org_code_payment_fee_cfg.xlsx");
            tempFile = fileDownAjax.touch("亿付数字支付业务手续费支出结算明细表-" + DateUtils.now() + ".xlsx");
            String time=new SimpleDateFormat("yyyy年MM月dd日").format(reportFormsModel.getSettleDate());
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(reportFormsModel);
            requestModel.setPageSize(10000);
            requestModel.setPageNo(0);
            reportFormsModel.setSettleDate(DateUtils.parse(reportFormsModel.getSettleDate(),DateUtils.DATESHOWFORMAT));
            ResponseModel<ReportFormsModel> responseModel= reportFormsInterface.getPayOrgCodePaymentFeeReport(requestModel);

            ResponseModel<FeeCfgReportModel> responseModelFeecfg = reportFormsInterface.getFeeCfgReport();
            Map<String,FeeCfgReportModel> mapF = new HashMap<String, FeeCfgReportModel>();
            for(FeeCfgReportModel cfgReportModel : responseModelFeecfg.getList()){
                mapF.put(cfgReportModel.getCompanyCode(),cfgReportModel);
            }
            double moneyD=0.00;
            double moneyY=0.00;

            if(responseModel.getResponseResult()){

                for(ReportFormsModel reportFormsModel1 : responseModel.getList()){
                    FeeCfgReportModel cfgReportModel = mapF.get(reportFormsModel1.getPayOrgCode());
                    if(cfgReportModel!=null){
                        reportFormsModel1.setMchntCode(cfgReportModel.getFixFeeShow());

                        if(cfgReportModel.getFeeSegMethod().equals("01")){
                            //计算当月
                            reportFormsModel1.setAmtDay(new BigDecimal(reportFormsModel1.getCountMonth()).multiply(cfgReportModel.getFixFee()));
                            //计算当年
                            reportFormsModel1.setFeeDay(new BigDecimal(reportFormsModel1.getCountYear()).multiply(cfgReportModel.getFixFee()));
                            moneyD+=reportFormsModel1.getAmtDay().doubleValue();
                            moneyY+=reportFormsModel1.getFeeDay().doubleValue();
                        }else {
                            reportFormsModel1.setAmtDay(reportFormsModel1.getAmtMonth().multiply(cfgReportModel.getFixFee().divide(new BigDecimal(100))).divide(new BigDecimal(100)));
                            moneyD+=reportFormsModel1.getAmtDay().doubleValue();
                            reportFormsModel1.setFeeDay(reportFormsModel1.getAmtYear().multiply(cfgReportModel.getFixFee().divide(new BigDecimal(100))).divide(new BigDecimal(100)));
                            moneyY+=reportFormsModel1.getFeeDay().doubleValue();
                        }
                    }
                }

                for(int i=0;i<responseModel.getList().size();i++){
                    if("合计".equals(responseModel.getList().get(i).getPayOrgCode())){
                        responseModel.getList().get(i).setAmtDay(new BigDecimal(moneyD));
                        responseModel.getList().get(i).setFeeDay(new BigDecimal(moneyY));
                    }

                    OrganizeInfo organizeInfo=organizeInfoService.queryChannelType(responseModel.getList().get(i).getPayOrgCode());
                    if(organizeInfo!=null &&null!=organizeInfo.getChannelType()){
                        responseModel.getList().get(i).setBusiType(organizeInfo.getChannelType());}
                }
            }
            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("支付机构", "payOrgCode"));
            tHeaders.add(new HeaderExt("业务种类", "busiType"));
            tHeaders.add(new HeaderExt("业务笔数", "countMonth"));
            tHeaders.add(new HeaderExt("结算金额", "amtMonth",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("计费费率", "mchntCode"));
            tHeaders.add(new HeaderExt("计费金额", "amtDay"));
            tHeaders.add(new HeaderExt("实际计费金额", "feeMonth",ExcelContentExt.CURRENCY));

            tHeaders.add(new HeaderExt("业务笔数", "countYear"));
            tHeaders.add(new HeaderExt("结算金额", "amtYear" ,ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("计费费率", "mchntCode"));
            tHeaders.add(new HeaderExt("计费金额", "feeDay"));
            tHeaders.add(new HeaderExt("实际计费金额", "feeYear",ExcelContentExt.CURRENCY));

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
