package com.oriental.manage.controller.report;

import com.oriental.check.commons.util.DateUtil;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.StringCommonUtils;
import com.oriental.manage.pojo.report.GLPaymentReportData;
import com.oriental.manage.pojo.report.GLPaymentReportPaidData;
import com.oriental.manage.service.report.IGLPaymentReportService;
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
 * <ul>
 * <li>总账付款核对报表控制器</li>
 * <li>User:蒯越 Date:2016/12/1 Time:14:10</li>
 * </ul>
 */
@Slf4j
@Controller
public class GLPaymentReportController {

    @Autowired
    private IGLPaymentReportService glPaymentReportService;

    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private com.oriental.manage.core.system.Constants Constants;

    @RequestMapping("/report/gLPaymentReport/init")
    public String init() {
        return "report/searchGLPaymentReport";
    }

    @OperateLogger(content = "查询总账收款核对报表", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/report/gLPaymentReport/search")
    @RequiresPermissions("gLPaymentReport_search")
    @ResponseBody
    public ResponseDTO<Pagination<GLPaymentReportData>> search(Pagination<GLPaymentReportData> pagination,
                                                                  GLPaymentReportData query,
                                                                  String sDateStart,
                                                                  String sDateEnd) {
        ResponseDTO<Pagination<GLPaymentReportData>> responseDTO = new ResponseDTO<>();
        log.info("查询信息:{},{}", query, pagination);
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sDateStart));
            query.setDateEnd(DateUtil.parse(sDateEnd));
            glPaymentReportService.pageQuery(pagination, query);
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

    @RequestMapping("/report/downloadGLPaymentReport")
    @ResponseBody
    public ResponseEntity<byte[]> download (GLPaymentReportData query,
                                            String oDateStart,
                                            String oDateEnd){
        ResponseEntity<byte[]> responseEntity=null;
        File tempFile = null;
        try{

            InputStream inputStream = new FileInputStream(Constants.getExcelPath() + "/gl_pay_template.xlsx");
            tempFile = fileDownAjax.touch("总账付款核对报表-" + DateUtils.now() + ".xlsx");
            String titleTime= StringCommonUtils.insertStr(oDateStart,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(oDateEnd,"-",4,7);
            Pagination<GLPaymentReportData> pagination = new Pagination<GLPaymentReportData>();
            query.setDateStart(DateUtil.parse(oDateStart));
            query.setDateEnd(DateUtil.parse(oDateEnd));
            query.setStartRow(0);
            query.setPageSize(10000);
            glPaymentReportService.pageQuery(pagination, query);
            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("商户", "mchntCode",ExcelContentExt.MCHNT));
            tHeaders.add(new HeaderExt("清算日期", "busiSettleDate"));
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
            tHeaders.add(new HeaderExt("笔数", "transCountDC"));
            tHeaders.add(new HeaderExt("金额", "transAmtDC", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "transCountDD"));
            tHeaders.add(new HeaderExt("金额", "transAmtDD", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "transCountCC"));
            tHeaders.add(new HeaderExt("金额", "transAmtCC", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("应收净额", "settleAmt", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("支付机构","payOrgCode",ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("出账日期", "paidOutDate"));
            tHeaders.add(new HeaderExt("出账金额", "paidOutAmt",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("入账日期", "paidInDate"));
            tHeaders.add(new HeaderExt("入账金额", "paidInAmt",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("实付净额", "settleAmtP",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("交易所在日期", "transDates"));
            int i = 1;
            int flag =0;
            for(int j=0;j<pagination.getList().size();j++){
                Map map = Bean2MapUtil.convertBean(pagination.getList().get(j));
                map.put("index", i);
                map.put("busiSettleDate", DateUtils.format(pagination.getList().get(j).getBusiSettleDate(),DateUtils.DATESHOWFORMAT));
                map.put("transCountDD",pagination.getList().get(j).getTransCountD());
                map.put("transAmtDD",pagination.getList().get(j).getTransAmtD());
                map.put("transCountCC",pagination.getList().get(j).getTransCountC());
                map.put("transAmtCC",pagination.getList().get(j).getTransAmtC());
                map.put("transCountDC",pagination.getList().get(j).getTransCountD()+pagination.getList().get(j).getTransCountC());
                map.put("transAmtDC",pagination.getList().get(j).getTransAmtD()+pagination.getList().get(j).getTransAmtC());
                map.put("settleAmt",pagination.getList().get(j).getTransAmtD()-pagination.getList().get(j).getTransAmtC());
                if(pagination.getList().get(j).getGlPaymentReportPaidDataList() !=null &&
                        pagination.getList().get(j).getGlPaymentReportPaidDataList().size()>0){
                    for(GLPaymentReportPaidData glPaymentReportPaidData:pagination.getList().get(j).getGlPaymentReportPaidDataList()){
                        Map map1 = Bean2MapUtil.convertBean(pagination.getList().get(j));
                        map1.put("busiSettleDate", DateUtils.format(pagination.getList().get(j).getBusiSettleDate(),DateUtils.DATESHOWFORMAT));
                        map1.put("transCountDD",pagination.getList().get(j).getTransCountD());
                        map1.put("transAmtDD",pagination.getList().get(j).getTransAmtD());
                        map1.put("transCountCC",pagination.getList().get(j).getTransCountC());
                        map1.put("transAmtCC",pagination.getList().get(j).getTransAmtC());
                        map1.put("transCountDC",pagination.getList().get(j).getTransCountD()+pagination.getList().get(j).getTransCountC());
                        map1.put("transAmtDC",pagination.getList().get(j).getTransAmtD()+pagination.getList().get(j).getTransAmtC());
                        map1.put("settleAmt",pagination.getList().get(j).getTransAmtD()-pagination.getList().get(j).getTransAmtC());
                            map1.put("index", i);
                            map1.put("payOrgCode",glPaymentReportPaidData.getPayOrgCode());
                            map1.put("paidOutDate",glPaymentReportPaidData.getPaidOutDate());
                            map1.put("paidOutAmt",glPaymentReportPaidData.getPaidOutAmt());
                            map1.put("paidInDate",glPaymentReportPaidData.getPaidInDate());
                            map1.put("paidInAmt",glPaymentReportPaidData.getPaidInAmt());
                            if(glPaymentReportPaidData.getPaidOutAmt()!=null && glPaymentReportPaidData.getPaidInAmt()!=null){

                                map1.put("settleAmtP",glPaymentReportPaidData.getPaidOutAmt()-glPaymentReportPaidData.getPaidInAmt());
                            }else{
                                map1.put("settleAmtP",null);
                        }
                        map1.put("transDates",glPaymentReportPaidData.getTransDates());
                        datas.add(map1);
                        flag=1;
                    }

                }else{
                    flag =0;
                }

                i++;
                if(flag == 0){
                    datas.add(map);
                    flag=0;
                }
            }
            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            config.mergeColumn("index","mchntCode","busiSettleDate","payCount","payAmt","refundCount","refundAmt",
                    "succCount","succAmt","transCountD","transAmtD","transCountC","transAmtC","transCountDC",
                    "transAmtDC","transCountDD","transAmtDD","transCountCC","transAmtCC","settleAmt"
                    );
            config.setStartWith(4);
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            ExcelTemplateUtil.write(inputStream,outputStream,config,titleTime);
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
