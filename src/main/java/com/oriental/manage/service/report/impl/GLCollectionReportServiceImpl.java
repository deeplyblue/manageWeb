package com.oriental.manage.service.report.impl;

import com.oriental.check.commons.enums.ChkType;
import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.check.commons.util.DateUtil;
import com.oriental.check.service.facade.manager.BankChkResultFacade;
import com.oriental.check.service.facade.manager.ChkCfgFacade;
import com.oriental.check.service.facade.manager.model.BankChkResultDTO;
import com.oriental.check.service.facade.manager.model.ChkCfgDTO;
import com.oriental.clearDubbo.api.clear.day.ClearDayInterface;
import com.oriental.clearDubbo.model.base.RequestModel;
import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.clearDubbo.model.clear.sum.OrgClearDayModel;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.report.GLCollectionReportData;
import com.oriental.manage.service.report.IGLCollectionReportService;
import com.oriental.paycenter.commons.mode.Response;
import com.oriental.reserve.api.paid.PaidQueryInterface;
import com.oriental.reserve.enums.OrgType;
import com.oriental.reserve.model.business.DayTransDetailDto;
import com.oriental.reserve.model.paid.PaidQueryReqDto;
import com.oriental.reserve.model.paid.PaidQueryRespDto;
import com.oriental.settlementfront.service.facade.manager.BankTransDetailFacade;
import com.oriental.settlementfront.service.facade.manager.model.ReportDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <ul>
 * <li>总账收款核对报表服务实现</li>
 * <li>User:蒯越 Date:2016/12/1 Time:14:50</li>
 * </ul>
 */
@Slf4j
@Service
public class GLCollectionReportServiceImpl implements IGLCollectionReportService {

    @Autowired
    private BankTransDetailFacade bankTransDetailFacade;

    @Autowired
    private ChkCfgFacade chkCfgFacade;

    @Autowired
    private BankChkResultFacade bankChkResultFacade;

    @Autowired
    private ClearDayInterface clearDayInterface;

    @Autowired
    private PaidQueryInterface paidQueryInterface;

