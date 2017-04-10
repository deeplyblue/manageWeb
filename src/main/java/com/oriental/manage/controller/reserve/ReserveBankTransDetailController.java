package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.enums.ErrorCodeManage;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.BankTransDetailService;
import com.oriental.reserve.model.business.PeriodCheckDto;
import com.oriental.reserve.model.trans.BankTransDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/6/29
 * Time: 18:06
 * Desc：银行交易明细
 */
@Slf4j
@Controller
@RequestMapping("/bank/trans/detail")
public class ReserveBankTransDetailController {

    @Autowired
    private BankTransDetailService bankTransDetailService;

    @RequestMapping("/init")
    public String init(){
        return "reserve/searchBankTransDetail";
    }

    @RequiresPermissions("reserveBankTransDetail_update")
    @RequestMapping("/toUpdate")
    public String update(){
        return "reserve/updateBankTransDetail";
    }

    @RequestMapping("/toCheck")
    public String toCheck(){
        return "reserve/checkBankTransDetail";
    }

    @RequestMapping("/search")
    @RequiresPermissions("bankTransDetail_search")
    @ResponseBody
    public ResponseDTO<Pagination<BankTransDetailDto>> search(@RequestBody Pagination<BankTransDetailDto> pagination){
        ResponseDTO<Pagination<BankTransDetailDto>> responseDTO = new ResponseDTO<>();
        try{
            bankTransDetailService.queryBankTransDetail(pagination,responseDTO);
        }catch (Exception e){
            log.error("查询报表生成信息",e);
            responseDTO.setMsg("操作失败");
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequestMapping("/update")
    @RequiresPermissions("reserveBankTransDetail_update")
    @ResponseBody
    public ResponseDTO<String> update(@RequestBody BankTransDetailDto bankTransDetail){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("备付金交易修改请求:{}",bankTransDetail);
            bankTransDetailService.updateBankTransDetail(bankTransDetail,responseDTO);
        }catch (Exception e){
            log.error("{}:备付金银行交易信息修改异常:",bankTransDetail,e);
            responseDTO.setMsg("操作失败");
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    /**
     * 根据银行交易流水查询业务金额
     * 实收查询应收
     * @param bankTransDetail 银行实际流水
     * @return 核对业务流水
     */
    @RequestMapping("/queryPeriodCheck")
    @ResponseBody
    public ResponseDTO<Pagination<PeriodCheckDto>> queryPeriodCheck(@RequestBody BankTransDetailDto bankTransDetail){
        ResponseDTO<Pagination<PeriodCheckDto>> responseDTO = new ResponseDTO<>();
        try {
            log.info("备付金核对查询请求参数:{}",bankTransDetail);
            bankTransDetailService.queryPeriodCheck(bankTransDetail,responseDTO);
        }catch (Exception e){
            log.info("{}:查询业务核对信息错误:",bankTransDetail,e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

    @RequestMapping("/updatePeriodCheck")
    @ResponseBody
    public ResponseDTO<String> updatePeriodCheck(@RequestBody List<PeriodCheckDto> periodCheckList){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("修改核对请求参数:{}",periodCheckList);
            bankTransDetailService.updatePeriodCheck(periodCheckList,responseDTO);
        }catch (Exception e){
            log.error("修改核对异常:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败");
        }
        return responseDTO;
    }

    @RequestMapping("/clearPeriodCheck")
    @ResponseBody
    public ResponseDTO<String> clearPeriodCheck(@RequestBody PeriodCheckDto periodCheck){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("清除核对请求参数:{}",periodCheck);
            bankTransDetailService.clearPeriodCheck(periodCheck,responseDTO);
        }catch (Exception e){
            log.error("修改核对异常:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(ErrorCodeManage.SYSTEM_EXCEPTION.getDesc());
        }
        return responseDTO;
    }

}
