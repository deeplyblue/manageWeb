package com.oriental.manage.controller.merchant.itconfig;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.merchant.itconfig.KeyInfo;
import com.oriental.manage.service.merchant.itconfig.IMerchantKeyInfoService;
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
 * Date: 2016/5/30
 * Time: 18:06
 * Desc：商户密钥配置
 */
@Slf4j
@Controller
@RequestMapping("/merchant/itCfg/key")
public class MerchantKeyInfoController  {

    @Autowired
    private IMerchantKeyInfoService merchantKeyInfoService;

    @RequestMapping("/init")
    public String init(){
        return "merchant/itconfig/searchMerchantKeyInfo";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-key_add")
    public String toAdd(){
        return "merchant/itconfig/addMerchantKeyInfo";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-key_update")
    public String toUpdate(){
        return "merchant/itconfig/updateMerchantKeyInfo";
    }

    @RequestMapping("/toDataKey")
    @RequiresPermissions("merchant-dataKey_query")
    public String toDataKey(){ return "merchant/itconfig/searchMerchantDataKey";}

    @RequestMapping("/toRsaKey")
    @RequiresPermissions("merchant-RSAKey_query")
    public String toRsaKey(){ return "merchant/itconfig/searchMerchantRsaPublicKey";}

    @OperateLogger(content = "新增商户密钥配置",operationType = OperateLogger.OperationType.C,tables = "T_KEY_INFO" )
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("merchant-key_add")
    public ResponseDTO<String> add( KeyInfo baseModel) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("新增信息:{}",baseModel);
        try {
            log.debug("KeyInfo--EncType:"+baseModel.getEncType());
            merchantKeyInfoService.add(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改商户密钥配置",operationType = OperateLogger.OperationType.U,tables = "T_KEY_INFO" )
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("merchant-key_update")
    public ResponseDTO<String> update( KeyInfo baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("修改信息:{}",baseModel);
        try {
            merchantKeyInfoService.update(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "查询商户密钥配置",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("merchant-RSAKey_query")
    public  ResponseDTO<Pagination<KeyInfo>> search(Pagination<KeyInfo> pagination, KeyInfo baseModel) {
        ResponseDTO<Pagination<KeyInfo>> responseDTO=new ResponseDTO<Pagination<KeyInfo>>();
        log.info("查询信息:{},{}",baseModel,pagination);
        try{
            merchantKeyInfoService.search(pagination,baseModel);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;

    }
    @OperateLogger(content = "查看数据加密KEY",operationType = OperateLogger.OperationType.R )
    @RequestMapping("/searchDataKey")
    @RequiresPermissions("merchant-dataKey_query")
    @ResponseBody
    public ResponseDTO<KeyInfo> searchDataKey (KeyInfo baseModel){
        ResponseDTO<KeyInfo> responseDTO=new ResponseDTO<KeyInfo>();
        try{
            KeyInfo dateKey=merchantKeyInfoService.searchDataKey(baseModel);
            responseDTO.setObject(dateKey);
            responseDTO.setSuccess(true);
        }catch(Exception e){
            log.error("系统异常",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "查看数据RSA公钥",operationType = OperateLogger.OperationType.R )
    @RequestMapping("/searchRsaKey")
    @ResponseBody
    @RequiresPermissions("merchant-RSAKey_query")
    public ResponseDTO<KeyInfo> searchRsaKey(KeyInfo baseModel){
        ResponseDTO<KeyInfo> responseDTO=new ResponseDTO<KeyInfo>();
        try{
            KeyInfo rsaKey=merchantKeyInfoService.searchRsaKey(baseModel);
            responseDTO.setObject(rsaKey);
            responseDTO.setSuccess(true);
        }catch(Exception e){
            log.error("系统异常",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
