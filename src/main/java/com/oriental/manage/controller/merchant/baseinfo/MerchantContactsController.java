package com.oriental.manage.controller.merchant.baseinfo;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantContactInfo;
import com.oriental.manage.service.merchant.baseinfo.IMerchantContactsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: Yang xp
 * Date: 2016/5/23
 * Time: 9:51
 * Desc：商户联系人
 */
@Slf4j
@Controller
@RequestMapping("/merchant/contacts")
public class MerchantContactsController  {


    @Autowired
    private IMerchantContactsService merchantContactsService;


    @RequestMapping("/init")
    public String init(){
        return "merchant/baseinfo/searchMerchantContacts";
    }


    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-contacts_add")
    public String toAdd(){
        return "merchant/baseinfo/addMerchantContacts";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-contacts_update")
    public String toUpdate(){
        return "merchant/baseinfo/updateMerchantContacts";
    }

    @OperateLogger(content = "新增商户联系人",operationType = OperateLogger.OperationType.C,tables = "T_CONTACT_INFO")
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("merchant-contacts_add")
    public ResponseDTO<String> add( MerchantContactInfo baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("新增信息:{}",baseModel);
        try {
            merchantContactsService.createMerchantContacts(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改商户联系人",operationType = OperateLogger.OperationType.U,tables = "T_CONTACT_INFO")
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("merchant-contacts_update")
    public ResponseDTO<String> update( MerchantContactInfo baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("修改信息:{}",baseModel);
        try {
            merchantContactsService.updateMerchantContacts(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "商户联系人查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("merchant-contacts_search")
    public ResponseDTO<Pagination<MerchantContactInfo>>  search(Pagination<MerchantContactInfo> pagination, MerchantContactInfo baseModel) {
        ResponseDTO<Pagination<MerchantContactInfo>> responseDTO=new ResponseDTO<Pagination<MerchantContactInfo>>();
        log.info("查询信息:{},{}",baseModel,pagination);
        try{
            merchantContactsService.searchMerchantContacts(pagination,baseModel);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改商户联系人状态",operationType = OperateLogger.OperationType.U,tables = "T_CONTACT_INFO")
    @RequestMapping("/updateItemEnableFlag")
    @ResponseBody
    @RequiresPermissions("merchant-contacts_open")
    public ResponseDTO<String> updateItemEnableFlag(@RequestBody MerchantContactInfo merchantContactInfo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            merchantContactsService.updateMerchantContacts(merchantContactInfo,responseDTO);
        }catch (Exception e){
            log.error("修改状态错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改状态错误");
        }
        return responseDTO;
    }
}
