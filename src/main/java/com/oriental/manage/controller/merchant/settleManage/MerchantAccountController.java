package com.oriental.manage.controller.merchant.settleManage;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.merchant.settleManage.ClearAccount;
import com.oriental.manage.service.merchant.settleManage.IMerchantClearAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Author: Yang xp
 * Date: 2016/5/31
 * Time: 15:55
 * Desc：
 */
@Controller
@Slf4j
@RequestMapping("/merchant/settle/account")
public class MerchantAccountController {

    @Autowired
    private IMerchantClearAccountService clearAccountService;

    @RequestMapping("/init")
    public String init(){
        return "/merchant/settleManage/searchMerchantClearAccount";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-account_add")
    public String toAdd(){
        return "/merchant/settleManage/addMerchantClearAccount";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-account_update")
    public String toUpdate(){
        return "/merchant/settleManage/updateMerchantClearAccount";
    }

    @OperateLogger(content = "新增商户账户信息",operationType = OperateLogger.OperationType.C,tables = "T_CLEAR_ACCOUNT")
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("merchant-account_add")
    public ResponseDTO<String> add( ClearAccount baseModel) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("新增信息:{}",baseModel);
        try {
            clearAccountService.addMerchantClearAccount(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改商户账户信息",operationType = OperateLogger.OperationType.U,tables = "T_CLEAR_ACCOUNT")
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("merchant-account_update")
    public  ResponseDTO<String> update(ClearAccount baseModel) {

        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("修改信息:{}",baseModel);
        try {
            clearAccountService.updateMerchantClearAccount(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }


    @OperateLogger(content = "查询商户账户信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("merchant-account_search")
    public ResponseDTO<Pagination<ClearAccount>> search(Pagination<ClearAccount> pagination, ClearAccount baseModel) {

        ResponseDTO<Pagination<ClearAccount>> responseDTO=new ResponseDTO<Pagination<ClearAccount>>();
        log.info("查询信息:{},{}",baseModel,pagination);
        try{
            clearAccountService.searchMerchantClearAccount(pagination,baseModel);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }
}
