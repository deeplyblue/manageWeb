package com.oriental.manage.controller.merchant.itconfig;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.merchant.itconfig.MerchantInterface;
import com.oriental.manage.service.merchant.itconfig.IMerchantInterfaceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Author: Yang xp
 * Date: 2016/5/30
 * Time: 12:55
 * Desc：商户支付接口配置
 */
@Controller
@Slf4j
@RequestMapping("/merchant/itCfg/interface")
public class MerchantPayCfgController  {


    @Autowired
    private IMerchantInterfaceService merchantInterfaceService;

    @RequestMapping("/init")
    public String init(){
        return "merchant/itconfig/searchMerchantPayInterface";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-interface_add")
    public String toAdd(){
        return "merchant/itconfig/addMerchantPayInterface";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-interface_update")
    public String toUpdate(){
        return "merchant/itconfig/updateMerchantPayInterface";
    }

    @OperateLogger(content = "新增商户支付接口配置",operationType = OperateLogger.OperationType.C,tables = "T_MCHNT_INTERFACE" )
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("merchant-interface_add")
    public ResponseDTO<String> add( MerchantInterface baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            log.info("新增信息:{}",baseModel);
            merchantInterfaceService.add(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;

    }

    @OperateLogger(content = "修改商户支付接口配置",operationType = OperateLogger.OperationType.U,tables = "T_MCHNT_INTERFACE" )
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("merchant-interface_update")
    public ResponseDTO<String> update(MerchantInterface baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            log.info("修改信息:{}",baseModel);
            merchantInterfaceService.update(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "查询商户支付接口配置",operationType = OperateLogger.OperationType.R )
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("merchant-interface_search")
    public  ResponseDTO<Pagination<MerchantInterface>> search(Pagination<MerchantInterface> pagination, MerchantInterface baseModel) {
        ResponseDTO<Pagination<MerchantInterface>> responseDTO=new ResponseDTO<Pagination<MerchantInterface>>();
        try{
            log.info("查询信息:{},{}",baseModel,pagination);
            merchantInterfaceService.search(pagination,baseModel);
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
