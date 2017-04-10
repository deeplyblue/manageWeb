package com.oriental.manage.controller.reserve.reset;


import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.ReserveResetBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Author: Yang xp
 * Date: 2017/1/10
 * Time: 10:33
 * Desc：
 */
@Slf4j
@Controller
@RequestMapping("/reserve/reset")
public class ReserveResetBusinessController {

    @Autowired
    private ReserveResetBusinessService reserveResetBusinessService;

    @RequestMapping("/init")
    public String init(){
        return "reserve/reset/bankCallBack";
    }

    @RequestMapping("/init/business")
    public String initBusiness(){
        return "reserve/reset/resertBusiness";
    }

    @RequestMapping("/callback")
    @ResponseBody
    public ResponseDTO<String> callbackMessage(@RequestBody Map<String,String> map){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("回调信息:{}",map);
            reserveResetBusinessService.callBack(map.get("message"),responseDTO);
        }catch (Exception e){
            log.error("银行信息回调错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }

    @RequestMapping("/month/base")
    @ResponseBody
    public ResponseDTO<String> resetMonthReportBase(@RequestBody Map<String,String> map){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("重置月报基础数据请求:{}",map);
            reserveResetBusinessService.resetMonthBaseData(map.get("baseBusinessDate"),responseDTO);
        }catch (Exception e){
            log.error("重置月报基础数据错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }
    @RequestMapping("/month/report")
    @ResponseBody
    public ResponseDTO<String> resetMonthReport(@RequestBody Map<String,String> map){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("重置月报报表数据请求:{}",map);
            reserveResetBusinessService.resetMonthReport(map.get("reportBusinessDate"),responseDTO);
        }catch (Exception e){
            log.error("重置月报报表数据错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }

    @RequestMapping("/pay/trans")
    @ResponseBody
    public ResponseDTO<String> resetPayTransDetail(@RequestBody Map<String,String> map){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("重新获取交易请求:{}",map);
            reserveResetBusinessService.resetPayTransDetail(map.get("startDate"),map.get("endDate"),responseDTO);
        }catch (Exception e){
            log.error("重新获取交易数据错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }


    @RequestMapping("/non/settlement")
    @ResponseBody
    public ResponseDTO<String> resetNonSettlement(@RequestBody Map<String,String> map){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("重新非结算请求:{}",map);
            reserveResetBusinessService.resetNonSettlement(map.get("NonBusinessDate"),map.get("accountNo"),responseDTO);
        }catch (Exception e){
            log.error("重新非结算错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }

    @RequestMapping("/deposit/settlement")
    @ResponseBody
    public ResponseDTO<String> resetDepositSettlement(@RequestBody Map<String,String> map){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("重新入金结算请求:{}",map);
            reserveResetBusinessService.resetDepositSettlement(map.get("depositBusinessDate"),responseDTO);
        }catch (Exception e){
            log.error("重新入金结算错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }

    @RequestMapping("/remit/settlement")
    @ResponseBody
    public ResponseDTO<String> resetRemitSettlement(@RequestBody Map<String,String> map){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("重新出金结算请求:{}",map);
            reserveResetBusinessService.resetRemitSettlement(map.get("remitBusinessDate"),responseDTO);
        }catch (Exception e){
            log.error("重新出金结算错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }


    @RequestMapping("/pay/resetBusiPayTrans")
    @ResponseBody
    public ResponseDTO<String> resetBusiPayTrans(@RequestBody Map<String,String> map){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("重新获取交易请求:{}",map);
            reserveResetBusinessService.resetBusiPayTrans(map.get("startTime"),map.get("endTime"),responseDTO);
        }catch (Exception e){
            log.error("重新获取交易数据错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }

    @RequestMapping("/pay/createBusiBacthNo")
    @ResponseBody
    public ResponseDTO<String> createBusiBacthNo(){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            reserveResetBusinessService.createBusiBacthNo(responseDTO);
        }catch (Exception e){
            log.error("重新上报业务数据:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }

    @RequestMapping("/pay/dealReqPayTransDetail")
    @ResponseBody
    public ResponseDTO<String> dealReqPayTransDetail(){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            reserveResetBusinessService.dealReqPayTransDetail(responseDTO);
        }catch (Exception e){
            log.error("处理业务数据为请求的交易:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }
    @RequestMapping("/pay/dealPayTransDetail")
    @ResponseBody
    public ResponseDTO<String> dealPayTransDetail(){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            reserveResetBusinessService.dealPayTransDetail(responseDTO);
        }catch (Exception e){
            log.error("处理超过期限业务数据为请求的交易:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }

    @RequestMapping("/bank/init/balance")
    @ResponseBody
    public ResponseDTO<String> initBankBalance(@RequestBody Map<String,String> map){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("银行余额初始化请求参数:{}",map);
            reserveResetBusinessService.initBankBalance(responseDTO,map);
        }catch (Exception e){
            log.error("处理银行余额初始化:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("处理失败");
        }
        return responseDTO;
    }

}
