package com.oriental.manage.controller.settlement.clear.detail;

import com.oriental.clearDubbo.api.clear.day.ClearDayInterface;
import com.oriental.clearDubbo.api.clear.detail.ClearDetailInterface;
import com.oriental.clearDubbo.api.settle.settle.SettleDetailInterface;
import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDayModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDetailModel;
import com.oriental.clearDubbo.model.settle.pay.PaySettleDetailModel;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.StringCommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
 * Created by zhangxinhai on 2016/5/23.
 */
@Slf4j
@Controller
@RequestMapping("settlement/clear/detail/payClearDetail")
public class PayClearDetailController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ClearDetailInterface clearDetailInterface;
    @Autowired
    private ClearDayInterface clearDayInterface;
    @Autowired
    private SettleDetailInterface settleDetailInterface;
    @Autowired
    private Constants constants;
    @Autowired
    private FileDownAjax fileDownAjax;

    @RequestMapping("init")
    @RequiresPermissions("payClearDetail_search")
    public String init() {
        return "settlement/clear/detail/payClearDetail";
    }

    @OperateLogger(content = "机构本金结算查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("payClearDetail_search")
    @ResponseBody
    public ResponseDTO<Pagination<OrgClearDetailModel>> queryPage(Pagination<OrgClearDetailModel> pagination,
                                                                  OrgClearDetailModel orgClearDetailModel,
                                                                  String beginDate, String endDate) {
        ResponseDTO<Pagination<OrgClearDetailModel>> responseDTO = new ResponseDTO<Pagination<OrgClearDetailModel>>();
        try{
            orgClearDetailModel.setClrDateBegin(DateUtils.parse(beginDate));
            orgClearDetailModel.setClrDateEnd(DateUtils.parse(endDate));
            RequestModel<OrgClearDetailModel> requestModel = new RequestModel<OrgClearDetailModel>();
            requestModel.setRequest(orgClearDetailModel);
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            log.info("机构本金结算查询参数："+requestModel);
            ResponseModel<OrgClearDetailModel> responseModel = clearDetailInterface.getPayClearDetailModel(requestModel);
            log.info("机构本金结算查询结果："+responseModel);
            OrgSumPageModel orgSumPageModel = responseModel.getOrgSumPageModel();
            if (responseModel.getResponseResult()) {
                pagination.setRowCount(orgSumPageModel.getPageSum());
                pagination.setList(responseModel.getList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
                responseDTO.setSumObject(orgSumPageModel);
            } else {
                responseDTO.setSuccess(false);
            }
        }catch (Exception e){
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @OperateLogger(content = "支付本金结算查询-日结",operationType = OperateLogger.OperationType.R)
    @RequestMapping("getPayClearDay")
    @ResponseBody
    public ResponseDTO<Pagination<OrgClearDayModel>> queryPayClearDay(String orgCode,String settleDateBegin,String settleDateEnd) {
        ResponseDTO<Pagination<OrgClearDayModel>> responseDTO = new ResponseDTO<Pagination<OrgClearDayModel>>();
        Pagination<OrgClearDayModel> pagination = new Pagination<OrgClearDayModel>();
        try{
            OrgClearDayModel orgClearDayModel = new OrgClearDayModel();
            orgClearDayModel.setOrgCode(orgCode);
            orgClearDayModel.setSettleDateBegin(DateUtils.parse(settleDateBegin));
            orgClearDayModel.setSettleDateEnd(DateUtils.parse(settleDateEnd));
            RequestModel<OrgClearDayModel> requestModel = new RequestModel<OrgClearDayModel>();
            requestModel.setPageSize(100);
            requestModel.setRequest(orgClearDayModel);
            log.info("机构本金结算查询-日结参数："+requestModel);
            ResponseModel<OrgClearDayModel> responseModel = clearDayInterface.getPayClearDayModel(requestModel);
            log.info("机构本金结算查询-日结结果："+responseModel);
            if (responseModel.getResponseResult()) {
                pagination.setList(responseModel.getList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
            } else {
                responseDTO.setSuccess(false);
            }
        }catch (Exception e){
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequestMapping("redirectPayClearDay")
    public String redirectPayClearDay() {
        return "settlement/clear/detail/redirectPayClearDay";
    }

    @OperateLogger(content = "清分明细",operationType = OperateLogger.OperationType.U)
    @RequestMapping("redirectPaySettleDetail")
    @ResponseBody
    public ResponseDTO<Pagination<PaySettleDetailModel>> redirectPaySettleDetail(Pagination<PaySettleDetailModel> pagination, String orgCode, String settleDate) {
        ResponseDTO<Pagination<PaySettleDetailModel>> responseDTO = new ResponseDTO<Pagination<PaySettleDetailModel>>();
        try{
            PaySettleDetailModel paySettleDetailModel = new PaySettleDetailModel();
            paySettleDetailModel.setPayOrgCode(orgCode);
            paySettleDetailModel.setSettleDate(DateUtils.parse(settleDate));
            RequestModel<PaySettleDetailModel> requestModel = new RequestModel<PaySettleDetailModel>();
            requestModel.setRequest(paySettleDetailModel);
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            log.info("机构清分明细查询参数："+requestModel);
            ResponseModel<PaySettleDetailModel> responseModel = settleDetailInterface.getPaySettleDetailModel(requestModel);
            log.info("机构清分明细查询结果："+responseModel);
            OrgSumPageModel orgSumPageModel =  responseModel.getOrgSumPageModel();
            if (responseModel.getResponseResult()) {
                pagination.setRowCount(orgSumPageModel.getPageSum());
                pagination.setList(responseModel.getList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
                responseDTO.setSumObject(orgSumPageModel);
            } else {
                responseDTO.setSuccess(false);
            }
        }catch (Exception e){
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequiresPermissions("payClearDetail_down")
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(OrgClearDetailModel orgClearDetailModel,
                                           String beginDate, String endDate){
        ResponseEntity<byte[]> responseEntity;
        File tempFile=null;

        try{
            InputStream inputStream= new FileInputStream(constants.getExcelPath()+"/pay_clear_detail_template.xlsx");
            tempFile = fileDownAjax.touch("支付本金结算"+ DateUtils.now() + ".xlsx");
            String time = StringCommonUtils.insertStr(beginDate,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(endDate,"-",4,7);
            orgClearDetailModel.setClrType("02");
            orgClearDetailModel.setClrDateBegin(DateUtils.parse(beginDate));
            orgClearDetailModel.setClrDateEnd(DateUtils.parse(endDate));
            RequestModel<OrgClearDetailModel> requestModel = new RequestModel<OrgClearDetailModel>();
            requestModel.setRequest(orgClearDetailModel);
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);
            ResponseModel<OrgClearDetailModel> responseModel = clearDetailInterface.getPayClearDetailModel(requestModel);

            LinkedList<Header> tHeaders = new LinkedList<>();
            List<Object> datas = new ArrayList<>();

            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("支付机构代码", "orgCode",ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("起始", "settleDateBegin"));
            tHeaders.add(new HeaderExt("截止", "settleDateEnd"));
            tHeaders.add(new HeaderExt("结算日期", "clrDate"));
            tHeaders.add(new HeaderExt("结算批次号", "clrBatchNo"));
            tHeaders.add(new HeaderExt("结算标识", "clrFlag", ExcelContentExt.CLR_FLAG));
            tHeaders.add(new HeaderExt("结算类型", "clrType",ExcelContentExt.CLR_TYPE));
            tHeaders.add(new HeaderExt("应收笔数", "transCountR"));
            tHeaders.add(new HeaderExt("应收金额（元）", "transAmtR",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应退笔数", "transCountP"));
            tHeaders.add(new HeaderExt("应退金额（元）", "transAmtP", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("结算净额(元)","sttleAmt",ExcelContentExt.CURRENCY));

            if(responseModel.getResponseResult()){
                int i=1;
                for(OrgClearDetailModel orgClearDetailModel1 : responseModel.getList()){
                    Map map= Bean2MapUtil.convertBean(orgClearDetailModel1);
                    map.put("index",i);
                    map.put("sttleAmt",orgClearDetailModel1.getTransAmtR().subtract(orgClearDetailModel1.getTransAmtP()));
                    i++;
                    datas.add(map);
                }
                OrgSumPageModel orgSumPageModel =  responseModel.getOrgSumPageModel();
                Map m=new HashMap();
                m.put("clrType","总计：");
                m.put("transAmtP",orgSumPageModel.getTransAmtP());
                m.put("transCountP",orgSumPageModel.getTransCountBP());
                m.put("transAmtR",orgSumPageModel.getTransAmtR());
                m.put("transCountR",orgSumPageModel.getTransCountBR());
                m.put("sttleAmt",orgSumPageModel.getTransAmtR().subtract(orgSumPageModel.getTransAmtP()));
                datas.add(m);
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                config.setStartWith(4);
                FileOutputStream outputStream = new FileOutputStream(tempFile);

                ExcelTemplateUtil.write(inputStream, outputStream, config,time);

                responseEntity = fileDownAjax.getResponseEntity(tempFile);
            }else {
                responseEntity = fileDownAjax.getResponseEntityFail();
            }
        }catch (Exception e){
            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }

    @RequiresPermissions("payFeeClearDetail_down")
    @RequestMapping("downloadFeeClear")
    public ResponseEntity<byte[]> downloadFeeClear(OrgClearDetailModel orgClearDetailModel,
                                           String beginDate, String endDate){
        ResponseEntity<byte[]> responseEntity;
        File tempFile=null;

        try{

            InputStream inputStream= new FileInputStream(constants.getExcelPath()+"/pay_fee_clear_template.xlsx");
            tempFile = fileDownAjax.touch("支付手续费结算"+ DateUtils.now() + ".xlsx");
            String time = StringCommonUtils.insertStr(beginDate,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(endDate,"-",4,7);
            orgClearDetailModel.setClrType("03");
            orgClearDetailModel.setClrDateBegin(DateUtils.parse(beginDate));
            orgClearDetailModel.setClrDateEnd(DateUtils.parse(endDate));
            RequestModel<OrgClearDetailModel> requestModel = new RequestModel<OrgClearDetailModel>();
            requestModel.setRequest(orgClearDetailModel);
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);
            ResponseModel<OrgClearDetailModel> responseModel = clearDetailInterface.getPayClearDetailModel(requestModel);

            LinkedList<Header> tHeaders = new LinkedList<>();
            List<Object> datas = new ArrayList<>();

            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("支付机构代码", "orgCode",ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("起始", "settleDateBegin"));
            tHeaders.add(new HeaderExt("截止", "settleDateEnd"));
            tHeaders.add(new HeaderExt("结算日期", "clrDate"));
            tHeaders.add(new HeaderExt("结算批次号", "clrBatchNo"));
            tHeaders.add(new HeaderExt("结算标识", "clrFlag", ExcelContentExt.CLR_FLAG));
            tHeaders.add(new HeaderExt("结算类型", "clrType",ExcelContentExt.CLR_TYPE));
            tHeaders.add(new HeaderExt("手续费收取方式", "feeClearType",ExcelContentExt.FEE_CLEAR_TYPE));
            tHeaders.add(new HeaderExt("应收笔数", "transCountR"));
            tHeaders.add(new HeaderExt("应收金额（元）", "transAmtR",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应退笔数", "transCountP"));
            tHeaders.add(new HeaderExt("应退金额（元）", "transAmtP", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("本金净额（元）","settleAmt",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应付手续费（元）", "feeAmtP",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应退手续费（元）", "feeAmtR",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("手续费净额（元）","feeSettleAmt",ExcelContentExt.CURRENCY));


            if(responseModel.getResponseResult()){

                int i=1;
                for(OrgClearDetailModel orgClearDetailModel1 : responseModel.getList()){
                    Map map= Bean2MapUtil.convertBean(orgClearDetailModel1);
                    map.put("index",i);
                    map.put("settleAmt",orgClearDetailModel1.getTransAmtR().subtract(orgClearDetailModel1.getTransAmtP()));
                    map.put("feeSettleAmt",orgClearDetailModel1.getFeeAmtP().subtract(orgClearDetailModel1.getFeeAmtR()));
                    i++;
                    datas.add(map);
                }
                OrgSumPageModel orgSumPageModel = responseModel.getOrgSumPageModel();
                Map m=new HashMap();
                m.put("feeClearType","总计：");
                m.put("transAmtR",orgSumPageModel.getTransAmtR());
                m.put("transCountR",orgSumPageModel.getTransCountBR());
                m.put("transAmtP",orgSumPageModel.getTransAmtP());
                m.put("transCountP",orgSumPageModel.getTransCountBP());
                m.put("settleAmt",orgSumPageModel.getTransAmtR().subtract(orgSumPageModel.getTransAmtP()));
                m.put("feeAmtP",orgSumPageModel.getFeeAmtP());
                m.put("feeAmtR",orgSumPageModel.getFeeAmtR());
                m.put("feeSettleAmt",orgSumPageModel.getFeeAmtP().subtract(orgSumPageModel.getFeeAmtR()));
                datas.add(m);
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                config.setStartWith(4);
                FileOutputStream outputStream = new FileOutputStream(tempFile);

                ExcelTemplateUtil.write(inputStream, outputStream, config,time);

                responseEntity = fileDownAjax.getResponseEntity(tempFile);
            }else {
                responseEntity = fileDownAjax.getResponseEntityFail();
            }
        }catch (Exception e){
            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }
}
