package com.oriental.manage.controller.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.merchant.BankClassCfg;
import com.oriental.manage.service.merchant.IBankClassInfoService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangjun on 2016/5/23.
 * 查询银行实现类信息
 */
@Slf4j
@Controller
@RequestMapping("merchant/bankClassCfg")
public class BankClassCfgController {
    @Autowired
    private IBankClassInfoService bankClassInfoService;

    @RequestMapping("init")
    public String init(){
        return "merchant/searchBankClassCfg";
    }

    @OperateLogger(content = "查询银行实现类",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("bankClassCfg_select")
    @ResponseBody
    public ResponseDTO<Pagination<BankClassCfg>> queryPage(BankClassCfg bankClassCfg, Pagination<BankClassCfg> pagination){
        ResponseDTO<Pagination<BankClassCfg>> responseDTO=new ResponseDTO<Pagination<BankClassCfg>>();
        try{
            bankClassInfoService.queryPage(pagination,bankClassCfg);

            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);

        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
     return  responseDTO;
    }

    @RequestMapping("toAdd")
    @RequiresPermissions("bankClassCfg_add")
    public String toAdd(){
        return "merchant/addBankClassCfg";
    }

    @OperateLogger(content = "新增银行实现类",operationType = OperateLogger.OperationType.C,tables = "T_BANK_CLASS_CFG")
    @RequestMapping("add")
    @RequiresPermissions("bankClassCfg_add")
    @ResponseBody
    public ResponseDTO<String> add(BankClassCfg bankClassCfg){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            bankClassCfg.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            bankClassCfg.setCreateTime(new Date());
            bankClassCfg.setLastUpdTime(new Date());
           bankClassInfoService.addBankClassCfg(responseDTO,bankClassCfg);

        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }
    @RequestMapping("toUpdate")
    @RequiresPermissions("bankClassCfg_update")
    public String toUpdate(){

        return "merchant/updateBankClassCfg";
    }

    @OperateLogger(content = "修改银行实现类",operationType = OperateLogger.OperationType.U,tables = "T_BANK_CLASS_CFG")
    @RequestMapping("update")
    @RequiresPermissions("bankClassCfg_update")
    @ResponseBody
    public ResponseDTO<String> update(BankClassCfg bankClassCfg){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            bankClassCfg.setLastUpdTime(new Date());
            bankClassInfoService.upadteBankClassCfg(responseDTO,bankClassCfg);

        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }
    @OperateLogger(content = "删除银行实现类",operationType = OperateLogger.OperationType.D,tables = "T_BANK_CLASS_CFG")
    @RequestMapping("delete")
    @RequiresPermissions("bankClassCfg_delete")
    @ResponseBody
    public ResponseDTO<String> delete(BankClassCfg bankClassCfg) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
           bankClassInfoService.deleteBankClassCfg(responseDTO,bankClassCfg);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
