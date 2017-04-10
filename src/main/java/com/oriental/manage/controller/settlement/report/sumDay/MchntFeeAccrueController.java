package com.oriental.manage.controller.settlement.report.sumDay;

import com.oriental.clearDubbo.api.clear.report.FeeAccrueInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.report.FeeAccrueModel;
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

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 业务佣金收计提表
 * Created by zhangxinhai on 2016/6/29.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/sumDay/mchntFeeAccrue")
public class MchntFeeAccrueController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private FeeAccrueInterface feeAccrueInterface;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private Constants constants;

    @RequestMapping("init")
    @RequiresPermissions("mchntFeeAccrue_search")
    public String init() {
        return "settlement/report/sumDay/mchntFeeAccrue";
    }

    @OperateLogger(content = "查询业务佣金收计提表",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("mchntFeeAccrue_search")
    @ResponseBody
    public ResponseDTO<Pagination<FeeAccrueModel>> queryPage(@RequestBody Pagination<FeeAccrueModel> pagination) {
        ResponseDTO<Pagination<FeeAccrueModel>> responseDTO = new ResponseDTO<Pagination<FeeAccrueModel>>();
        try {
            RequestModel<FeeAccrueModel> requestModel = new RequestModel<FeeAccrueModel>();
            requestModel.setRequest(pagination.getQueryBean());
            log.info("业务佣金收计提报表查询参数："+requestModel);
            ResponseModel<FeeAccrueModel> responseModel = feeAccrueInterface.getMchntFeeAccrueModel(requestModel);
            log.info("业务佣金收计提报表查询结果："+responseModel);
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

    @RequiresPermissions("mchntFeeAccrue_down")
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestBody FeeAccrueModel feeAccrueModel) {

        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            InputStream inputStream = new FileInputStream(constants.getExcelPath().concat("/佣金收计提表模板.xlsx"));
            tempFile = fileDownAjax.touch("佣金收计提表-" + DateUtils.now() + ".xlsx");
            String beginDate = DateUtils.format(feeAccrueModel.getClrDate(),DateUtils.DATESHOWFORMAT);
            OutputStream outputStream = new FileOutputStream(tempFile);

            RequestModel<FeeAccrueModel> requestModel = new RequestModel<FeeAccrueModel>();
            requestModel.setRequest(feeAccrueModel);
            ResponseModel<FeeAccrueModel> responseModel = feeAccrueInterface.getMchntFeeAccrueModel(requestModel);
            if (responseModel.getResponseResult()) {
                LinkedList<Header> tHeaders = new LinkedList<>();
                List<Object> datas = new ArrayList<>();
                tHeaders.add(new HeaderExt("商户代码", "orgCode", ExcelContentExt.MCHNT));
                tHeaders.add(new HeaderExt("业务种类", "busiType", ExcelContentExt.MCHNT_BUSI_TYPE));
                tHeaders.add(new HeaderExt("计提周期", "periodic"));
                tHeaders.add(new HeaderExt("业务笔数", "payableCount"));
                tHeaders.add(new HeaderExt("业务金额（元）", "payableAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("退款笔数", "receivableCount"));
                tHeaders.add(new HeaderExt("退款金额（元）", "receivableAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("业务笔数", "payableCountD"));
                tHeaders.add(new HeaderExt("业务金额（元）", "payableAmtD", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("佣金金额（元）", "brokerage", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("佣金费率", "fixFee"));
                if(responseModel.getList() !=null && responseModel.getList().size()>0) {
                    for (FeeAccrueModel feeModel : responseModel.getList()) {
                        Map map = Bean2MapUtil.convertBean(feeModel);
                        String periodic = DateUtils.format(feeModel.getSettleDateBegin(), DateUtils.DATESPRITFORMAT).concat("—").concat(DateUtils.format(feeModel.getSettleDateEnd(), DateUtils.DATESPRITFORMAT));
                        BigDecimal brokerage = feeModel.getReceivableFeeAmt().subtract(feeModel.getPayableFeeAmt());
                        map.put("payableCountD", feeModel.getPayableCount() + feeModel.getReceivableCount());
                        map.put("payableAmtD", feeModel.getPayableAmt().add(feeModel.getReceivableAmt()));
                        map.put("periodic", periodic);
                        map.put("brokerage", brokerage);
                        datas.add(map);
                    }
               }
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setSheetName("佣金收计提表");
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                config.setStartWith(4);
                ExcelTemplateUtil.write(inputStream, outputStream, config,beginDate);
                responseEntity = fileDownAjax.getResponseEntity(tempFile);
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
