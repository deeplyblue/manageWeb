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
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.transaction.BizTrans;
import com.oriental.manage.service.transaction.IBizTransService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("bizTrans")
public class BizTransController {

    @Autowired
    private IBizTransService bizTransService;
    @Autowired
    private FileDownAjax fileDownAjax;

    @RequestMapping("init")
    public String init() {
        return "transaction/queryBizTransList";
    }

    @OperateLogger(content = "业务订单信息查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryBizTransList")
    @RequiresPermissions("bizTrans_search")
    @ResponseBody
    public ResponseDTO<Pagination<BizTrans>> queryPage(Pagination<BizTrans> pagination, BizTrans bizTrans) {
        ResponseDTO<Pagination<BizTrans>> responseDTO = new ResponseDTO<Pagination<BizTrans>>();
        try {
            bizTransService.queryPage(pagination, bizTrans);
            Map map = bizTransService.summaryBizTrans(bizTrans);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
            responseDTO.setSumObject(map);
        } catch (Exception e) {
            log.error("查询业务信息失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @RequestMapping("toQueryDetail")
    public String toQueryDetail() {
        return "transaction/queryBizTransDetail";
    }

    @OperateLogger(content = "业务订单明细查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryBizTransDetail")
    @RequiresPermissions("bizTrans_search")
    @ResponseBody
    public ResponseDTO<BizTrans> queryBizTransDetail(BizTrans bizTrans) {
        ResponseDTO<BizTrans> responseDTO = new ResponseDTO<BizTrans>();
        try {
            BizTrans resultBizTrans = bizTransService.queryBizTransDetail(bizTrans);
            responseDTO.setSuccess(true);
            responseDTO.setObject(resultBizTrans);
        } catch (Exception e) {
            log.error("查询业务信息失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @RequiresPermissions("bizTrans_download")
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestBody BizTrans bizTrans) {


        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            tempFile = fileDownAjax.touch("业务订单信息" + DateUtils.now() + ".xlsx");
            //查询需要下载导出的结果
            bizTrans.setPageNum(1);
            bizTrans.setPageSize(10000);
            List<BizTrans> responseModel = bizTransService.queryPageDown(bizTrans);
            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            //添加表头
            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("商户代码", "merchantCode", ExcelContentExt.MCHNT));
            tHeaders.add(new HeaderExt("订单号", "orderNo"));
            tHeaders.add(new HeaderExt("订单日期", "orderDate"));
            tHeaders.add(new HeaderExt("接入渠道", "channel",ExcelContentExt.CONN_CHANNEL));
            tHeaders.add(new HeaderExt("平台流水号", "ourTransNo"));
            tHeaders.add(new HeaderExt("业务类型", "transCode"));
            tHeaders.add(new HeaderExt("业务请求流水号", "busiReqNo"));
            tHeaders.add(new HeaderExt("业务请求日期", "busiReqDate"));
            tHeaders.add(new HeaderExt("金额(元)", "amount",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("交易状态", "transStatus",ExcelContentExt.ORDER_STATUS));
            tHeaders.add(new HeaderExt("交易标识", "flag"));
            tHeaders.add(new HeaderExt("清算日期", "settleDate"));
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

                //金额格式转换
                BigDecimal bd=new BigDecimal(responseModel.get(i).getTransAmt());

                if(responseModel.get(i).getDeleteFlag()!=null && responseModel.get(i).getDeleteFlag().equals("0")){
                    map.put("delete","未删除");
                }
                if(responseModel.get(i).getDeleteFlag()!=null && responseModel.get(i).getDeleteFlag().equals("1")){
                    map.put("delete","已删除");
                }
                map.put("amount",bd);
                map.put("index", i+1);
                datas.add(map);
            }
            Map m = bizTransService.summaryBizTrans(bizTrans);
            m.put("merchantCode","总笔数:"+m.get("sumCount")+"笔,总金额："+m.get("sumTotalAmt")+"元");
            datas.add(m);

            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
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