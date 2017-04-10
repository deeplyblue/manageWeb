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
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.pojo.institution.OrganizeInfo;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantInfo;
import com.oriental.manage.service.institution.IOrganizeInfoService;
import com.oriental.manage.service.merchant.baseinfo.IMerchantInfoService;
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
 * 商户委托支付业务统计明细表
 * Created by wangjun on 2016/12/23.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/formsReport/mchntCodePayment")
public class MchntCodePaymentReportController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ReportFormsInterface reportFormsInterface;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private IMerchantInfoService merchantService;
    @Autowired
    private com.oriental.manage.core.system.Constants Constants;

    @RequestMapping("init")
    @RequiresPermissions("mchntCodePayment_search")
    public String init(){ return "settlement/report/formsReport/mchntCodePaymentReport";}


    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("mchntCodePayment_search")
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
            ResponseModel<ReportFormsModel> responseModel= reportFormsInterface.getMchntCodePaymentReport(requestModel);
            if(responseModel.getResponseResult()){
                Iterator<ReportFormsModel> iter = responseModel.getList().iterator();
                /*for(int i=0 ;i<responseModel.getList().size();i++){
                    if("小计".equals(responseModel.getList().get(i).getBusiType()) && !responseModel.getList().get(i).getMchntCode().equals("合计")){
                        responseModel.getList().remove(i);
                    }
                }*/

                while (iter.hasNext()){
                    ReportFormsModel tmp = iter.next();
                    if("小计".equals(tmp.getBusiType()) && !tmp.getMchntCode().equals("合计")){
                        iter.remove();
                    }
                }
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
    public ResponseEntity<byte[]> download(@RequestBody ReportFormsModel reportFormsModel) {
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            InputStream inputStream = new FileInputStream(Constants.getExcelPath() + "/mchnt_code_payment_report.xlsx");

            tempFile = fileDownAjax.touch("商户委托支付业务统计报表-" + DateUtils.now() + ".xlsx");
            String time=new SimpleDateFormat("yyyy年MM月dd日").format(reportFormsModel.getSettleDate());
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(reportFormsModel);
            requestModel.setPageNo(0);
            requestModel.setPageSize(10000);
            reportFormsModel.setSettleDate(DateUtils.parse(reportFormsModel.getSettleDate(),DateUtils.DATESHOWFORMAT));
            ResponseModel<ReportFormsModel> responseModel= reportFormsInterface.getMchntCodePaymentReport(requestModel);

            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("商户名称", "mchntCode", ExcelContentExt.MCHNT));
            tHeaders.add(new HeaderExt("业务种类", "busiType", ExcelContentExt.MCHNT_BUSI_TYPE));
            tHeaders.add(new HeaderExt("笔数", "countDay"));
            tHeaders.add(new HeaderExt("金额", "amtDay",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "countMonth"));
            tHeaders.add(new HeaderExt("金额", "amtMonth",ExcelContentExt.CURRENCY));

            tHeaders.add(new HeaderExt("笔数", "countYear"));
            tHeaders.add(new HeaderExt("金额", "amtYear",ExcelContentExt.CURRENCY));

            List<MerchantInfo> listM = merchantService.getMerchantInfo();

            for (int j = 0; j < responseModel.getList().size(); j++) {
                for (MerchantInfo merchantInfo : listM) {
                    if (responseModel.getList().get(j).getMchntCode().equals(merchantInfo.getMerchantCode())) {
                        responseModel.getList().get(j).setMchntCode(merchantInfo.getMerchantName());
                    }
                }
            }
            for(int i=0 ;i<responseModel.getList().size();i++){
                Iterator<ReportFormsModel> iter = responseModel.getList().iterator();
                /*for(int i=0 ;i<responseModel.getList().size();i++){
                    if("小计".equals(responseModel.getList().get(i).getBusiType()) && !responseModel.getList().get(i).getMchntCode().equals("合计")){
                        responseModel.getList().remove(i);
                    }
                }*/

                while (iter.hasNext()){
                    ReportFormsModel tmp = iter.next();
                    if("小计".equals(tmp.getBusiType()) && !tmp.getMchntCode().equals("合计")){
                        iter.remove();
                    }
                }
            }
            for (int j = 0; j < responseModel.getList().size(); j++) {
                if("小计".equals(responseModel.getList().get(j).getBusiType())){
                    responseModel.getList().get(j).setBusiType(" ");
                }
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
