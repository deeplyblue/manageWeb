package com.oriental.manage.service.report.impl;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.check.commons.util.DateUtil;
import com.oriental.clearDubbo.api.clear.day.ClearDayInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDayModel;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.report.GLPaymentReportData;
import com.oriental.manage.pojo.report.GLPaymentReportPaidData;
import com.oriental.manage.service.report.IGLPaymentReportService;
import com.oriental.paycenter.commons.mode.Response;
import com.oriental.reserve.api.paid.PaidQueryInterface;
import com.oriental.reserve.enums.OrgType;
import com.oriental.reserve.model.business.DayTransDetailDto;
import com.oriental.reserve.model.paid.PaidQueryReqDto;
import com.oriental.reserve.model.paid.PaidQueryRespDto;
import com.oriental.settlementfront.service.facade.manager.MchntTransDetailFacade;
import com.oriental.settlementfront.service.facade.manager.model.ReportDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <ul>
 * <li>总账付款核对报表服务实现</li>
 * <li>User:蒯越 Date:2016/12/1 Time:14:50</li>
 * </ul>
 */
@Slf4j
@Service
public class GLPaymentReportServiceImpl implements IGLPaymentReportService {

    @Autowired
    private MchntTransDetailFacade mchntTransDetailFacade;

    @Autowired
    private ClearDayInterface clearDayInterface;

    @Autowired
    private PaidQueryInterface paidQueryInterface;

    @Override
    public void pageQuery(Pagination<GLPaymentReportData> pagination, GLPaymentReportData query) {
        List<GLPaymentReportData> list = new ArrayList<>();
        String userName = SessionUtils.getUserName();
        // 查询平台成功数据
        ReportDataDTO reportDataDTOQuery = BeanMapperUtil.objConvert(query, ReportDataDTO.class);
        log.info("调用清结算前置系统,分页查询报表数据,查询条件【{}】", reportDataDTOQuery);
        Response<com.oriental.settlementfront.service.facade.common.model.Pagination<ReportDataDTO>> response
                = mchntTransDetailFacade.pageQueryReportData(reportDataDTOQuery, userName);
        log.info("调用清结算前置系统,分页查询报表数据,响应结果【{}】", response);
        if (!response.isSuccess()) {
            throw new BusiException(response.getErrorCode(), response.getErrorMsg());
        }
        pagination.setRowCount(response.getResult().getTotalCount());
        List<ReportDataDTO> reportDataDTOs = response.getResult().getPageList();
        for (ReportDataDTO reportDataDTO : reportDataDTOs) {
            GLPaymentReportData data = BeanMapperUtil.objConvert(reportDataDTO, GLPaymentReportData.class);
            // 查询日结信息
            this.queryClearDayData(reportDataDTO, data);
            // 查询实付信息
            this.queryPaidData(reportDataDTO, data);
            // 判断是否需要标红
            this.fillIsRed(data);
            list.add(data);
        }
        pagination.setList(list);
    }

