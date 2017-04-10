package com.oriental.manage.controller.business;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.business.MchntBank;
import com.oriental.manage.service.business.IMchntBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/5/24.
 * 业务机构银行配置
 */
@Slf4j
@Controller
@RequestMapping("business/mchntBank")
public class MchntBankConterller {

    @Autowired
    private IMchntBankService mchntBankService;

    @RequestMapping("init")
    public String init(){
        return "business/searchMchntBank";
    }

    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<MchntBank>> queryPage (MchntBank mchntBank, Pagination<MchntBank> pagination){
        ResponseDTO<Pagination<MchntBank>> responseDTO =new ResponseDTO<Pagination<MchntBank>>();
        try {

            mchntBank.setCompanyType(CompanyType.BUSINESS.getCode());
            mchntBankService.queryPage(pagination,mchntBank);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

}
