package com.oriental.manage.controller.reserve.trans;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.BankTransDetailService;
import com.oriental.reserve.model.business.PeriodCheckDto;
import com.oriental.reserve.model.trans.BankTransDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2017/1/11
 * Time: 23:41
 * Desc：周期结算查询
 */
@Slf4j
@Controller
@RequestMapping("/reserve/period")
public class PeriodCheckController {

    @Autowired
    private BankTransDetailService bankTransDetailService;


    @RequestMapping("/init")
    public String init(){
        return "reserve/trans/searchPeriodCheck";
    }

    @RequestMapping("/toCheck")
    public String toCheck(){
        return "reserve/trans/checkPeriodCheckBank";
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<PeriodCheckDto>> queryPeriodCheck(@RequestBody Pagination<PeriodCheckDto> pagination){
        ResponseDTO<Pagination<PeriodCheckDto>> responseDTO = new ResponseDTO<>();
        try {
            log.info("查询周结数据请求参数:{}",pagination);
            bankTransDetailService.queryPeriodCheck(pagination.getQueryBean(),responseDTO,pagination);
        }catch (Exception e){
            log.error("周期结算数据查询异常",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

    @RequestMapping("/search/bank/trans")
    @ResponseBody
    public ResponseDTO<Pagination<BankTransDetailDto>> queryBankTransDetail(@RequestBody PeriodCheckDto periodCheckDto){
        ResponseDTO<Pagination<BankTransDetailDto>> responseDTO = new ResponseDTO<>();
        try {
            bankTransDetailService.queryBankTrans(periodCheckDto,responseDTO);
        }catch (Exception e){
            log.error("周期结算数据查询银行数据异常:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

    /**
     *  通过结算数据关联银行流水信息
     * @param bankTransDetailDtoList 银行流水信息
     */
    @RequestMapping("/update/bank/trans")
    @ResponseBody
    public ResponseDTO<String> updateBankTransDetail(@RequestBody List<BankTransDetailDto> bankTransDetailDtoList){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            log.info("银行流水修改:{}",bankTransDetailDtoList);
            bankTransDetailService.updateBankTransDetail(bankTransDetailDtoList,responseDTO);
        }catch (Exception e){
            log.error("周期结算数据修改银行数据异常:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

}