    private void queryPaidData(ReportDataDTO reportDataDTO, GLPaymentReportData data) {
        PaidQueryReqDto paidQueryReqDto = new PaidQueryReqDto();
        paidQueryReqDto.setOrgCode(reportDataDTO.getMchntCode());
        paidQueryReqDto.setOrgType(OrgType.MERCHANT.getCode());
        paidQueryReqDto.setTransDate(reportDataDTO.getBusiSettleDate());
        log.info("调用备付金系统,查询实付数据,查询条件【{}】", paidQueryReqDto);
        com.oriental.reserve.model.ResponseModel<List<PaidQueryRespDto>> responseModel
                = paidQueryInterface.queryPaid(paidQueryReqDto);
        log.info("调用备付金系统,查询实收数据,响应结果【{}】", responseModel);
        if (!responseModel.isSuccess()) {
            throw new BusiException(responseModel.getErrorCode(), responseModel.getErrorMsg());
        }
        List<PaidQueryRespDto> paidQueryRespDtoList = responseModel.getResult();
        List<GLPaymentReportPaidData> glPaymentReportPaidDataList = data.getGlPaymentReportPaidDataList();
        for (PaidQueryRespDto paidQueryRespDto : paidQueryRespDtoList) {
            if (paidQueryRespDto != null) {
                // 获取支付机构
                String payOrgCode = null;
                String[] arr = paidQueryRespDto.getOrgCode().split("_");
                if (arr.length >= 2) {
                    payOrgCode = arr[1];
                }
                GLPaymentReportPaidData glPaymentReportPaidData = new GLPaymentReportPaidData();
                glPaymentReportPaidData.setPayOrgCode(payOrgCode);
                // 处理实付信息
                if (paidQueryRespDto.isPaid()) {
                    // 处理交易日期范围
                    Date minTransDate = reportDataDTO.getBusiSettleDate();
                    Date maxTransDate = reportDataDTO.getBusiSettleDate();
                    List<DayTransDetailDto> dayTransDetailDtoList = paidQueryRespDto.getDayTransDetailDtoList();
                    for (DayTransDetailDto dayTransDetailDto : dayTransDetailDtoList) {
                        if (dayTransDetailDto.getTransDate().before(minTransDate)) {
                            minTransDate = dayTransDetailDto.getTransDate();
                        }
                        if (dayTransDetailDto.getTransDate().after(maxTransDate)) {
                            maxTransDate = dayTransDetailDto.getTransDate();
                        }
                    }
                    String transDates;
                    if (minTransDate.equals(maxTransDate)) {
                        transDates = DateUtil.format(minTransDate);
                    } else {
                        transDates = DateUtil.format(minTransDate) + "至" + DateUtil.format(maxTransDate);
                    }
                    glPaymentReportPaidData.setPaidOutDate(paidQueryRespDto.getPayTransDate());
                    glPaymentReportPaidData.setPaidOutAmt(paidQueryRespDto.getAmtPay());
                    glPaymentReportPaidData.setPaidInDate(paidQueryRespDto.getRecTransDate());
                    glPaymentReportPaidData.setPaidInAmt(paidQueryRespDto.getAmtRec());
                    glPaymentReportPaidData.setTransDates(transDates);
                }
                glPaymentReportPaidDataList.add(glPaymentReportPaidData);
            }
        }
    }

    private void queryClearDayData(ReportDataDTO reportDataDTO, GLPaymentReportData data) {
        OrgClearDayModel orgClearDayModel = new OrgClearDayModel();
        orgClearDayModel.setOrgCode(reportDataDTO.getMchntCode());
        orgClearDayModel.setSettleDateBegin(reportDataDTO.getBusiSettleDate());
        orgClearDayModel.setSettleDateEnd(reportDataDTO.getBusiSettleDate());
        RequestModel<OrgClearDayModel> requestModel = new RequestModel<>();
        requestModel.setPageSize(100);
        requestModel.setRequest(orgClearDayModel);
        log.info("调用清结算系统,查询日结信息,查询条件【{}】", requestModel);
        ResponseModel<OrgClearDayModel> responseModel = clearDayInterface.getMchntClearDayModel(requestModel);
        log.info("调用清结算系统,查询日结信息,响应结果【{}】", responseModel);
        if (!responseModel.getResponseResult()) {
            throw new BusiException(responseModel.getResponseMessage(), responseModel.getResponseMessage());
        }
        if (responseModel.getList() != null && !responseModel.getList().isEmpty()) {
            for (OrgClearDayModel model : responseModel.getList()) {
                data.setTransCountD(data.getTransCountD() + model.getTransCountP());
                data.setTransAmtD(data.getTransAmtD() + model.getTransAmtP().longValue());
                data.setTransCountC(data.getTransCountC() + model.getTransCountR());
                data.setTransAmtC(data.getTransAmtC() + model.getTransAmtR().longValue());
            }
        }
    }

    private void fillIsRed(GLPaymentReportData data) {
        boolean isRed = false;
        if (!data.getSuccCount().equals(data.getTransCountD() + data.getTransCountC())) {
            isRed = true;
        }
        if (!data.getSuccAmt().equals(data.getTransAmtD() + data.getTransAmtC())) {
            isRed = true;
        }
        List<GLPaymentReportPaidData> glPaymentReportPaidDataList = data.getGlPaymentReportPaidDataList();
        if (glPaymentReportPaidDataList == null || glPaymentReportPaidDataList.isEmpty()) {
            isRed = true;
        } else {
            for (GLPaymentReportPaidData glPaymentReportPaidData : glPaymentReportPaidDataList) {
                if (glPaymentReportPaidData.getPaidOutDate() == null) {
                    isRed = true;
                    break;
                }
            }
        }
        data.setRed(isRed);
    }
}
