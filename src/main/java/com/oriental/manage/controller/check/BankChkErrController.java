package com.oriental.manage.controller.check;

import com.oriental.check.commons.util.DateUtil;
import com.oriental.check.service.facade.err.BankChkErrFacade;
import com.oriental.check.service.facade.err.model.BankChkErrDTO;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.excelUtils.ExcelUtils;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.IPUtil;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.paycenter.commons.mode.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * <ul>
 * <li>银行对账差异控制器</li>
 * <li>User:蒯越 Date:2016/5/11 Time:16:14</li>
 * </ul>
 */
@Slf4j
@Controller
public class BankChkErrController {
    @Autowired
    private FileDownAjax fileDownAjax;

    @Autowired
    private BankChkErrFacade bankChkErrFacade;
    @Autowired
    private Constants constants;

    @RequestMapping("/check/bankChkErr/init")
    public String init() {
        return "check/searchBankChkErr";
    }

    @OperateLogger(content = "银行对账差异查询", operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequestMapping("/check/bankChkErr/search")
    @RequiresPermissions("bankChkErr_search")
    public ResponseDTO<Pagination<BankChkErrDTO>> queryPage(Pagination<BankChkErrDTO> pagination,
                                                            BankChkErrDTO query,
                                                            String sDateStart,
                                                            String sDateEnd,
                                                            String sHandleDateStart,
                                                            String sHandleDateEnd) {
        ResponseDTO<Pagination<BankChkErrDTO>> responseDTO = new ResponseDTO<>();
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sDateStart));
            query.setDateEnd(DateUtil.parse(sDateEnd));
            if (StringUtils.isNotBlank(sHandleDateStart)) {
                query.setHandleDateStart(DateUtil.parse(sHandleDateStart + "000000", DateUtil.yyyyMMddHHmmss));
            }
            if (StringUtils.isNotBlank(sHandleDateEnd)) {
                query.setHandleDateEnd(DateUtil.parse(sHandleDateEnd + "235959", DateUtil.yyyyMMddHHmmss));
            }
            Response<com.oriental.check.service.facade.model.Pagination<BankChkErrDTO>> response
                    = bankChkErrFacade.pageQuery(query, SessionUtils.getUserName());
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

