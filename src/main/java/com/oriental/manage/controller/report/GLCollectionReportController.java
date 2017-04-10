package com.oriental.manage.controller.report;

import com.oriental.check.commons.util.DateUtil;
import com.oriental.check.service.facade.err.BankChkErrFacade;
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
import com.oriental.manage.pojo.report.GLCollectionReportData;
import com.oriental.manage.service.report.IGLCollectionReportService;
import com.oriental.paycenter.commons.mode.Response;
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
 * <li>总账收款核对报表控制器</li>
 * <li>User:蒯越 Date:2016/12/1 Time:14:10</li>
 * </ul>
 */
@Slf4j
@Controller
public class GLCollectionReportController {

    @Autowired
    private IGLCollectionReportService glCollectionReportService;

    @Autowired
    private BankChkErrFacade bankChkErrFacade;

    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private Constants Constants;

    @RequestMapping("/report/gLCollectionReport/init")
    public String init() {
        return "report/searchGLCollectionReport";
    }

    @OperateLogger(content = "查询总账收款核对报表", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/report/gLCollectionReport/search")
    @RequiresPermissions("gLCollectionReport_search")
    @ResponseBody
    public ResponseDTO<Pagination<GLCollectionReportData>> search(Pagination<GLCollectionReportData> pagination,
                                                                  GLCollectionReportData query,
                                                                  String sDateStart,
                                                                  String sDateEnd) {
        ResponseDTO<Pagination<GLCollectionReportData>> responseDTO = new ResponseDTO<>();
        log.info("查询信息:{},{}", query, pagination);
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sDateStart));
            query.setDateEnd(DateUtil.parse(sDateEnd));
            glCollectionReportService.pageQuery(pagination, query);
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

    @RequestMapping("/report/gLCollectionReport/toSearchErrDetail")
    public String toSearchErrDetail() {
        return "report/searchGLCollectionReportErrDetail";
    }

    @OperateLogger(content = "查询总账收款核对报表-差异明细", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/report/gLCollectionReport/searchErrDetail")
    @ResponseBody
    public ResponseDTO<Pagination<BankChkErrDTO>> searchErrDetail(Pagination<BankChkErrDTO> pagination,
                                                                  GLCollectionReportData query,
                                                                  String payOrgCode,
                                                                  String sSettleDate) {
        ResponseDTO<Pagination<BankChkErrDTO>> responseDTO = new ResponseDTO<>();
        log.info("查询信息:{},{}", query, pagination);
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            BankChkErrDTO bankChkErrDTO = new BankChkErrDTO();
            bankChkErrDTO.setPayOrgCode(payOrgCode);
            bankChkErrDTO.setDateStart(DateUtil.parse(sSettleDate));
            bankChkErrDTO.setDateEnd(DateUtil.parse(sSettleDate));
            bankChkErrDTO.setPageSize(10);
            Response<com.oriental.check.service.facade.model.Pagination<BankChkErrDTO>> response
                    = bankChkErrFacade.pageQuery(bankChkErrDTO, SessionUtils.getUserName());
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
    @RequestMapping("/report/downloadGLCollectionReport")
    public ResponseEntity<byte[]> download(GLCollectionReportData query,
                                           String oDateStart,
                                           String oDateEnd){
        ResponseEntity<byte[]> responseEntity=null;
        File tempFile = null;
        try{
            InputStream inputStream = new FileInputStream(Constants.getExcelPath() + "/gl_rev_template.xlsx");
            tempFile = fileDownAjax.touch("总账收款核对报表-" + DateUtils.now() + ".xlsx");
            String titleTime= StringCommonUtils.insertStr(oDateStart,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(oDateEnd,"-",4,7);
            Pagination<GLCollectionReportData> pagination = new Pagination<GLCollectionReportData>();
            query.setDateStart(DateUtil.parse(oDateStart));
            query.setDateEnd(DateUtil.parse(oDateEnd));
            query.setStartRow(0);
            query.setPageSize(10000);
            glCollectionReportService.pageQuery(pagination, query);
            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("支付机构", "payOrgCode",ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("清算日期", "bankSettleDate"));
            tHeaders.add(new HeaderExt("笔数", "payCount"));
            tHeaders.add(new HeaderExt("金额", "payAmt", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "refundCount"));
            tHeaders.add(new HeaderExt("金额", "refundAmt", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "succCount"));
            tHeaders.add(new HeaderExt("金额", "succAmt", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "transCountD"));
            tHeaders.add(new HeaderExt("金额", "transAmtD", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "transCountC"));
            tHeaders.add(new HeaderExt("金额", "transAmtC", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "fileCount"));
            tHeaders.add(new HeaderExt("金额", "fileAmt", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "transCountD"));
            tHeaders.add(new HeaderExt("金额", "transAmtD", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "transCountC"));
            tHeaders.add(new HeaderExt("金额", "transAmtC", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应收净额", "settleAmt", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("入账日期", "paidDate"));
            tHeaders.add(new HeaderExt("入账金额", "paidInAmt",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("出账日期", "paidOutDate"));
            tHeaders.add(new HeaderExt("出账金额", "paidOutAmt",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("实收净额", "settleAmtP",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("交易所在日期", "transDates"));
            int i = 1;
            for(int j=0;j<pagination.getList().size();j++){
                Map map = Bean2MapUtil.convertBean(pagination.getList().get(j));
                map.put("index", i);
                map.put("settleAmt",pagination.getList().get(j).getTransAmtD()-pagination.getList().get(j).getTransAmtC());
                if(pagination.getList().get(j).getPaidInAmt()==null){
                    map.put("paidInAmt",0);
                }
                if(pagination.getList().get(j).getPaidOutAmt()==null){
                    map.put("paidOutAmt",0);
                }
                if(pagination.getList().get(j).getPaidInAmt()!=null && pagination.getList().get(j).getPaidOutAmt() !=null){

                    map.put("settleAmtP",pagination.getList().get(j).getPaidInAmt()-pagination.getList().get(j).getPaidOutAmt());
                }else if(pagination.getList().get(j).getPaidInAmt()==null && pagination.getList().get(j).getPaidOutAmt() !=null){
                    map.put("settleAmtP",-pagination.getList().get(j).getPaidOutAmt());
                }else if(pagination.getList().get(j).getPaidInAmt()!=null && pagination.getList().get(j).getPaidOutAmt() ==null){
                    map.put("settleAmtP",pagination.getList().get(j).getPaidInAmt());
                }
                map.put("settleAmtP",0);
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
