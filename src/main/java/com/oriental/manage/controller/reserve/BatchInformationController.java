package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.MessageBatchInfoService;
import com.oriental.reserve.enums.MessageType;
import com.oriental.reserve.model.message.MessageBatch;
import com.oriental.reserve.model.message.MessageDetailBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Yang xp
 * Date: 2016/12/28
 * Time: 15:55
 * Desc：批次信息
 */
@Controller
@Slf4j
@RequestMapping("/reserve/batch")
public class BatchInformationController {

    private Map<String,String> typeUrl = new HashMap<>();

    @PostConstruct
    public void initCache(){
        typeUrl.put(MessageType.PSIS133.getCode(), "reserve/settlement/nonSettlementDetail");
        typeUrl.put(MessageType.PSIS700.getCode(),"reserve/report/month/monthReport101Detail");
        typeUrl.put(MessageType.PSIS701.getCode(),"reserve/report/month/monthReport101AddDetail");
        typeUrl.put(MessageType.PSIS702.getCode(),"reserve/report/month/monthReport102Detail");
        typeUrl.put(MessageType.PSIS703.getCode(),"reserve/report/month/monthReport102AddDetail");
        typeUrl.put(MessageType.PSIS704.getCode(),"reserve/report/month/monthReport103Detail");
        typeUrl.put(MessageType.PSIS705.getCode(),"reserve/report/month/monthReport104Detail");
        typeUrl.put(MessageType.PSIS706.getCode(),"reserve/report/month/monthReport105Detail");
        typeUrl.put(MessageType.PSIS707.getCode(),"reserve/report/month/monthReport106Detail");
        typeUrl.put(MessageType.PSIS708.getCode(),"reserve/report/month/monthReport107Detail");
        typeUrl.put(MessageType.PSIS709.getCode(),"reserve/report/month/monthReport108Detail");
        typeUrl.put(MessageType.PSIS710.getCode(),"reserve/report/month/monthReport109Detail");
        typeUrl.put(MessageType.PSIS711.getCode(),"reserve/report/month/monthReport110Detail");
        typeUrl.put(MessageType.PSIS712.getCode(),"reserve/report/month/monthReport111Detail");
        typeUrl.put(MessageType.PSIS713.getCode(),"reserve/report/month/monthReport112Detail");
        typeUrl.put(MessageType.PSIS714.getCode(),"reserve/report/month/monthReport113Detail");
        typeUrl.put(MessageType.PSIS903.getCode(),"reserve/system/excepRestatementsDetail");
        typeUrl.put(MessageType.PSIS200.getCode(),"reserve/infoManagement/msgApplyInfoDetail");
        typeUrl.put(MessageType.PSIS213.getCode(), "reserve/infoManagement/paymentContactInfoForBarch");
        typeUrl.put(MessageType.PSIS216.getCode(),"reserve/infoManagement/paymentComplaintInfoForBatch");
        typeUrl.put(MessageType.PSIS201.getCode(),"reserve/infoManagement/paymentBaseInfoDetailForBatch");
        typeUrl.put(MessageType.PSIS203.getCode(),"reserve/infoManagement/paymentManagerInfoDetailForBatch");
        typeUrl.put(MessageType.PSIS740.getCode(),"reserve/report/quarter/quarterReport201Detail");
        typeUrl.put(MessageType.PSIS741.getCode(),"reserve/report/quarter/quarterReport202Detail");
        typeUrl.put(MessageType.PSIS744.getCode(),"reserve/report/quarter/quarterReport205Detail");
        typeUrl.put(MessageType.PSIS749.getCode(),"reserve/report/quarter/quarterReport210Detail");
        typeUrl.put(MessageType.PSIS750.getCode(),"reserve/report/quarter/quarterReport211Detail");
        typeUrl.put(MessageType.PSIS909.getCode(), "reserve/system/aloneAccountDetail");
        typeUrl.put(MessageType.PSIS151.getCode(), "reserve/settlementBatchDetail");
        typeUrl.put(MessageType.PSIS152.getCode(), "reserve/settlementBatchDetail");
        typeUrl.put(MessageType.PSIS800.getCode(), "reserve/businessInformationDetail");

        typeUrl.put(MessageType.PSIS801.getCode(), "reserve/searchBankAccountDetail");
    }

    @Autowired
    private MessageBatchInfoService messageBatchInfoService;

    //    @RequiresPermissions(value = "reserveBatch_search")
    @RequestMapping("init")
    public String init() {
        return "reserve/searchBatchInformation";
    }

    @RequestMapping("toDetail")
    public String toDetail(String messageType) {
        log.info("请求传入参数:{}", messageType);
        String returnUrl = typeUrl.get(messageType);
        log.info("响应url:{}",returnUrl);
        return returnUrl;
    }

    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<MessageBatch>> querySubmitBatchInfo(@RequestBody Pagination<MessageBatch> pagination) {
        ResponseDTO<Pagination<MessageBatch>> responseDTO = new ResponseDTO<>();
        try {
            messageBatchInfoService.query(pagination, pagination.getQueryBean(), responseDTO);
        } catch (Exception e) {
            log.error("查询备付金审核批次失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

    @RequestMapping("/query/detail")
    @ResponseBody
    public ResponseDTO<Pagination<? extends MessageDetailBase>> queryBatchDetail(@RequestBody MessageBatch messageBatch) {
        ResponseDTO<Pagination<? extends MessageDetailBase>> responseDTO = new ResponseDTO<>();
        try {
            messageBatchInfoService.queryBatchDetail(responseDTO, messageBatch);
        } catch (Exception e) {
            log.error("查询备付金审核批次详细信息失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

    @RequestMapping("/audit/detail")
    @ResponseBody
    public ResponseDTO<String> auditMessage(@RequestBody MessageBatch messageBatch){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            messageBatchInfoService.auditMessage(responseDTO,messageBatch);
        }catch (Exception e){
            log.error("批次审核失败:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("审核失败");
        }
        return responseDTO;
    }

    @RequestMapping("/regenerate")
    @ResponseBody
    public ResponseDTO<String> regenerateBatch(@RequestBody MessageBatch messageBatch){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            messageBatchInfoService.regenerateBatch(responseDTO,messageBatch);
        }catch (Exception e){
            log.error("批次重新生成失败:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("重新生成失败失败");
        }
        return responseDTO;
    }

}
