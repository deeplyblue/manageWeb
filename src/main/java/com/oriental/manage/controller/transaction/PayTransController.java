package com.oriental.manage.controller.transaction;

import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.pojo.transaction.PayTrans;
import com.oriental.manage.service.transaction.IPayTransService;
import lombok.extern.slf4j.Slf4j;
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
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Map;

/**
 * User: hj
 * Date: 2016/6/13 20:15
 * Desc:
 */
@Slf4j
@Controller
@RequestMapping("payTrans")
public class PayTransController {

    @Autowired
    private IPayTransService payTransService;

    @Autowired
    private FileDownAjax fileDownAjax;

    @RequestMapping("init")
    public String init() {
        return "transaction/queryPayTransList";
    }

    @OperateLogger(content = "支付订单信息查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryPayTransList")
    @RequiresPermissions("payTrans_search")
    @ResponseBody
    public ResponseDTO<Pagination<PayTrans>> queryPage(Pagination<PayTrans> pagination, PayTrans payTrans) {
        ResponseDTO<Pagination<PayTrans>> responseDTO = new ResponseDTO<Pagination<PayTrans>>();
        try {
            payTransService.queryPage(pagination, payTrans);
            Map map = payTransService.summaryPayTrans(payTrans);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
            responseDTO.setSumObject(map);
        } catch (Exception e) {
            log.error("查询支付信息失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @RequestMapping("toQueryDetail")
    public String toQueryDetail() {
        return "transaction/queryPayTransDetail";
    }

    @OperateLogger(content = "支付订单明细查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryPayTransDetail")
    @ResponseBody
    public ResponseDTO<PayTrans> queryPayTransDetail(PayTrans payTrans) {
        ResponseDTO<PayTrans> responseDTO = new ResponseDTO<PayTrans>();
        try {
            PayTrans resultPayTrans = payTransService.queryPayTransDetail(payTrans);
            responseDTO.setSuccess(true);
            responseDTO.setObject(resultPayTrans);
        } catch (Exception e) {
            log.error("查询支付信息失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @RequiresPermissions("payTrans_download")
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestBody PayTrans payTrans) {

        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            tempFile = fileDownAjax.touch("支付订单信息" + DateUtils.now() + ".xlsx");
            //查询需要下载导出的结果
            payTrans.setPageNum(1);
            payTrans.setPageSize(10000);
            List<PayTrans> responseModel = payTransService.queryPageDown(payTrans);
                //表头
                LinkedList<Header> tHeaders = new LinkedList<>();
                //内容
                List<Object> datas = new ArrayList<>();
                //添加表头
                tHeaders.add(new HeaderExt("序号", "index"));
                tHeaders.add(new HeaderExt("支付机构", "payOrgCode", ExcelContentExt.ORGANIZE));
                tHeaders.add(new HeaderExt("订单号", "orderNo"));
                tHeaders.add(new HeaderExt("接入渠道", "channel",ExcelContentExt.CONN_CHANNEL));
                tHeaders.add(new HeaderExt("平台流水号", "ourTransNo"));
                tHeaders.add(new HeaderExt("支付类型", "transCode",ExcelContentExt.PAY_TRANS_CODE));
                tHeaders.add(new HeaderExt("支付请求流水号", "payReqNo"));
                tHeaders.add(new HeaderExt("支付请求日期", "payReqDate"));
                tHeaders.add(new HeaderExt("支付金额(元)", "amount",ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("交易状态", "transStatus",ExcelContentExt.ORDER_STATUS));
                tHeaders.add(new HeaderExt("交易标识", "flag"));
                tHeaders.add(new HeaderExt("清算日期", "settleDate"));
                tHeaders.add(new HeaderExt("银行清算日期", "paySettleDate"));
                tHeaders.add(new HeaderExt("删除标识", "delete"));
                //循环添加表数据
                for(int i = 0;i<responseModel.size();i++){
                    Map map = Bean2MapUtil.convertBean(responseModel.get(i));
                    //数据转换
                    if(responseModel.get(i).getReverseFlag()!=null && responseModel.get(i).getReverseFlag().equals("1")){
                        map.put("flag","正交易");
                    }
                    if(responseModel.get(i).getReverseFlag()!=null && responseModel.get(i).getReverseFlag().equals("0")){
                        map.put("flag","反交易");
                    }
                    if(responseModel.get(i).getDeleteFlag()!=null && responseModel.get(i).getDeleteFlag().equals("0")){
                        map.put("delete","未删除");
                    }
                    if(responseModel.get(i).getDeleteFlag()!=null && responseModel.get(i).getDeleteFlag().equals("1")){
                        map.put("delete","已删除");
                    }
                    //金额格式转换
                    BigDecimal bd=new BigDecimal(responseModel.get(i).getTransAmt());
                    map.put("amount",bd);
                    map.put("index", i+1);
                    datas.add(map);
                }
                Map m = payTransService.summaryPayTrans(payTrans);
                m.put("payOrgCode","总笔数:"+m.get("sumCount")+"笔,总金额："+m.get("sumTotalAmt")+"元");
                datas.add(m);
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                //合并单元格





                ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                POIExcelApi.getInstance().write(outputStream, ca);

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