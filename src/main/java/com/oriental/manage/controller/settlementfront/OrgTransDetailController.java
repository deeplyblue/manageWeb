package com.oriental.manage.controller.settlementfront;

import com.oriental.clearDubbo.api.clear.report.OrgTransDetailInterface;
import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.report.OrgTransDetailModel;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 *结算明细查询
 * Created by zhangxinhai on 2016/10/28.
 */
@Slf4j
@Controller
@RequestMapping("settlementfront/orgTransDetail")
public class OrgTransDetailController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OrgTransDetailInterface orgTransDetailInterface;
    @Autowired
    private FileDownAjax fileDownAjax;

    @RequestMapping("init")
    @RequiresPermissions("orgTransDetail_init")
    public String init() {
        return "settlementfront/searchOrgTransDetail";
    }

    @OperateLogger(content = "结算明细查询", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("orgTransDetail_init")
    @ResponseBody
    public ResponseDTO search(Pagination<OrgTransDetailModel> pagination,OrgTransDetailModel transDetailModel,String beginDate, String endDate) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            RequestModel<OrgTransDetailModel> requestModel = new RequestModel<>();
            transDetailModel.setSettleDateBegin(DateUtils.parse(beginDate));
            transDetailModel.setSettleDateEnd(DateUtils.parse(endDate));
            requestModel.setRequest(transDetailModel);
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            log.info("结算明细查询参数："+requestModel);
            ResponseModel<OrgTransDetailModel> responseModel = orgTransDetailInterface.selectOrgTransDetail(requestModel);
            log.info("结算明细查询结果："+responseModel);
            if (responseModel.getResponseResult()) {
                responseDTO.setSuccess(true);
                pagination.setRowCount(responseModel.getOrgSumPageModel().getPageSum());
                pagination.setPageSize(pagination.getPageSize());
                pagination.setList(responseModel.getList());
                responseDTO.setSumObject(responseModel.getOrgSumPageModel());
                responseDTO.setObject(pagination);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("系统繁忙");
            }
        }catch (Exception e){
            log.error("结算明细查询异常！", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("download")
    @RequiresPermissions("orgTransDetail_download")
    public ResponseEntity<byte[]> download(OrgTransDetailModel transDetailModel,String beginDate, String endDate) {
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            tempFile = fileDownAjax.touch("结算明细表" + DateUtils.now() + ".xlsx");
            RequestModel<OrgTransDetailModel> requestModel = new RequestModel<>();
            transDetailModel.setSettleDateBegin(DateUtils.parse(beginDate));
            transDetailModel.setSettleDateEnd(DateUtils.parse(endDate));
            requestModel.setRequest(transDetailModel);
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);
            ResponseModel<OrgTransDetailModel> responseModel = orgTransDetailInterface.selectOrgTransDetail(requestModel);

            LinkedList<Header> tHeaders = new LinkedList<>();

            List<Object> datas = new ArrayList<>();

            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("机构", "payOrgCode", ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("商户", "mchntCode", ExcelContentExt.MCHNT));
            tHeaders.add(new HeaderExt("交易金额", "transAmt",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("交易日期", "mchntSettleDate"));
            tHeaders.add(new HeaderExt("支付单号", "innerPayTransNo"));
            tHeaders.add(new HeaderExt("渠道流水号", "bankReqNo"));
            tHeaders.add(new HeaderExt("商户流水号", "busiReqNo"));
            tHeaders.add(new HeaderExt("商户付款状态", "mchntCashTransFlag",ExcelContentExt.CASH_TRANS_FLAG));
            tHeaders.add(new HeaderExt("渠道收款状态", "payCashTransFlag",ExcelContentExt.CASH_TRANS_FLAG));
            tHeaders.add(new HeaderExt("应收日期", "payClearDate"));
            tHeaders.add(new HeaderExt("实收日期", "payCashTransDate"));
            tHeaders.add(new HeaderExt("应付日期", "mchntClearDate"));
            tHeaders.add(new HeaderExt("实付日期", "mchntCashTransDate"));
            int i=1;
            if(responseModel.getResponseResult()){
                for(OrgTransDetailModel orgTransDetailModel : responseModel.getList()){
                    Map map = Bean2MapUtil.convertBean(orgTransDetailModel);
                    map.put("index",i);
                    datas.add(map);
                    i++;
                }
            }
            OrgSumPageModel orgSumPageModel = responseModel.getOrgSumPageModel();
            Map m=new HashMap();

            m.put("payOrgCode","总计");
            m.put("mchntCode","总金额");
            m.put("transAmt",orgSumPageModel.getAmtSum());
            m.put("mchntSettleDate","总笔数");
            m.put("innerPayTransNo",orgSumPageModel.getPageSum());
            datas.add(m);
            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            POIExcelApi.getInstance().write(outputStream, ca);

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
