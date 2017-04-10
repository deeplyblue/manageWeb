package com.oriental.manage.controller.merchant.itconfig;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.merchant.itconfig.ClearPoint;
import com.oriental.manage.service.merchant.itconfig.IMerchantClearPointService;
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
 * Time: 12:31
 * Desc：结算点配置
 */
@RequestMapping("/merchant/itCfg/point")
@Controller
@Slf4j
public class MerchantClearPointController  {

    @Autowired
    private IMerchantClearPointService merchantClearPointService;

    @RequestMapping("/init")
    public String init(){
        return "merchant/itconfig/searchMerchantClearPoint";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-itCfg_add")
    public String toAdd(){
        return "merchant/itconfig/addMerchantClearPoint";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-itCfg_update")
    public String toUpdate(){
        return "merchant/itconfig/updateMerchantClearPoint";
    }

    @OperateLogger(content = "删除结算点配置",operationType = OperateLogger.OperationType.D,tables = "T_CLR_POINT_CFG" )
    @RequestMapping("/delete")
    @RequiresPermissions("merchant-itCfg_delete")
    @ResponseBody
    public ResponseDTO<String> delete(ClearPoint clearPoint){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("删除结算点配置:{}",clearPoint);
        try {
            merchantClearPointService.delete(clearPoint,responseDTO);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "新增结算点配置",operationType = OperateLogger.OperationType.C,tables = "T_CLR_POINT_CFG" )
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("merchant-itCfg_add")
    public ResponseDTO<String> add( ClearPoint baseModel) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("新增信息:{}",baseModel);
        try {
            merchantClearPointService.add(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改结算点配置",operationType = OperateLogger.OperationType.U,tables = "T_CLR_POINT_CFG" )
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("merchant-itCfg_update")
    public ResponseDTO<String> update( ClearPoint baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("修改信息:{}",baseModel);
        try {
            merchantClearPointService.update(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "查询结算点配置",operationType = OperateLogger.OperationType.R )
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("merchant-itCfg_search")
    public ResponseDTO<Pagination<ClearPoint>> search(Pagination<ClearPoint> pagination, ClearPoint baseModel) {
        ResponseDTO<Pagination<ClearPoint>> responseDTO=new ResponseDTO<Pagination<ClearPoint>>();
        log.info("查询信息:{},{}",baseModel,pagination);
        try{
            merchantClearPointService.search(pagination,baseModel);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }
    @RequestMapping("/check")
    @ResponseBody
    public boolean check(String itemKey,String itemValue,String id){
        ClearPoint clearPoint =new ClearPoint();

        if(null!=itemKey && !"".equals(itemKey)){
            clearPoint.setItemKey(itemKey);
        }
        if(null!=itemValue && !"".equals(itemValue)){
            clearPoint.setItemValue(itemValue);
        }
        if(merchantClearPointService.check(clearPoint,id)){
                 return false;
        }else {
               return  true;
        }
    }
}
