package com.oriental.manage.core.bigDataDownloadThread;

import com.oriental.manage.core.bigDataDownloadThread.base.BaseDownloadThread;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.utils.ApplicationContextUtil;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.pojo.transaction.OrderMain;
import com.oriental.manage.service.transaction.IOrderMainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
import org.webbuilder.office.excel.config.CustomCellStyle;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjun on 2016/8/8.
 */
@Slf4j
public class DownloadOrderMainThread extends BaseDownloadThread<OrderMain> {

    private IOrderMainService orderMainService = ApplicationContextUtil.getBean(IOrderMainService.class);

    public DownloadOrderMainThread(OrderMain orderMain) {
        this.queryModel = orderMain;
    }

    @Override
    protected void config(LinkedList<Header> tHeaders) {
        //设置输出文件
        setFileNameOut("ORDER_MAIN-" + DateUtils.now() + "-.zip");

        //添加表头
        tHeaders.add(new HeaderExt("序号", "index"));
        tHeaders.add(new HeaderExt("商户", "merchantCode", ExcelContentExt.MCHNT));
        tHeaders.add(new HeaderExt("订单号", "orderNo"));
        tHeaders.add(new HeaderExt("订单日期", "orderDate"));
        tHeaders.add(new HeaderExt("平台流水号", "ourTransNo"));
        tHeaders.add(new HeaderExt("接入渠道", "channel", ExcelContentExt.CONN_CHANNEL));
        tHeaders.add(new HeaderExt("订单金额(元)", "amount", ExcelContentExt.CURRENCY));
        tHeaders.add(new HeaderExt("订单状态", "orderStatus", ExcelContentExt.ORDER_STATUS));
        tHeaders.add(new HeaderExt("通知状态", "notify"));
        tHeaders.add(new HeaderExt("交易标识", "reverseFlag"));
        tHeaders.add(new HeaderExt("清算日期", "settleDate"));
    }

    @Override
    protected void queryRowCount() {
        queryModel.setPageSize(1);
        //如果有单独的查询总行数方法，请自行修改
        List<OrderMain> response = orderMainService.queryPageDownload(queryModel);
        if (response != null && response.size() != 0) {
            this.rowCount = response.get(0).getRowCount();
        }
    }

    @Override
    protected File buildTempFile(int pageNum) throws Exception {
        List<Object> resultDatas = new ArrayList<>();
        File tempFile = null;
        queryModel.setPageNum(pageNum);
        List<OrderMain> responseModel = orderMainService.queryPageDownload(queryModel);

        for (int i = 0; i < responseModel.size(); i++) {


            if (Constants.getDataDictMap().get("REVERSE_FLAG").get(responseModel.get(i).getReverseFlag()) != null) {

                responseModel.get(i).setReverseFlag(Constants.getDataDictMap().get("REVERSE_FLAG")
                        .get(responseModel.get(i).getReverseFlag()));
            }

            StringBuilder sb = new StringBuilder();
            sb.append("商户订单查询：第")
                    .append(pageNum)
                    .append("张表")
                    .append(DateUtils.now())
                    .append(".xlsx");

            tempFile = fileDownAjax.touch(sb.toString());
            Map map = Bean2MapUtil.convertBean(responseModel.get(i));
            if (responseModel.get(i).getNotifyStatus() != null && responseModel.get(i).getNotifyStatus().equals("A")) {
                map.put("notify", "未通知");
            }
            if (responseModel.get(i).getNotifyStatus() != null && responseModel.get(i).getNotifyStatus().equals("B")) {
                map.put("notify", "已通知");
            }
            if (responseModel.get(i).getNotifyStatus() != null && responseModel.get(i).getNotifyStatus().equals("C")) {
                map.put("notify", "通知失败");
            }
            BigDecimal bd = new BigDecimal(responseModel.get(i).getTotalAmt());
            map.put("amount", bd);
            map.put("index", i + 1);
            resultDatas.add(map);
        }

        ExcelWriterConfig config = new ExcelWriterConfig() {
            @Override
            public CustomCellStyle getCellStyle(int row, int column, String header, Object value) {
                CustomCellStyle style = super.getCellStyle(row, column, header, value);
                if (row > 0 && StringUtils.equals("orderDate", header)) {
                    style.setFormat("yyyy-m-d h:mm:ss");
                }
                return style;
            }
        };
        Map m = orderMainService.summaryOrderMain(queryModel);
        m.put("merchantCode", "总笔数:" + m.get("sumCount") + "笔,总金额：" + m.get("sumTotalAmt") + "元");
        resultDatas.add(m);
        config.setHeaders(tHeaders);
        config.setDatas(resultDatas);
        config.setSheetName("商户订单查询：第" + pageNum + "张表");
        ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
        FileOutputStream outputStream = new FileOutputStream(tempFile);
        POIExcelApi.getInstance().write(outputStream, ca);
        return tempFile;
    }

}
