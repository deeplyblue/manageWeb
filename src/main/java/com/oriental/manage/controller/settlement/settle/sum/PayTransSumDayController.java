package com.oriental.manage.controller.settlement.settle.sum;

import com.oriental.clearDubbo.api.settle.sum.TransSumDetailInterface;
import com.oriental.clearDubbo.model.base.OrgSumPageModel;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.settle.sum.OrgTransSumDayModel;
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
@RequestMapping("settlement/settle/sum/payTransSumDay")
public class PayTransSumDayController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TransSumDetailInterface transSumDetailInterface;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private com.oriental.manage.core.system.Constants Constants;

    @RequestMapping("init")
    @RequiresPermissions("payTransSumDay_search")
    public String init() {

        return "settlement/settle/sum/payTransSumDay";
    }

    @OperateLogger(content = "支付交易汇总查询", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("payTransSumDay_search")
    @ResponseBody
    public ResponseDTO<Pagination<OrgTransSumDayModel>> queryPage(Pagination<OrgTransSumDayModel> pagination,
                                                                  OrgTransSumDayModel orgTransSumDayModel,
                                                                  String beginDate, String endDate) {
        ResponseDTO<Pagination<OrgTransSumDayModel>> responseDTO = new ResponseDTO<Pagination<OrgTransSumDayModel>>();
        try {
            orgTransSumDayModel.setSumDateBegin(DateUtils.parse(beginDate));
            orgTransSumDayModel.setSumDateEnd(DateUtils.parse(endDate));
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(orgTransSumDayModel);
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            log.info("机构交易汇总查询参数："+requestModel);
            ResponseModel<OrgTransSumDayModel> responseModel = transSumDetailInterface.getPayTransSumDayModel(requestModel);
            log.info("机构交易汇总查询结果："+responseModel);
            OrgSumPageModel orgSumPageModel = responseModel.getOrgSumPageModel();
            if (responseModel.getResponseResult()) {
                pagination.setRowCount(orgSumPageModel.getPageSum());
                pagination.setList(responseModel.getList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
                responseDTO.setSumObject(orgSumPageModel);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getResponseMessage());
            }
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("download")
    @RequiresPermissions("payTransSumDay_down")
    public ResponseEntity<byte[]> download(OrgTransSumDayModel orgTransSumDayModel,
                                           String beginDate, String endDate) {
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            InputStream inputStream = null;
            if (orgTransSumDayModel.getRadioValue().equals("01")) {
                inputStream = new FileInputStream(Constants.getExcelPath() + "/支付机构交易汇总查询模板.xlsx");
            } else if (orgTransSumDayModel.getRadioValue().equals("02")) {
                inputStream = new FileInputStream(Constants.getExcelPath() + "/支付机构子银行交易汇总查询模板.xlsx");
            }
            tempFile = fileDownAjax.touch("支付交易汇总查询-" + DateUtils.now() + ".xlsx");
            String time = StringCommonUtils.insertStr(beginDate,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(endDate,"-",4,7);
            orgTransSumDayModel.setSumDateBegin(DateUtils.parse(beginDate));
            orgTransSumDayModel.setSumDateEnd(DateUtils.parse(endDate));
            RequestModel requestModel = new RequestModel();
            requestModel.setRequest(orgTransSumDayModel);
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);

            ResponseModel<OrgTransSumDayModel> responseModel = transSumDetailInterface.getPayTransSumDayModel(requestModel);
            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("支付机构", "orgCode", ExcelContentExt.ORGANIZE));
            if (orgTransSumDayModel.getRadioValue().equals("02")) {
                tHeaders.add(new HeaderExt("银行名称", "bankCode", ExcelContentExt.BANK_INFO));
            }
            tHeaders.add(new HeaderExt("日期", "sumDate"));
            tHeaders.add(new HeaderExt("笔数", "transCountSum"));
            tHeaders.add(new HeaderExt("金额(元)", "transAmtSum", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("笔数", "transCountBP"));
            tHeaders.add(new HeaderExt("金额(元)", "transAmtP", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("成功率", "rateP"));
            tHeaders.add(new HeaderExt("笔数", "transCountBR"));
            tHeaders.add(new HeaderExt("金额(元)", "transAmtR", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("退款率", "rateR"));
            tHeaders.add(new HeaderExt("笔数", "transCountSumC"));
            tHeaders.add(new HeaderExt("金额(元)", "transAmtSumC", ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("退款率", "rateC"));


            int i = 1;
            for (int j = 0; j < responseModel.getList().size(); j++) {
                Map map = Bean2MapUtil.convertBean(responseModel.getList().get(j));
                map.put("balance", responseModel.getList().get(j).getTransAmtP().subtract(responseModel.getList().get(j).getTransAmtR()));
                map.put("index", i);
                datas.add(map);
                i++;
            }
            OrgSumPageModel orgSumPageModel = responseModel.getOrgSumPageModel();
            Map m = new HashMap<>();
            if (orgSumPageModel != null && orgSumPageModel.getTransAmtP() != null) {
                if (orgTransSumDayModel.getRadioValue().equals("01")) {
                    m.put("orgCode", "总计");
                } else if (orgTransSumDayModel.getRadioValue().equals("02")) {
                    m.put("bankCode", "总计");
                }
                m.put("transAmtSum", orgSumPageModel.getTransAmtSum());
                m.put("transCountSum", orgSumPageModel.getTransCountSum());
                m.put("transAmtP", orgSumPageModel.getTransAmtP());
                m.put("transCountBP", orgSumPageModel.getTransCountBP());
                m.put("rateP", orgSumPageModel.getRateP());
                m.put("transAmtR", orgSumPageModel.getTransAmtR());
                m.put("transCountBR", orgSumPageModel.getTransCountBR());
                m.put("rateR", orgSumPageModel.getRateR());
                m.put("transAmtSumC", orgSumPageModel.getTransAmtSumC());
                m.put("transCountSumC", orgSumPageModel.getTransCountSumC());
                m.put("rateC", orgSumPageModel.getRateC());
            }
            datas.add(m);
            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            config.setStartWith(4);
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            ExcelTemplateUtil.write(inputStream, outputStream, config,time);
            responseEntity = fileDownAjax.getResponseEntity(tempFile);

        } catch (Exception e) {

            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        } finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }

}
