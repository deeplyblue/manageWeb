package com.oriental.manage.controller.report;

import com.oriental.check.commons.util.DateUtil;
import com.oriental.check.service.facade.err.model.BankChkErrDTO;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.exception.BusiException;
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
import com.oriental.manage.core.utils.StringCommonUtils;
import com.oriental.manage.pojo.report.PayBusiCheckReportData;
import com.oriental.manage.service.report.IPayBusiCheckReportService;
import com.oriental.paycenter.commons.mode.Response;
import com.oriental.settlementfront.service.facade.manager.BankTransDetailFacade;
import com.oriental.settlementfront.service.facade.manager.model.PayBusiErrDTO;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>支付业务核对报表控制器</li>
 * <li>User:蒯越 Date:2016/12/5 Time:15:36</li>
 * </ul>
 */
@Slf4j
@Controller
public class PayBusiCheckReportController {

    @Autowired
    private IPayBusiCheckReportService payBusiCheckReportService;

    @Autowired
    private BankTransDetailFacade bankTransDetailFacade;

    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private com.oriental.manage.core.system.Constants Constants;

    @RequestMapping("/report/payBusiCheckReport/init")
    public String init() {
        return "report/searchPayBusiCheckReport";
    }

    @OperateLogger(content = "查询支付业务核对报表", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/report/payBusiCheckReport/search")
    @RequiresPermissions("payBusiCheckReport_search")
    @ResponseBody
    public ResponseDTO<Pagination<PayBusiCheckReportData>> search(Pagination<PayBusiCheckReportData> pagination,
                                                                  PayBusiCheckReportData query,
                                                                  String sDateStart,
                                                                  String sDateEnd) {
        ResponseDTO<Pagination<PayBusiCheckReportData>> responseDTO = new ResponseDTO<>();
        log.info("查询信息:{},{}", query, pagination);
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sDateStart));
            query.setDateEnd(DateUtil.parse(sDateEnd));
            payBusiCheckReportService.pageQuery(pagination, query);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (BusiException e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("/report/payBusiCheckReport/toSearchErrDetail")
    public String toSearchErrDetail() {
        return "report/searchPayBusiCheckReportErrDetail";
    }

    @OperateLogger(content = "查询支付业务核对报表-差异明细", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/report/payBusiCheckReport/searchErrDetail")
    @ResponseBody
    public ResponseDTO<Pagination<PayBusiErrDTO>> searchErrDetail(Pagination<PayBusiErrDTO> pagination,
                                                                  PayBusiErrDTO query,
                                                                  String sSettleDate) {
        ResponseDTO<Pagination<PayBusiErrDTO>> responseDTO = new ResponseDTO<>();
        log.info("查询信息:{},{}", query, pagination);
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sSettleDate));
            query.setDateEnd(DateUtil.parse(sSettleDate));
            query.setPageSize(10000);
            Response<com.oriental.settlementfront.service.facade.common.model.Pagination<PayBusiErrDTO>> response
                    = bankTransDetailFacade.pageQueryPayBusiErr(query, SessionUtils.getUserName());
            if (response.isSuccess()) {
                pagination.setRowCount(response.getResult().getTotalCount());
                pagination.setList(response.getResult().getPageList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
                responseDTO.setSumObject(response.getResult().getSumData());
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("/report/downloadPayBusiCheckReport")
    @ResponseBody
    public ResponseEntity<byte[]> download (PayBusiCheckReportData query,
                                            String oDateStart,
                                            String oDateEnd){
        ResponseEntity<byte[]> responseEntity=null;
        File tempFile = null;
        try{
            InputStream inputStream = new FileInputStream(Constants.getExcelPath() + "/pay_busi_check_template.xlsx");
            tempFile = fileDownAjax.touch("支付业务核对报表-" + DateUtils.now() + ".xlsx");
            Pagination<PayBusiCheckReportData> pagination = new Pagination<>();
            String titleTime= StringCommonUtils.insertStr(oDateStart,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(oDateEnd,"-",4,7);
            query.setStartRow(0);
            query.setPageSize(10000);
            query.setDateStart(DateUtil.parse(oDateStart));
            query.setDateEnd(DateUtil.parse(oDateEnd));
            payBusiCheckReportService.pageQuery(pagination, query);

            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("支付机构", "payOrgCode",ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("银行清算日期", "bankSettleDate"));
            tHeaders.add(new HeaderExt("笔数", "payCount"));
            tHeaders.add(new HeaderExt("金额", "payAmt", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "busiCount"));
            tHeaders.add(new HeaderExt("金额", "busiAmt", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "refundCount"));
            tHeaders.add(new HeaderExt("金额", "refundAmt", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("待处理差异金额","settleAmt", ExcelContentExt.CURRENCY));

            int i = 1;
            for(int j=0;j<pagination.getList().size();j++){
                Map map = Bean2MapUtil.convertBean(pagination.getList().get(j));
                map.put("index", i);
                map.put("settleAmt",pagination.getList().get(j).getPayAmt()-pagination.getList().get(j).getBusiAmt()
                        -pagination.getList().get(j).getRefundAmt());
                datas.add(map);
                i++;
            }
            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            config.setStartWith(4);
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            ExcelTemplateUtil.write(inputStream,outputStream,config,titleTime);
            responseEntity = fileDownAjax.getResponseEntity(tempFile);
        }catch(Exception e){
            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);

        }
        return responseEntity;
    }


}
