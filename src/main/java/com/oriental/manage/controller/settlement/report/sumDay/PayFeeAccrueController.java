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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 支付银行手续费计提表
 * Created by zhangxinhai on 2016/6/29.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/sumDay/payFeeAccrue")
public class PayFeeAccrueController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private FeeAccrueInterface feeAccrueInterface;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private Constants constants;
    @RequestMapping("init")
    @RequiresPermissions("payFeeAccrue_search")
    public String init() {
        return "settlement/report/sumDay/payFeeAccrue";
    }

    @OperateLogger(content = "查询支付银行手续费计提表",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("payFeeAccrue_search")
    @ResponseBody
    public ResponseDTO<Pagination<FeeAccrueModel>> queryPage(@RequestBody Pagination<FeeAccrueModel> pagination) {
        ResponseDTO<Pagination<FeeAccrueModel>> responseDTO = new ResponseDTO<Pagination<FeeAccrueModel>>();
        try {
            RequestModel<FeeAccrueModel> requestModel = new RequestModel<FeeAccrueModel>();
            requestModel.setRequest(pagination.getQueryBean());
            log.info("支付银行手续费计提报表查询参数："+requestModel);
            ResponseModel<FeeAccrueModel> responseModel = feeAccrueInterface.getPayFeeAccrueModel(requestModel);
            log.info("支付银行手续费计提报表查询结果："+responseModel);
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

    @RequiresPermissions("payFeeAccrue_down")
    @RequestMapping("download")
    @ResponseBody
    public ResponseEntity<byte[]> download(@RequestBody FeeAccrueModel feeAccrueModel){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;

        try{
//            InputStream inputStream = new FileInputStream("C:/Users/wangjun/Desktop/支付银行手续费计提表模板.xlsx");
            InputStream inputStream = new FileInputStream(constants.getExcelPath()+"/支付银行手续费计提表模板.xlsx");
            tempFile = fileDownAjax.touch("支付银行手续费计提表-" + DateUtils.now() + ".xlsx");
            String beginDate = DateUtils.format(feeAccrueModel.getClrDate(),DateUtils.DATESHOWFORMAT);
            RequestModel<FeeAccrueModel> requestModel = new RequestModel<FeeAccrueModel>();
//            requestModel.setPageNo(1);
//            requestModel.setPageSize(10000);
            requestModel.setRequest(feeAccrueModel);
            ResponseModel<FeeAccrueModel> responseModel = feeAccrueInterface.getPayFeeAccrueModel(requestModel);

            if(responseModel.getResponseResult()){
                //表头
                LinkedList<Header> tHeaders = new LinkedList<>();
                //内容
                List<Object> datas = new ArrayList<>();
                //添加表头
                tHeaders.add(new HeaderExt("支付机构代码", "orgCode", ExcelContentExt.ORGANIZE));
                tHeaders.add(new HeaderExt("计提周期", "settleDateBegin"));
                tHeaders.add(new HeaderExt("支付笔数", "receivableCount"));
                tHeaders.add(new HeaderExt("支付金额（元）", "receivableAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("退款笔数", "payableCount"));
                tHeaders.add(new HeaderExt("退款金额（元）", "payableAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("支付笔数", "count"));
                tHeaders.add(new HeaderExt("支付金额（元）", "amt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("佣金金额", "FeeAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("佣金费率", "fixFee"));
                if(responseModel.getList()!=null &&responseModel.getList().size()>0){
                    for(FeeAccrueModel feeAccrueModel1 : responseModel.getList()){
                        Map map = Bean2MapUtil.convertBean(feeAccrueModel1);
                        String  settleDateBegin = DateUtils.format(feeAccrueModel1.getSettleDateBegin(),DateUtils.DATESPRITFORMAT);
                        String  settleDateEnd = DateUtils.format(feeAccrueModel1.getSettleDateEnd(),DateUtils.DATESPRITFORMAT);
                        map.put("settleDateBegin",settleDateBegin+"-"+settleDateEnd);
                        map.put("count",(feeAccrueModel1.getPayableCount()+feeAccrueModel1.getReceivableCount()));
                        map.put("amt",feeAccrueModel1.getPayableAmt().add(feeAccrueModel1.getReceivableAmt()));
                        map.put("FeeAmt",feeAccrueModel1.getReceivableFeeAmt().subtract(feeAccrueModel1.getPayableFeeAmt()));
                        datas.add(map);
                    }
                }
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                config.setStartWith(4);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                ExcelTemplateUtil.write(inputStream, outputStream, config,beginDate);
                responseEntity = fileDownAjax.getResponseEntity(tempFile);
            }
            else {
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
