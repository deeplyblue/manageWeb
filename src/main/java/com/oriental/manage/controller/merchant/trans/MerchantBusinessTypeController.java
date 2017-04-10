package com.oriental.manage.controller.merchant.trans;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.merchant.trans.MerchantBusinessType;
import com.oriental.manage.service.merchant.trans.IMerchantBusinessTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: Yang xp
 * Date: 2016/5/24
 * Time: 10:32
 * Desc：商户业务类型
 */
@Controller
@RequestMapping("/merchant/business/type")
@Slf4j
public class MerchantBusinessTypeController   {

    @Autowired
    private IMerchantBusinessTypeService merchantBusinessTypeService;

    @RequestMapping("/init")
    public String init(){
        return "merchant/trans/searchMerchantBusinessType";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-business-type_add")
    public String toAdd(){
        return "merchant/trans/addMerchantBusinessType";
    }

    @OperateLogger(content = "新增商户业务资金源配置",operationType = OperateLogger.OperationType.C,tables = "T_MCHNT_BUSITYPE")
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("merchant-business-type_add")
    public  ResponseDTO<String>  add( MerchantBusinessType baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            baseModel.setCreateUser(SessionUtils.getUserName());
            log.info("商户业务资金源配置:{}",baseModel);
            merchantBusinessTypeService.addMerchantBusinessType(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改商户业务资金源配置",operationType = OperateLogger.OperationType.U,tables = "T_MCHNT_BUSITYPE")
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions(value = {"merchant-business-type_open","merchant-business-type_close"},logical= Logical.OR)
    public ResponseDTO<String> update(MerchantBusinessType baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            log.info("修改商户业务资金源信息:{}",baseModel);
            baseModel.setModifierUser(SessionUtils.getUserName());
            merchantBusinessTypeService.updateMerchantBusinessType(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;

    }

    @OperateLogger(content = "查询商户业务资金源配置",operationType = OperateLogger.OperationType.R,tables = "T_MCHNT_BUSITYPE")
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("merchant-business-type_search")
    public ResponseDTO<Pagination<MerchantBusinessType>> search(Pagination<MerchantBusinessType> pagination, MerchantBusinessType baseModel) {

        ResponseDTO<Pagination<MerchantBusinessType>> responseDTO=new ResponseDTO<Pagination<MerchantBusinessType>>();
        log.info("查询信息:{},{}",baseModel,pagination);
        try{
            merchantBusinessTypeService.queryPage(pagination,baseModel);
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
