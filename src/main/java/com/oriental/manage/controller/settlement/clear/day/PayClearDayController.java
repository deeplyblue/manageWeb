package com.oriental.manage.controller.settlement.clear.day;

import com.oriental.clearDubbo.api.clear.day.ClearDayInterface;
import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.pay.PayClearDayModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDayModel;
import com.oriental.manage.core.excelUtils.ExcelUtils;
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
import java.util.*;

/**
 * Created by zhangxinhai on 2016/6/8.
 */
@Slf4j
@Controller
@RequestMapping("settlement/clear/day/payClearDay")
public class PayClearDayController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ClearDayInterface clearDayInterface;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private Constants constants;

    @RequestMapping("init")
    @RequiresPermissions("payClearDay_search")
    public String init() {
        return "settlement/clear/day/payClearDay";
    }

    @OperateLogger(content = "支付机构日结明细查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("payClearDay_search")
    @ResponseBody
    public ResponseDTO<Pagination<PayClearDayModel>> queryPage(Pagination<PayClearDayModel> pagination,
                                                                 OrgClearDayModel orgClearDayModel,
                                                                 String beginDate, String endDate) {
        ResponseDTO<Pagination<PayClearDayModel>> responseDTO = new ResponseDTO<Pagination<PayClearDayModel>>();
        try {
            orgClearDayModel.setSettleDateBegin(DateUtils.parse(beginDate));
            orgClearDayModel.setSettleDateEnd(DateUtils.parse(endDate));
            RequestModel<OrgClearDayModel> requestModel = new RequestModel<OrgClearDayModel>();
            requestModel.setRequest(orgClearDayModel);
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            log.info("机构日结明细查询参数："+requestModel);
            ResponseModel<PayClearDayModel> responseModel = clearDayInterface.selectPayClearDay(requestModel);
            log.info("机构日结明细查询结果："+responseModel);
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
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequiresPermissions("payClearDay_down")
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(OrgClearDayModel orgClearDayModel,
                                           String beginDate, String endDate){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;

        try {
            tempFile = fileDownAjax.touch("支付机构日结明细" + DateUtils.now() + ".xlsx");
            orgClearDayModel.setSettleDateBegin(DateUtils.parse(beginDate));
            orgClearDayModel.setSettleDateEnd(DateUtils.parse(endDate));
            RequestModel<OrgClearDayModel> requestModel = new RequestModel<OrgClearDayModel>();
            requestModel.setRequest(orgClearDayModel);
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);
            ResponseModel<PayClearDayModel> responseModel = clearDayInterface.selectPayClearDay(requestModel);
            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("支付机构代码", "payOrgCode", ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("清算日期", "settleDate"));
            tHeaders.add(new HeaderExt("交易类型", "transCode",ExcelContentExt.PAY_TRANS_CODE));
            tHeaders.add(new HeaderExt("渠道", "connChannel",ExcelContentExt.CONN_CHANNEL));
            tHeaders.add(new HeaderExt("交易笔数", "transCount"));
            tHeaders.add(new HeaderExt("交易金额(元)", "transAmt",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("手续费(元)", "feeAmt",ExcelContentExt.CURRENCY));
            int i=1;
           if(responseModel.getResponseResult()){
               for(PayClearDayModel payClearDayModel : responseModel.getList()){
                   Map map = Bean2MapUtil.convertBean(payClearDayModel);
                   map.put("index",i);
                   datas.add(map);
                   i++;
               }
               OrgSumPageModel orgSumPageModel = responseModel.getOrgSumPageModel();
                Map m=new HashMap();

               m.put("connChannel","总计");
               m.put("transCount",orgSumPageModel.getTransCountBP());
               m.put("transAmt",orgSumPageModel.getTransAmtP());
               m.put("feeAmt",orgSumPageModel.getFeeAmtP());
               datas.add(m);
               ExcelWriterConfig config = new ExcelWriterConfig();
               config.setHeaders(tHeaders);
               config.setDatas(datas);
               ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
               FileOutputStream outputStream = new FileOutputStream(tempFile);
               POIExcelApi.getInstance().write(outputStream, ca);
               File file = ExcelUtils.addTitle(tempFile, "支付机构日结明细表", fileDownAjax, tHeaders.size(), beginDate, endDate);
               responseEntity = fileDownAjax.getResponseEntity(file);
           }else{
               log.error("下载失败");
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
