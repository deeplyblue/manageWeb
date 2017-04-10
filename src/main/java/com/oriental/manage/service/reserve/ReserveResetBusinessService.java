package com.oriental.manage.service.reserve;

import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.reserve.api.reset.ResetBusinessInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.business.ExtPayTransDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Author: Yang xp
 * Date: 2017/1/10
 * Time: 10:58
 * Desc：
 */
@Slf4j
@Service
public class ReserveResetBusinessService {

    @Autowired
    private ResetBusinessInterface resetBusinessInterface;

    public void callBack(String message, ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = resetBusinessInterface.callbackMessage(message);
        log.info("重置响应信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重置成功");
    }

    public void resetMonthBaseData(String date, ResponseDTO<String> responseDTO) throws ParseException {
        Date businessDate = DateUtils.parse(date,DateUtils.DATESHOWFORMAT);
        ResponseModel<String> responseModel = resetBusinessInterface.resetMonthBase(businessDate,SessionUtils.getUserName());
        log.info("重置月报基础响应信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重置成功");
    }

    public void resetMonthReport(String date, ResponseDTO<String> responseDTO) throws ParseException {
        Date businessDate = DateUtils.parse(date,DateUtils.DATESHOWFORMAT);
        ResponseModel<String> responseModel = resetBusinessInterface.resetMonthReport(businessDate,SessionUtils.getUserName());
        log.info("重置月报响应信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重置成功");
    }

    public void resetPayTransDetail(String startDate,String endDate, ResponseDTO<String> responseDTO) throws ParseException {
        Date queryStartDate = DateUtils.parse(startDate,DateUtils.full_pattern);
        Date queryEndDate = DateUtils.parse(endDate,DateUtils.full_pattern);
        ResponseModel<String> responseModel = resetBusinessInterface.payTransDetailPull(queryStartDate,queryEndDate,SessionUtils.getUserName());
        log.info("重置月报响应信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重置成功");
    }

    public void resetNonSettlement(String startDate,String accountNo, ResponseDTO<String> responseDTO) throws ParseException {
        Date businessDate = DateUtils.parse(startDate,DateUtils.DATESHOWFORMAT);
        ResponseModel<String> responseModel = resetBusinessInterface.resetNonSettlement(businessDate,accountNo,SessionUtils.getUserName());
        log.info("重置非结算信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重置成功");
    }

    public void resetDepositSettlement(String depositBusinessDate,ResponseDTO<String> responseDTO) throws ParseException {
        Date businessDate = DateUtils.parse(depositBusinessDate,DateUtils.DATESHOWFORMAT);
        ResponseModel<String> responseModel = resetBusinessInterface.resetDepositSettlement(businessDate,SessionUtils.getUserName());
        log.info("重置入金结算信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重置成功");
    }

    public void resetRemitSettlement(String remitBusinessDate,ResponseDTO<String> responseDTO) throws ParseException {
        Date businessDate = DateUtils.parse(remitBusinessDate,DateUtils.DATESHOWFORMAT);
        ResponseModel<String> responseModel = resetBusinessInterface.resetRemitSettlement(businessDate,SessionUtils.getUserName());
        log.info("重置出金结算信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重置成功");
    }

    public void resetBusiPayTrans(String startTime, String endTime, ResponseDTO<String> responseDTO) {

        ExtPayTransDetailDto extPayTransDetailDto=new ExtPayTransDetailDto();
        extPayTransDetailDto.setStartDate(startTime);
        extPayTransDetailDto.setEndDate(endTime);
        ResponseModel<String> responseModel = resetBusinessInterface.queryPayTransDetail(extPayTransDetailDto);
        log.info("重跑业务数据信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重跑成功");
    }

    public void createBusiBacthNo(ResponseDTO<String> responseDTO) {
        ResponseModel<String> responseModel = resetBusinessInterface.buildPayTransDetailBatchNo();
        log.info("重新上报业务数据:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重跑成功");
    }


    public void dealReqPayTransDetail(ResponseDTO<String> responseDTO) {
        ResponseModel<String> responseModel = resetBusinessInterface.buildPayTransDetailReqBatchNo();
        log.info("处理业务数据为请求的交易:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重跑成功");
    }

    public void dealPayTransDetail(ResponseDTO<String> responseDTO) {
        ResponseModel<String> responseModel = resetBusinessInterface.dealPayTransDetail();
        log.info("处理超过期限业务数据为请求的交易:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重跑成功");
    }

    public void initBankBalance(ResponseDTO<String> responseDTO,Map<String,String> map) throws ParseException {
        String accountNo = map.get("accountNo");
        Date businessDate = DateUtils.parse(map.get("businessDate"),DateUtils.DATESHORTFORMAT);
        ResponseModel<String> responseModel = resetBusinessInterface.initBankBalance(accountNo,businessDate,SessionUtils.getUserName());
        log.info("处理超过期限业务数据为请求的交易:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"重跑成功");
    }
}
