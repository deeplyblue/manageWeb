package com.oriental.manage.controller.settlement.clear.detail;

import com.oriental.clearDubbo.api.clear.day.ClearDayInterface;
import com.oriental.clearDubbo.api.clear.detail.ClearDetailInterface;
import com.oriental.clearDubbo.api.settle.settle.SettleDetailInterface;
import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDayModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDetailModel;
import com.oriental.clearDubbo.model.settle.mchnt.MchntSettleDetailModel;
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
@RequestMapping("settlement/clear/detail/mchntClearDetail")
public class MchntClearDetailController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ClearDetailInterface clearDetailInterface;
    @Autowired
    private ClearDayInterface clearDayInterface;
    @Autowired
    private SettleDetailInterface settleDetailInterface;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private Constants constants;

    @RequestMapping("init")
    @RequiresPermissions("mchntClearDetail_search")
    public String init() {

        return "settlement/clear/detail/mchntClearDetail";
    }

    @OperateLogger(content = "商户本金结算查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("mchntClearDetail_search")
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
            log.info("商户本金结算查询参数："+requestModel);
            ResponseModel<OrgClearDetailModel> responseModel = clearDetailInterface.getMchntClearDetailModel(requestModel);
            log.info("商户本金结算查询结果："+responseModel);
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

    @OperateLogger(content = "结算明细",operationType = OperateLogger.OperationType.R)
    @RequestMapping("getMchntClearDay")
    @ResponseBody
    public ResponseDTO<Pagination<OrgClearDayModel>> queryMchntClearDay(String payOrgCode, String orgCode, String settleDateBegin, String settleDateEnd) {
        ResponseDTO<Pagination<OrgClearDayModel>> responseDTO = new ResponseDTO<Pagination<OrgClearDayModel>>();
        Pagination<OrgClearDayModel> pagination = new Pagination<OrgClearDayModel>();
        try{
            OrgClearDayModel orgClearDayModel = new OrgClearDayModel();
            orgClearDayModel.setPayOrgCode(payOrgCode);
            orgClearDayModel.setOrgCode(orgCode);
            orgClearDayModel.setSettleDateBegin(DateUtils.parse(settleDateBegin));
            orgClearDayModel.setSettleDateEnd(DateUtils.parse(settleDateEnd));
            RequestModel<OrgClearDayModel> requestModel = new RequestModel<OrgClearDayModel>();
            requestModel.setPageSize(100);
            requestModel.setRequest(orgClearDayModel);
            log.info("商户结算明细查询参数："+requestModel);
            ResponseModel<OrgClearDayModel> responseModel = clearDayInterface.getMchntClearDayModel(requestModel);
            log.info("商户结算明细查询结果："+responseModel);
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

    @RequestMapping("redirectMchntClearDay")
    public String redirectMchntClearDay() {
        return "settlement/clear/detail/redirectMchntClearDay";
    }

    @OperateLogger(content = "清分明细",operationType = OperateLogger.OperationType.R)
    @RequestMapping("redirectMchntSettleDetail")
    @ResponseBody
    public ResponseDTO<Pagination<MchntSettleDetailModel>> redirectMchntSettleDetail(Pagination<MchntSettleDetailModel> pagination, String orgCode, String settleDate) {
        ResponseDTO<Pagination<MchntSettleDetailModel>> responseDTO = new ResponseDTO<Pagination<MchntSettleDetailModel>>();
        try{
            MchntSettleDetailModel mchntSettleDetailModel = new MchntSettleDetailModel();
            mchntSettleDetailModel.setMchntCode(orgCode);
            mchntSettleDetailModel.setSettleDate(DateUtils.parse(settleDate));
            RequestModel<MchntSettleDetailModel> requestModel = new RequestModel<MchntSettleDetailModel>();
            requestModel.setRequest(mchntSettleDetailModel);
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            log.info("商户清分明细查询参数："+requestModel);
            ResponseModel<MchntSettleDetailModel> responseModel = settleDetailInterface.getMchntSettleDetailModel(requestModel);
            log.info("商户清分明细查询结果："+responseModel);
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
    @RequiresPermissions("mchntClearDetail_down")
    @RequestMapping("download")
    public ResponseEntity<byte[]>download(OrgClearDetailModel orgClearDetailModel,
                                          String beginDate, String endDate){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try{

            InputStream inputStream = new FileInputStream(constants.getExcelPath() +"/mchnt_clear_detail_template.xlsx");
            tempFile = fileDownAjax.touch("商户本金结算查询 -" + DateUtils.now() + ".xlsx");
            String time = StringCommonUtils.insertStr(beginDate,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(endDate,"-",4,7);
            orgClearDetailModel.setClrType("02");
            orgClearDetailModel.setClrDateBegin(DateUtils.parse(beginDate));
            orgClearDetailModel.setClrDateEnd(DateUtils.parse(endDate));
            RequestModel<OrgClearDetailModel> requestModel = new RequestModel<OrgClearDetailModel>();
            requestModel.setRequest(orgClearDetailModel);
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);
            ResponseModel<OrgClearDetailModel> responseModel = clearDetailInterface.getMchntClearDetailModel(requestModel);

            LinkedList<Header> tHeaders = new LinkedList<>();
            List<Object> datas = new ArrayList<>();

            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("商户代码", "orgCode",ExcelContentExt.MCHNT));
            tHeaders.add(new HeaderExt("机构代码", "payOrgCode",ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("起始", "settleDateBegin"));
            tHeaders.add(new HeaderExt("截止", "settleDateEnd"));
            tHeaders.add(new HeaderExt("结算日期", "clrDate"));
            tHeaders.add(new HeaderExt("结算批次号", "clrBatchNo"));
            tHeaders.add(new HeaderExt("结算标识", "clrFlag",ExcelContentExt.CLR_FLAG));
            tHeaders.add(new HeaderExt("结算类型", "clrType",ExcelContentExt.CLR_TYPE));
            tHeaders.add(new HeaderExt("应付笔数", "transCountP"));
            tHeaders.add(new HeaderExt("应付金额（元）", "transAmtP", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应退笔数", "transCountR"));
            tHeaders.add(new HeaderExt("应退金额（元）", "transAmtR",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("结算净额（元）","sttleAmt",ExcelContentExt.CURRENCY));
            int i=1;
            if(responseModel.getResponseResult()){
                for(OrgClearDetailModel orgClearDetailModel1 : responseModel.getList()){
                    Map map = Bean2MapUtil.convertBean(orgClearDetailModel1);
                    map.put("index",i);
                    map.put("sttleAmt",orgClearDetailModel1.getTransAmtP().subtract(orgClearDetailModel1.getTransAmtR()));
                    datas.add(map);
                    i++;
                }
                OrgSumPageModel orgSumPageModel =  responseModel.getOrgSumPageModel();
                Map m=new HashMap();
                m.put("clrType","总计：");
                m.put("transAmtP",orgSumPageModel.getTransAmtP());
                m.put("transCountP",orgSumPageModel.getTransCountBP());
                m.put("transAmtR",orgSumPageModel.getTransAmtR());
                m.put("transCountR",orgSumPageModel.getTransCountBR());
                m.put("sttleAmt",orgSumPageModel.getTransAmtP().subtract(orgSumPageModel.getTransAmtR()));
                datas.add(m);
            }

            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            config.setStartWith(4);
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            ExcelTemplateUtil.write(inputStream, outputStream, config,time);

            responseEntity = fileDownAjax.getResponseEntity(tempFile);
        }catch (Exception e){
            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }
    @RequiresPermissions("mchntFeeClearDetail_down")
    @RequestMapping("downloadFeeClear")
    public ResponseEntity<byte[]>downloadFeeClear(OrgClearDetailModel orgClearDetailModel,
                                          String beginDate, String endDate){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try{

            InputStream inputStream = new FileInputStream(constants.getExcelPath() +"/mchnt_fee_clear_template.xlsx");
            tempFile = fileDownAjax.touch("商户手续费结算查询 -" + DateUtils.now() + ".xlsx");
            String time = StringCommonUtils.insertStr(beginDate,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(endDate,"-",4,7);
            orgClearDetailModel.setClrType("03");
            orgClearDetailModel.setClrDateBegin(DateUtils.parse(beginDate));
            orgClearDetailModel.setClrDateEnd(DateUtils.parse(endDate));
            RequestModel<OrgClearDetailModel> requestModel = new RequestModel<OrgClearDetailModel>();
            requestModel.setRequest(orgClearDetailModel);
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);
            ResponseModel<OrgClearDetailModel> responseModel = clearDetailInterface.getMchntClearDetailModel(requestModel);

            LinkedList<Header> tHeaders = new LinkedList<>();
            List<Object> datas = new ArrayList<>();

            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("商户代码", "orgCode",ExcelContentExt.MCHNT));
            tHeaders.add(new HeaderExt("起始", "settleDateBegin"));
            tHeaders.add(new HeaderExt("截止", "settleDateEnd"));
            tHeaders.add(new HeaderExt("结算日期", "clrDate"));
            tHeaders.add(new HeaderExt("结算批次号", "clrBatchNo"));
            tHeaders.add(new HeaderExt("结算标识", "clrFlag",ExcelContentExt.CLR_FLAG));
            tHeaders.add(new HeaderExt("结算类型", "clrType",ExcelContentExt.CLR_TYPE));
            tHeaders.add(new HeaderExt("手续费收取方式", "feeClearType",ExcelContentExt.FEE_CLEAR_TYPE));
            tHeaders.add(new HeaderExt("应付笔数", "transCountP"));
            tHeaders.add(new HeaderExt("应付金额（元）", "transAmtP", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应退笔数", "transCountR"));
            tHeaders.add(new HeaderExt("应退金额（元）", "transAmtR",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("本金净额(元)","sttleAmt",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应收佣金（元）", "feeAmtR",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应退佣金（元）", "feeAmtP",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("佣金净额（元）","feeSettleAmt",ExcelContentExt.CURRENCY));


            int i=1;
            if(responseModel.getResponseResult()){
                if(responseModel.getList().size()>0 && responseModel.getList() !=null){

                    for(OrgClearDetailModel orgClearDetailModel1 : responseModel.getList()){
                        Map map = Bean2MapUtil.convertBean(orgClearDetailModel1);
                        map.put("index",i);
                        map.put("sttleAmt",orgClearDetailModel1.getTransAmtP().subtract(orgClearDetailModel1.getTransAmtR()));
                        map.put("feeSettleAmt",orgClearDetailModel1.getFeeAmtR().subtract(orgClearDetailModel1.getFeeAmtP()));
                        datas.add(map);
                        i++;
                    }
                    OrgSumPageModel orgSumPageModel =  responseModel.getOrgSumPageModel();
                    Map m=new HashMap();
                    m.put("feeClearType","总计：");
                    m.put("transAmtP",orgSumPageModel.getTransAmtP());
                    m.put("transCountP",orgSumPageModel.getTransCountBP());
                    m.put("transAmtR",orgSumPageModel.getTransAmtR());
                    m.put("transCountR",orgSumPageModel.getTransCountBR());
                    m.put("feeAmtR",orgSumPageModel.getFeeAmtR());
                    m.put("feeAmtP",orgSumPageModel.getFeeAmtP());
                    m.put("sttleAmt",orgSumPageModel.getTransAmtP().subtract(orgSumPageModel.getTransAmtR()));
                    m.put("feeSettleAmt",orgSumPageModel.getFeeAmtR().subtract(orgSumPageModel.getFeeAmtP()));
                    datas.add(m);
                }
            }

            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            config.setStartWith(4);
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            ExcelTemplateUtil.write(inputStream, outputStream, config,time);

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