    @ResponseBody
    @RequestMapping("/check/bankChkErr/handle")
    public ResponseDTO<String> handle(String id, String handleType, HttpServletRequest request) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = bankChkErrFacade.handle(id, handleType, SessionUtils.getUserName(), IPUtil.getIpAddrByRequest(request));
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject(response.getResult());
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("处理失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("处理失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("/check/bankChkErr/reportInit")
    public String reportInit() {
        return "check/searchBankChkErrReport";
    }

    @ResponseBody
    @RequestMapping("/check/bankChkErr/searchReport")
    public ResponseDTO<List<Map<String, String>>> searchReport(String sSettleDate) {
        ResponseDTO<List<Map<String, String>>> responseDTO = new ResponseDTO<>();
        try {
            Date settleDate = DateUtil.parse(sSettleDate);
            Response<List<Map<String, String>>> response = bankChkErrFacade.selectReportData(
                    settleDate, SessionUtils.getUserName());
            if (response.isSuccess()) {
                List<Map<String, String>> list = response.getResult();
                responseDTO.setSuccess(true);
                responseDTO.setObject(list);
                if (list.size() > 0) {
                    Map<String, String> removeMap = list.remove(list.size() - 1);
                    responseDTO.setSumObject(removeMap);
                }
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("查询报表失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("查询报表失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("/check/bankChkErr/downloadReport")
    @ResponseBody
    public ResponseEntity<byte[]> downloadReport(String oDate) {
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {

            InputStream inputStream = new FileInputStream(constants.getExcelPath() + "/业务差错报表模板.xlsx");
            tempFile = fileDownAjax.touch("业务差异报表模板-" + DateUtils.now() + ".xlsx");
            Date settleDate = DateUtil.parse(oDate);
            Response<List<Map<String, String>>> response = bankChkErrFacade.selectReportData(
                    settleDate, SessionUtils.getUserName());
            if (response.isSuccess()) {
                //表头
                LinkedList<Header> tHeaders = new LinkedList<>();
                //内容
                List<Object> datas = new ArrayList<>();
                //添加表头
                tHeaders.add(new HeaderExt("业务类型", "type", ExcelContentExt.BUSI_TYPE));
                tHeaders.add(new HeaderExt("差异类型", "ERR_TYPE_DESC"));
                tHeaders.add(new HeaderExt("笔数", "NO_HANDLE_DAY_COUNT"));
                tHeaders.add(new HeaderExt("金额", "NO_HANDLE_DAY_AMT", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "HANDLED_DAY_COUNT"));
                tHeaders.add(new HeaderExt("金额", "HANDLED_DAY_AMT", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "NO_HANDLE_MONTH_COUNT"));
                tHeaders.add(new HeaderExt("金额", "NO_HANDLE_MONTH_AMT", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "HANDLED_MONTH_COUNT"));
                tHeaders.add(new HeaderExt("金额", "HANDLED_MONTH_AMT", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "NO_HANDLE_YEAR_COUNT"));
                tHeaders.add(new HeaderExt("金额", "NO_HANDLE_YEAR_AMT", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "HANDLED_YEAR_COUNT"));
                tHeaders.add(new HeaderExt("金额", "HANDLED_YEAR_AMT", ExcelContentExt.CURRENCY));

                for (int i = 0; i < response.getResult().size(); i++) {
                    Map map = Bean2MapUtil.convertBean(response.getResult().get(i));
                    if (response.getResult().get(i).get("BUSI_TYPE") == null) {
                        map.put("type", "未知业务");
                    } else {
                        map.put("type", response.getResult().get(i).get("BUSI_TYPE"));
                    }
                    if (response.getResult().get(i).get("ERR_TYPE_DESC") == null) {
                        map.put("ERR_TYPE_DESC", "小计");
                    } else {
                        map.put("ERR_TYPE_DESC", response.getResult().get(i).get("ERR_TYPE_DESC"));
                    }
                    if (i == response.getResult().size() - 1) {
                        map.put("type", "总计");
                        map.put("ERR_TYPE_DESC", "总计");
                    }
                    map.put("NO_HANDLE_DAY_COUNT", response.getResult().get(i).get("NO_HANDLE_DAY_COUNT"));
                    map.put("NO_HANDLE_DAY_AMT", response.getResult().get(i).get("NO_HANDLE_DAY_AMT"));
                    map.put("HANDLED_DAY_COUNT", response.getResult().get(i).get("HANDLED_DAY_COUNT"));
                    map.put("HANDLED_DAY_AMT", response.getResult().get(i).get("HANDLED_DAY_AMT"));
                    map.put("NO_HANDLE_MONTH_COUNT", response.getResult().get(i).get("NO_HANDLE_MONTH_COUNT"));
                    map.put("NO_HANDLE_MONTH_AMT", response.getResult().get(i).get("NO_HANDLE_MONTH_AMT"));
                    map.put("HANDLED_MONTH_COUNT", response.getResult().get(i).get("HANDLED_MONTH_COUNT"));
                    map.put("HANDLED_MONTH_AMT", response.getResult().get(i).get("HANDLED_MONTH_AMT"));
                    map.put("NO_HANDLE_YEAR_COUNT", response.getResult().get(i).get("NO_HANDLE_YEAR_COUNT"));
                    map.put("NO_HANDLE_YEAR_AMT", response.getResult().get(i).get("NO_HANDLE_YEAR_AMT"));
                    map.put("HANDLED_YEAR_COUNT", response.getResult().get(i).get("HANDLED_YEAR_COUNT"));
                    map.put("HANDLED_YEAR_AMT", response.getResult().get(i).get("HANDLED_YEAR_AMT"));
                    datas.add(map);

                }
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                config.setStartWith(2);
                config.mergeColumn("type");

                FileOutputStream outputStream = new FileOutputStream(tempFile);

                ExcelTemplateUtil.write(inputStream, outputStream, config);
                File file = ExcelUtils.addTitle(tempFile, "业务差异报表", fileDownAjax, tHeaders.size(), oDate, "");
                responseEntity = fileDownAjax.getResponseEntity(file);

            } else {
                responseEntity = fileDownAjax.getResponseEntityFail();
            }
        } catch (Exception e) {
            log.error("下载失败", e);
            fileDownAjax.forceDelete(tempFile);
            responseEntity = fileDownAjax.getResponseEntityFail();
        } finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }


    @ResponseBody
    @RequestMapping("/check/bankChkErr/batchHandle")
    @RequiresPermissions(value = {"bankChkErr_operation","bankChkErr_operationBank"},logical = Logical.OR)
    public ResponseDTO<String> batchHandle(@RequestParam("ids[]") String[] ids,
                                           String handleType,
                                           HttpServletRequest request) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            for (String id : ids) {
                Response<String> response = bankChkErrFacade.handle(id, handleType, SessionUtils.getUserName(), IPUtil.getIpAddrByRequest(request));
                log.info("批量差异处理,差异ID【{}】处理结果【{}】", id, response);
            }
            responseDTO.setSuccess(true);
            responseDTO.setObject("批量处理成功");
        } catch (BusiException e) {
            log.error("处理失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("处理失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("/check/bankChkErr/download")
    @RequiresPermissions("bankChkErr_download")
    @ResponseBody
    public ResponseEntity<byte[]> download(BankChkErrDTO bankChkErrDTO,
                                           String oDateStart,
                                           String oDateEnd) {
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            tempFile = fileDownAjax.touch("银行对账差异报表-" + DateUtils.now() + ".xlsx");
            bankChkErrDTO.setStartRow(0);
            bankChkErrDTO.setPageSize(10000);
            bankChkErrDTO.setDateStart(DateUtil.parse(oDateStart));
            bankChkErrDTO.setDateEnd(DateUtil.parse(oDateEnd));
            Response<com.oriental.check.service.facade.model.Pagination<BankChkErrDTO>> response
                    = bankChkErrFacade.pageQuery(bankChkErrDTO, SessionUtils.getUserName());

            if (response.isSuccess()) {
                //表头
                LinkedList<Header> tHeaders = new LinkedList<>();
                //内容
                List<Object> datas = new ArrayList<>();
                tHeaders.add(new HeaderExt("序号", "index"));
                tHeaders.add(new HeaderExt("支付机构", "payOrgCode", ExcelContentExt.ORGANIZE));
                tHeaders.add(new HeaderExt("银行请求流水号", "bankReqNo"));
                tHeaders.add(new HeaderExt("银行响应流水号", "bankRespNo"));
                tHeaders.add(new HeaderExt("清算日期", "extSettleDate"));
                tHeaders.add(new HeaderExt("平台清算日期", "settleDate"));
                tHeaders.add(new HeaderExt("商户", "platformCode", ExcelContentExt.MCHNT));
                tHeaders.add(new HeaderExt("支付流水号", "innerPayTransNo"));
                tHeaders.add(new HeaderExt("交易类型", "transCodeDesc"));
                tHeaders.add(new HeaderExt("系统交易金额", "transAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("外部系统交易金额", "extTransAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("外部交易状态", "extTransStatus"));
                tHeaders.add(new HeaderExt("差异类型", "errTypeDesc"));
                tHeaders.add(new HeaderExt("处理状态", "handleStatusDesc"));
                tHeaders.add(new HeaderExt("处理人", "handler"));
                tHeaders.add(new HeaderExt("处理时间", "handleTime"));
                tHeaders.add(new HeaderExt("处理方式", "handleTypeDesc"));
                tHeaders.add(new HeaderExt("备注", "remark"));
                int i = 1;
                for (BankChkErrDTO bankChkResultDTO1 : response.getResult().getPageList()) {
                    Map map = Bean2MapUtil.convertBean(bankChkResultDTO1);
                    map.put("index", i);
                    datas.add(map);
                    i++;
                }
                Map m = response.getResult().getSumData();
                m.put("platformCode", "总笔数");
                m.put("innerPayTransNo", i - 1);
                m.put("transCodeDesc", "总计");
                datas.add(m);

                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                POIExcelApi.getInstance().write(outputStream, ca);
                File file = ExcelUtils.addTitle(tempFile, "银行对账差异报表", fileDownAjax,tHeaders.size(),oDateStart,oDateEnd);
                responseEntity = fileDownAjax.getResponseEntity(file);

            } else {
                log.error("下载失败");
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
