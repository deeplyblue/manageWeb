package com.oriental.manage.controller.opcif;

import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.system.log.LogIdInterceptor;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.opcif.common.model.RequestModel;
import com.oriental.opcif.common.model.ResponseModel;
import com.oriental.opcif.product.bizModel.manager.AgreementReportDto;
import com.oriental.opcif.product.bizModel.manager.TCustomerBankDto;
import com.oriental.opcif.product.facade.manager.CustomerBankInfoFacade;
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
 * Created by wangjun on 2016/12/5.
 */
@Slf4j
@Controller
@RequestMapping("cif/agreementRateReport")
public class AgreementRateReportController {

    @Autowired(required = false)
    private CustomerBankInfoFacade customerBankInfoFacade;

    @Autowired
    private Constants constants;
    @Autowired
    private FileDownAjax fileDownAjax;

    @RequestMapping("init")
    @RequiresPermissions("agreementRateReport_search")
    public String init(){ return "opcif/searchAgreementRateReport";}

    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("agreementRateReport_search")
    @OperateLogger(content = "绑卡报表查询",operationType = OperateLogger.OperationType.R)
    public ResponseDTO<Pagination<AgreementReportDto>> queryPage( @RequestBody Pagination <TCustomerBankDto> query){
         log.info("查询数据",query);
        ResponseDTO<Pagination<AgreementReportDto>> responseDTO=new ResponseDTO<>();
        try{

            Pagination<AgreementReportDto> pagination =new Pagination<>();
            ResponseModel<AgreementReportDto> responseModel1 = customerBankInfoFacade.agreementRateReport(query.getQueryBean());
            pagination.setRowCount(responseModel1.getTotal());
            pagination.setList(responseModel1.getList());
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
            log.info("查询结果",responseModel1);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequiresPermissions("agreementRateReport_download")
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestBody TCustomerBankDto tCustomerBankDto ){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try{

            InputStream inputStream= new FileInputStream(constants.getExcelPath()+"/agreement_rate_report_template.xlsx");
            tempFile = fileDownAjax.touch("绑卡报表"+ DateUtils.now() + ".xlsx");

            ResponseModel<AgreementReportDto> responseModel1 = customerBankInfoFacade.agreementRateReport(tCustomerBankDto);

            LinkedList<Header> tHeaders = new LinkedList<>();
            List<Object> datas = new ArrayList<>();

            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("银行名称", "bankName"));
            tHeaders.add(new HeaderExt("总申请数", "applyCount"));
            tHeaders.add(new HeaderExt("签约总数", "signCount"));
            tHeaders.add(new HeaderExt("成功笔数", "signSuccessCount"));
            tHeaders.add(new HeaderExt("失败笔数", "signFailCount"));
            tHeaders.add(new HeaderExt("成功占比", "signSuccessRate"));
            tHeaders.add(new HeaderExt("失败占比", "signFailRate"));
            if(responseModel1.getResponseResult()){
                int i=1;
                for(AgreementReportDto agreementReportDto : responseModel1.getList()) {
                    Map map = Bean2MapUtil.convertBean(agreementReportDto);
                    map.put("index", i);
                    i++;
                    datas.add(map);
                }
            }

            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            config.setStartWith(2);
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            ExcelTemplateUtil.write(inputStream, outputStream, config);

            responseEntity = fileDownAjax.getResponseEntity(tempFile);

             }catch (Exception e){
             log.error("下载失败", e);
             responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }

}
