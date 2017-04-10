package com.oriental.manage.controller.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.bank.BankInfo;
import com.oriental.manage.service.bank.IBankInfoService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/5/23.
 * 查询银行信息
 */

@Slf4j
@Controller
@RequestMapping("merchant/bankInfo")
public class BankInfoController {

    @Autowired
    private IBankInfoService bankInfoService;

    @RequestMapping("init")
    public String init(){
        return "merchant/searchBankInfo";
}

    @OperateLogger(content = "查询银行信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("bankInfo_select")
    @ResponseBody
    public ResponseDTO<Pagination<BankInfo>> queryPage(BankInfo bankInfo , Pagination<BankInfo> pagination){
        ResponseDTO<Pagination<BankInfo>> responseDTO=new ResponseDTO<Pagination<BankInfo>>();
        try{
            bankInfoService.queryPage(pagination,bankInfo);
            responseDTO.setSuccess(true);
           responseDTO.setObject(pagination);

        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @RequestMapping("toAdd")
    @RequiresPermissions("bankInfo_add")
    public String toAdd(){
        return "merchant/addBankInfo";
    }

    @OperateLogger(content = "新增银行信息",operationType = OperateLogger.OperationType.C,tables = "T_BANK_INFO")
    @RequestMapping("add")
    @RequiresPermissions("bankInfo_add")
    @ResponseBody
    public ResponseDTO<String> add(BankInfo bankInfo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            bankInfo.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            bankInfoService.addBnakInfo(responseDTO,bankInfo);

        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @RequestMapping("toUpdate")
    @RequiresPermissions("bankInfo_update")
    public String toUpdate(){
        return"merchant/updateBankInfo";
    }

    @OperateLogger(content = "修改银行信息",operationType = OperateLogger.OperationType.U,tables = "T_BANK_INFO")
    @RequestMapping("update")
    @RequiresPermissions("bankInfo_update")
    @ResponseBody
    public ResponseDTO<String> update(BankInfo bankInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            bankInfoService.updateBankInfo(responseDTO,bankInfo);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "删除银行信息",operationType = OperateLogger.OperationType.D,tables = "T_BANK_INFO")
    @RequestMapping("delete")
    @RequiresPermissions("bankInfo_delete")
    @ResponseBody
    public ResponseDTO<String> delete(BankInfo bankInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
           bankInfoService.deleteBankInfo(responseDTO,bankInfo);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
