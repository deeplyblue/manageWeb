package com.oriental.manage.service.report.impl;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.report.PayBusiCheckReportData;
import com.oriental.manage.service.report.IPayBusiCheckReportService;
import com.oriental.paycenter.commons.mode.Response;
import com.oriental.settlementfront.service.facade.manager.BankTransDetailFacade;
import com.oriental.settlementfront.service.facade.manager.model.ReportDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>支付业务核对报表服务实现</li>
 * <li>User:蒯越 Date:2016/12/5 Time:15:42</li>
 * </ul>
 */
@Slf4j
@Service
public class PayBusiCheckReportServiceImpl implements IPayBusiCheckReportService {

    @Autowired
    private BankTransDetailFacade bankTransDetailFacade;

    @Override
    public void pageQuery(Pagination<PayBusiCheckReportData> pagination, PayBusiCheckReportData query) {
        List<PayBusiCheckReportData> list = new ArrayList<>();
        String userName = SessionUtils.getUserName();
        // 查询数据
        ReportDataDTO reportDataDTOQuery = BeanMapperUtil.objConvert(query, ReportDataDTO.class);
        reportDataDTOQuery.setQueryCheckReport(true);
        log.info("调用清结算前置系统,分页查询报表数据,查询条件【{}】", reportDataDTOQuery);
        Response<com.oriental.settlementfront.service.facade.common.model.Pagination<ReportDataDTO>> response
                = bankTransDetailFacade.pageQueryReportData(reportDataDTOQuery, userName);
        log.info("调用清结算前置系统,分页查询报表数据,响应结果【{}】", response);
        if (!response.isSuccess()) {
            throw new BusiException(response.getErrorCode(), response.getErrorMsg());
        }
        pagination.setRowCount(response.getResult().getTotalCount());
        List<ReportDataDTO> reportDataDTOs = response.getResult().getPageList();
        for (ReportDataDTO reportDataDTO : reportDataDTOs) {
            PayBusiCheckReportData data = BeanMapperUtil.objConvert(reportDataDTO, PayBusiCheckReportData.class);
            // 判断是否需要标红
            this.fillIsRed(data);
            list.add(data);
        }
        pagination.setList(list);
    }

    private void fillIsRed(PayBusiCheckReportData data) {
        boolean isRed = false;
        if (!data.getPayAmt().equals(data.getBusiAmt() + data.getRefundAmt())) {
            isRed = true;
        }
        data.setRed(isRed);
    }
}