    @Override
    public void pageQuery(Pagination<GLCollectionReportData> pagination, GLCollectionReportData query) {
        List<GLCollectionReportData> list = new ArrayList<>();
        String userName = SessionUtils.getUserName();
        // 查询平台成功数据
        ReportDataDTO reportDataDTOQuery = BeanMapperUtil.objConvert(query, ReportDataDTO.class);
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
            GLCollectionReportData data = BeanMapperUtil.objConvert(reportDataDTO, GLCollectionReportData.class);
            // 查询文件信息
            this.queryFileData(reportDataDTO, userName, data);
            // 查询日结信息
            this.queryClearDayData(reportDataDTO, data);
            // 查询实收信息
            this.queryPaidData(reportDataDTO, data);
            // 判断是否需要标红
            this.fillIsRed(data);
            list.add(data);
        }
        pagination.setList(list);
    }

    private void queryPaidData(ReportDataDTO reportDataDTO, GLCollectionReportData data) {
        PaidQueryReqDto paidQueryReqDto = new PaidQueryReqDto();
        paidQueryReqDto.setOrgCode(reportDataDTO.getPayOrgCode());
        paidQueryReqDto.setOrgType(OrgType.PAY.getCode());
        paidQueryReqDto.setTransDate(reportDataDTO.getBankSettleDate());
        log.info("调用备付金系统,查询实收数据,查询条件【{}】", paidQueryReqDto);
        com.oriental.reserve.model.ResponseModel<List<PaidQueryRespDto>> responseModel
                = paidQueryInterface.queryPaid(paidQueryReqDto);
        log.info("调用备付金系统,查询实收数据,响应结果【{}】", responseModel);
        if (!responseModel.isSuccess()) {
            throw new BusiException(responseModel.getErrorCode(), responseModel.getErrorMsg());
        }
        List<PaidQueryRespDto> paidQueryRespDtoList = responseModel.getResult();
        if (paidQueryRespDtoList != null && !paidQueryRespDtoList.isEmpty()) {
            PaidQueryRespDto paidQueryRespDto = paidQueryRespDtoList.get(0);
            if (paidQueryRespDto != null && paidQueryRespDto.isPaid()) {
                Date minTransDate = reportDataDTO.getBankSettleDate();
                Date maxTransDate = reportDataDTO.getBankSettleDate();
                List<DayTransDetailDto> dayTransDetailDtoList = paidQueryRespDto.getDayTransDetailDtoList();
                for (DayTransDetailDto dayTransDetailDto : dayTransDetailDtoList) {
                    if (dayTransDetailDto.getTransDate().before(minTransDate)) {
                        minTransDate = dayTransDetailDto.getTransDate();
                    }
                    if (dayTransDetailDto.getTransDate().after(maxTransDate)) {
                        maxTransDate = dayTransDetailDto.getTransDate();
                    }
                }
                // 交易日期范围
                String transDates;
                if (minTransDate.equals(maxTransDate)) {
                    transDates = DateUtil.format(minTransDate, DateUtil.yyyy_MM_dd);
                } else {
                    transDates = DateUtil.format(minTransDate, DateUtil.yyyy_MM_dd)
                            + " 至 " + DateUtil.format(maxTransDate, DateUtil.yyyy_MM_dd);
                }
                data.setPaidInDate(paidQueryRespDto.getRecTransDate());
                data.setPaidInAmt(paidQueryRespDto.getAmtRec());
                data.setPaidOutDate(paidQueryRespDto.getPayTransDate());
                data.setPaidOutAmt(paidQueryRespDto.getAmtPay());
                data.setTransDates(transDates);
            }
        }
    }

    private void queryFileData(ReportDataDTO reportDataDTO, String userName, GLCollectionReportData data) {
        // 判断是否强制对账,若是直接填写文件信息
        ChkCfgDTO chkCfgDTO = new ChkCfgDTO();
        chkCfgDTO.setCompanyCode(reportDataDTO.getPayOrgCode());
        chkCfgDTO.setPageSize(1);
        log.info("调用对账系统,分页查询对账配置,查询条件【{}】", chkCfgDTO);
        Response<com.oriental.check.service.facade.model.Pagination<ChkCfgDTO>> response1
                = chkCfgFacade.pageQuery(chkCfgDTO, userName);
        log.info("调用对账系统,分页查询对账配置,响应结果【{}】", response1);
        if (!response1.isSuccess()) {
            throw new BusiException(response1.getErrorCode(), response1.getErrorMsg());
        }
        if (response1.getResult().getPageList() != null && !response1.getResult().getPageList().isEmpty()) {
            chkCfgDTO = response1.getResult().getPageList().get(0);
            if (ChkType.FORCE_CHECK_SUCCESS.getCode().equals(chkCfgDTO.getChkType())) {
                data.setFileCount(data.getSuccCount());
                data.setFileAmt(data.getSuccAmt());
                return;
            }
        }
        // 查询文件信息
        BankChkResultDTO bankChkResultDTO = new BankChkResultDTO();
        bankChkResultDTO.setPayOrgCode(reportDataDTO.getPayOrgCode());
        bankChkResultDTO.setSettleDate(reportDataDTO.getBankSettleDate());
        bankChkResultDTO.setPageSize(1);
        log.info("调用对账系统,分页查询对账结果,查询条件【{}】", bankChkResultDTO);
        Response<com.oriental.check.service.facade.model.Pagination<BankChkResultDTO>> response
                = bankChkResultFacade.pageQuery(bankChkResultDTO, userName);
        log.info("调用对账系统,分页查询对账结果,响应结果【{}】", response);
        if (!response.isSuccess()) {
            throw new BusiException(response.getErrorCode(), response.getErrorMsg());
        }
        if (response.getResult().getPageList() != null && !response.getResult().getPageList().isEmpty()) {
            bankChkResultDTO = response.getResult().getPageList().get(0);
            data.setFileCount(bankChkResultDTO.getExtTransCount() == null ? 0 : bankChkResultDTO.getExtTransCount());
            data.setFileAmt(bankChkResultDTO.getExtTransAmt());
        } else {
            data.setFileCount(0);
            data.setFileAmt(0L);
        }
    }

    private void queryClearDayData(ReportDataDTO reportDataDTO, GLCollectionReportData data) {
        OrgClearDayModel orgClearDayModel = new OrgClearDayModel();
        orgClearDayModel.setOrgCode(reportDataDTO.getPayOrgCode());
        orgClearDayModel.setSettleDateBegin(reportDataDTO.getBankSettleDate());
        orgClearDayModel.setSettleDateEnd(reportDataDTO.getBankSettleDate());
        RequestModel<OrgClearDayModel> requestModel = new RequestModel<>();
        requestModel.setPageSize(1);
        requestModel.setRequest(orgClearDayModel);
        log.info("调用清结算系统,查询日结信息,查询条件【{}】", requestModel);
        ResponseModel<OrgClearDayModel> responseModel = clearDayInterface.getPayClearDayModel(requestModel);
        log.info("调用清结算系统,查询日结信息,响应结果【{}】", responseModel);
        if (!responseModel.getResponseResult()) {
            throw new BusiException(responseModel.getResponseMessage(), responseModel.getResponseMessage());
        }
        if (responseModel.getList() != null && !responseModel.getList().isEmpty()) {
            orgClearDayModel = responseModel.getList().get(0);
            data.setTransCountD(orgClearDayModel.getTransCountR());
            data.setTransAmtD(orgClearDayModel.getTransAmtR().longValue());
            data.setTransCountC(orgClearDayModel.getTransCountP());
            data.setTransAmtC(orgClearDayModel.getTransAmtP().longValue());
        } else {
            data.setTransCountD(0);
            data.setTransAmtD(0L);
            data.setTransCountC(0);
            data.setTransAmtC(0L);
        }
    }

    private void fillIsRed(GLCollectionReportData data) {
        boolean isRed = false;
        if (!data.getSuccCount().equals(data.getFileCount())) {
            isRed = true;
        }
        if (!data.getSuccAmt().equals(data.getFileAmt())) {
            isRed = true;
        }
        if (!data.getSuccCount().equals(data.getTransCountD() + data.getTransCountC())) {
            isRed = true;
        }
        if (!data.getSuccAmt().equals(data.getTransAmtD() + data.getTransAmtC())) {
            isRed = true;
        }
        if (data.getPaidInDate() == null) {
            isRed = true;
        }
        data.setRed(isRed);
    }
}
