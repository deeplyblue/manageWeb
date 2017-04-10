package com.oriental.manage.controller.settlement.report.formsReport;

import com.oriental.clearDubbo.api.clear.report.ReportFormsInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.report.FeeCfgReportModel;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
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
import java.util.*;

/**
 * 支付业务手续费佣金费率明细表
 * Created by wangjun on 2016/12/25.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/formsReport/feeCfgReport")
public class FeeCfgReportController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ReportFormsInterface reportFormsInterface;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private com.oriental.manage.core.system.Constants Constants;
    @Autowired
    private IOrganizeInfoService organizeInfoService;
    @Autowired
    private IMerchantInfoService merchantService;

    @RequestMapping("init")
    @RequiresPermissions("feeCfgReport_search")
    public String init() {
        return "settlement/report/formsReport/feeCfgReport";
    }


    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("feeCfgReport_search")
    public ResponseDTO<FeeCfgReportModel> queryPage(@RequestBody Pagination<FeeCfgReportModel> pagination) {
        ResponseDTO responseDTO = new ResponseDTO<>();
        try {

            RequestModel<FeeCfgReportModel> requestModel = new RequestModel();
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setPageNo(pagination.getPageNum());

            ResponseModel<FeeCfgReportModel> responseModel = reportFormsInterface.getFeeCfgReport();

           for(int i=0;i<responseModel.getList().size();i++){{
               if("03".equals(responseModel.getList().get(i).getCompanyType())){
                   responseModel.getList().get(i).setRemark("代理收款");
               }
               if("02".equals(responseModel.getList().get(i).getCompanyType())){
                   OrganizeInfo organizeInfo=organizeInfoService.queryChannelType(responseModel.getList().get(i).getCompanyCode());
                   if(organizeInfo!=null && organizeInfo.getChannelType()!=null){
                       responseModel.getList().get(i).setRemark(organizeInfo.getChannelType());}
                   else{
                       responseModel.getList().get(i).setRemark(" ");
                   }
               }
           }
           }
            if (responseModel.getResponseResult()) {
                pagination.setList(responseModel.getList());
                responseDTO.setObject(pagination);
                responseDTO.setSuccess(true);
            }
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("download")
    public ResponseEntity<byte[]> download() {
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            InputStream inputStream = new FileInputStream(Constants.getExcelPath() + "/fee_cfg_report.xlsx");

            tempFile = fileDownAjax.touch("支付业务手续费佣金费率明细表-" + DateUtils.now() + ".xlsx");

            ResponseModel<FeeCfgReportModel> responseModel = reportFormsInterface.getFeeCfgReport();

            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("机构名称", "companyCode"));
            tHeaders.add(new HeaderExt("机构类别", "companyType"));
            tHeaders.add(new HeaderExt("费率名称", "feeCfgNae"));
            tHeaders.add(new HeaderExt("支付接口或业务种类", "remark"));
            tHeaders.add(new HeaderExt("费率或佣金费率", "fixFeeShow"));

            List<OrganizeInfo> list = organizeInfoService.getCompnayCode();

            List<MerchantInfo> listM = merchantService.getMerchantInfo();

            for(int i=0;i<responseModel.getList().size();i++) {
                {
                    if ("03".equals(responseModel.getList().get(i).getCompanyType())) {
                        responseModel.getList().get(i).setRemark("代理收款");
                    }
                    if ("02".equals(responseModel.getList().get(i).getCompanyType())) {
                        OrganizeInfo organizeInfo = organizeInfoService.queryChannelType(responseModel.getList().get(i).getCompanyCode());
                        if (organizeInfo != null && organizeInfo.getChannelType() != null) {
                            responseModel.getList().get(i).setRemark(organizeInfo.getChannelType());
                        } else {
                            responseModel.getList().get(i).setRemark(" ");
                        }
                    }
                }
            }
            for (int j = 0; j < responseModel.getList().size(); j++) {

                Map map = Bean2MapUtil.convertBean(responseModel.getList().get(j));

                for(OrganizeInfo organizeInfo :list){
                    if(responseModel.getList().get(j).getCompanyCode().equals(organizeInfo.getOrgCode())){
                        map.put("companyCode",organizeInfo.getOrgAbbrName());

                    }
                }
                for(MerchantInfo merchantInfo :listM){
                    if(responseModel.getList().get(j).getCompanyCode().equals(merchantInfo.getMerchantCode())){
                        map.put("companyCode",merchantInfo.getMerchantAbbrName());
                    }
                }

                if(responseModel.getList().get(j).getCompanyType().equals("02")){
                    map.put("companyType","支付机构");
                    map.put("feeCfgNae","手续费支付");
                }
                if(responseModel.getList().get(j).getCompanyType().equals("03")){
                    map.put("companyType","商户");
                    map.put("feeCfgNae","佣金收入");
                }
                datas.add(map);
            }

            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            config.setStartWith(4);
            FileOutputStream outputStream = new FileOutputStream(tempFile);

//            ExcelTemplateUtil.write(inputStream, outputStream, config);
            ExcelTemplateUtil.write(inputStream, outputStream, config);
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