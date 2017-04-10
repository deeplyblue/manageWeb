package com.oriental.manage.controller.business;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.ContractInfo;
import com.oriental.manage.service.merchant.IContractService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by wangjun on 2016/5/16.
 * 业务机构合同信息
 */
@Slf4j
@Controller
@RequestMapping("business/contaract")
public class BusinessContractController {

    @Autowired
    private IContractService contractService;

    @RequestMapping("init")
    public String init(){

        return "business/searchContract";
    }

   @RequestMapping("search")
   @ResponseBody
    public ResponseDTO<Pagination<ContractInfo>> queryPage (ContractInfo contractInfo, Pagination<ContractInfo> pagination){

        ResponseDTO<Pagination<ContractInfo>> responseDTO =new ResponseDTO<Pagination<ContractInfo>>();
       try{
           contractInfo.setCompanyType(CompanyType.BUSINESS.getCode());
           contractService.queryPage(pagination,contractInfo);
           responseDTO.setSuccess(true);
           responseDTO.setObject(pagination);

       }catch (Exception e){
           log.error("查询失败", e);
           responseDTO.setSuccess(false);
           responseDTO.setMsg(e.getMessage());
       }

        return responseDTO;
    }

    @RequestMapping("toAdd")
    public String toAdd(){
        return "business/addContract";
    }

    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contractInfo.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            contractInfo.setCompanyType(CompanyType.BUSINESS.getCode());
            contractService.addContract(responseDTO,contractInfo);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("toUpdate")
    public String toUpdate(){
        return "business/updateContract";
    }

    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update(ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contractService.updateContract(responseDTO,contractInfo);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contractService.deleteContract(responseDTO,contractInfo);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
