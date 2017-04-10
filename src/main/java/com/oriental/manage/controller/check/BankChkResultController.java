package com.oriental.manage.controller.check;

import com.oriental.check.commons.util.DateUtil;
import com.oriental.check.service.facade.manager.BankChkResultFacade;
import com.oriental.check.service.facade.manager.model.BankChkResultDTO;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.core.utils.StringCommonUtils;
import com.oriental.paycenter.commons.mode.Response;
import com.oriental.paycenter.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
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
 * <ul>
 * <li>银行对账结果控制器</li>
 * <li>User:蒯越 Date:2016/5/11 Time:13:48</li>
 * </ul>
 */
@Slf4j
@Controller
public class BankChkResultController {

    @Autowired
    private BankChkResultFacade bankChkResultFacade;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private Constants Constants;

    @RequestMapping("/check/bankChkResult/init")
    @RequiresPermissions("bankChkResult_search")
    public String init() {
        return "check/searchBankChkResult";
    }

    @OperateLogger(content = "银行对账结果查询",operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequestMapping("/check/bankChkResult/search")
    @RequiresPermissions("bankChkResult_search")
    public ResponseDTO<Pagination<BankChkResultDTO>> queryPage(Pagination<BankChkResultDTO> pagination,
                                                               BankChkResultDTO query,
                                                               String sDateStart,
                                                               String sDateEnd) {
        ResponseDTO<Pagination<BankChkResultDTO>> responseDTO = new ResponseDTO<Pagination<BankChkResultDTO>>();
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sDateStart));
            query.setDateEnd(DateUtil.parse(sDateEnd));
            Response<com.oriental.check.service.facade.model.Pagination<BankChkResultDTO>> response
                    = bankChkResultFacade.pageQuery(query, SessionUtils.getUserName());
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
    @RequestMapping("/check/bankChkResult/download")
    @RequiresPermissions("bankChkResult_download")
    public ResponseEntity<byte[]> download(BankChkResultDTO bankChkResultDTO,
                                            String payOrgCode,
                                           String oDateStart,
                                           String oDateEnd){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try{
            InputStream inputStream = new FileInputStream(Constants.getExcelPath() + "/银行对账结果模板.xlsx");
            tempFile = fileDownAjax.touch("银行对账结果-" + DateUtils.now() + ".xlsx");
            String titleTime= StringCommonUtils.insertStr(oDateStart,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(oDateEnd,"-",4,7);
            bankChkResultDTO.setStartRow(0);
            bankChkResultDTO.setPageSize(10000);
            bankChkResultDTO.setPayOrgCode(payOrgCode);
            bankChkResultDTO.setDateStart(DateUtil.parse(oDateStart.trim()));
            bankChkResultDTO.setDateEnd(DateUtil.parse(oDateEnd.trim()));
            Response<com.oriental.check.service.facade.model.Pagination<BankChkResultDTO>> response
                    = bankChkResultFacade.pageQuery(bankChkResultDTO, SessionUtils.getUserName());

            if(response.isSuccess()){
            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("支付机构", "payOrgCode", ExcelContentExt.ORGANIZE));
                tHeaders.add(new HeaderExt("清算日期", "settleDate"));
                tHeaders.add(new HeaderExt("笔数", "extTransCount"));
                tHeaders.add(new HeaderExt("金额(元)", "extTransAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "transCount"));
                tHeaders.add(new HeaderExt("金额(元)", "transAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "chkSuccCount"));
                tHeaders.add(new HeaderExt("金额(元)", "chkSuccAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "chkErrCount"));
                tHeaders.add(new HeaderExt("金额(元)", "chkErrAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "transCountD"));
                tHeaders.add(new HeaderExt("金额(元)", "transAmtD", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "transCountC"));
                tHeaders.add(new HeaderExt("金额(元)", "transAmtC", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("净额（元）","settleAmt",ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("对账状态", "statusDesc"));
            int i = 1;
            for(BankChkResultDTO bankChkResultDTO1 : response.getResult().getPageList()){
                Map map = Bean2MapUtil.convertBean(bankChkResultDTO1);
                map.put("index", i);
                if(bankChkResultDTO1.getTransAmtD()!=null&&bankChkResultDTO1.getTransAmtC()!=null){
                    map.put("settleAmt",bankChkResultDTO1.getTransAmtD()-bankChkResultDTO1.getTransAmtC());
                }
                datas.add(map);
                i++;
            }
            Map m = response.getResult().getSumData();
                m.put("settleDate","合计：");
            datas.add(m);
            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            config.setStartWith(4);
            FileOutputStream outputStream = new FileOutputStream(tempFile);

//                ExcelTemplateUtil.write(inputStream, outputStream, config);
                ExcelTemplateUtil.write(inputStream, outputStream, config,titleTime);
            responseEntity = fileDownAjax.getResponseEntity(tempFile);
            }else{
                responseEntity = fileDownAjax.getResponseEntityFail();
            }

        }catch(Exception e){

            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }
}
