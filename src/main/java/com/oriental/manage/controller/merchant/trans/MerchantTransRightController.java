package com.oriental.manage.controller.merchant.trans;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.institution.TransRight;
import com.oriental.manage.service.merchant.trans.IMerchantTransRightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: Yang xp
 * Date: 2016/5/24
 * Time: 18:05
 * Desc：商户交易权限配置
 */
@Controller
@RequestMapping("/merchant/trans/right")
@Slf4j
public class MerchantTransRightController  {


    @Autowired
    private IMerchantTransRightService merchantTransRightService;

    @RequestMapping("/init")
    public String init() {
        return "merchant/trans/searchMerchantTransRight";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-trans-right_update")
    public String toUpdate() {
        return "merchant/trans/updateMerchantTransRight";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-trans-right_add")
    public String toAdd() {
        return "merchant/trans/addMerchantTransRight";
    }

    @OperateLogger(content = "新增商户交易权限配置",operationType = OperateLogger.OperationType.C,tables = "T_TRANS_RIGHT")
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("merchant-trans-right_add")
    public ResponseDTO<String> add( TransRight baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("新增信息:{}",baseModel);
        try {
            List<TransRight> transRightList = changeTransRightsByGson(baseModel);
            merchantTransRightService.addMerchantTransRight(transRightList, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改商户交易权限配置",operationType = OperateLogger.OperationType.U,tables = "T_TRANS_RIGHT")
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("merchant-trans-right_update")
    public ResponseDTO<String> update( TransRight baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("修改信息:{}",baseModel);
        try {
            List<TransRight> transRightList = changeTransRightsByGson(baseModel);
            merchantTransRightService.updateMerchantTransRight(transRightList);
            responseDTO.setSuccess(true);
            responseDTO.setObject("修改成功");
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;

    }

    @OperateLogger(content = "查询商户交易权限配置",operationType = OperateLogger.OperationType.R,tables = "T_TRANS_RIGHT")
    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("merchant-trans-right_search")
    public ResponseDTO<Pagination<TransRight>> search(Pagination<TransRight> pagination, TransRight baseModel) {

        ResponseDTO<Pagination<TransRight>> responseDTO=new ResponseDTO<Pagination<TransRight>>();
        log.info("查询信息:{},{}",baseModel,pagination);
        try{
            merchantTransRightService.searchMerchantTransRight(pagination, baseModel);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;

    }

    @OperateLogger(content = "删除商户交易权限配置",operationType = OperateLogger.OperationType.D,tables = "T_TRANS_RIGHT")
    @RequestMapping("/delete")
    @RequiresPermissions("merchant-trans-right_delete")
    @ResponseBody
    public ResponseDTO<String> delete(TransRight baseModel){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        try {
            merchantTransRightService.deleteMerchantTransRight(baseModel,responseDTO);
        }catch (Exception e){
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }


    @RequestMapping("/initResource")
    @ResponseBody
    @RequiresPermissions(value = {"merchant-trans-right_update","merchant-trans-right_delete","merchant-trans-right_add"},logical= Logical.OR)
    public ResponseDTO<List<Map<String, String>>> initResource(String merchantCode,String companyType) {
        ResponseDTO<List<Map<String, String>>> responseDTO = new ResponseDTO<List<Map<String, String>>>();
        try {
            responseDTO.setObject(merchantTransRightService.initResource(merchantCode,companyType));
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("系统异常",e);
        }
        return responseDTO;
    }

    private List<TransRight> changeTransRightsByGson(TransRight baseModel) {
        log.info("商户权限菜单入参：{}", baseModel);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TransRight>>() {
        }.getType();
        List<TransRight> transRightList = gson.fromJson(baseModel.getTempParams(), type);
        log.info("商户权限菜单入参转换:{}", transRightList);
        //去掉父节点
        for (Iterator<TransRight> transRight = transRightList.iterator(); transRight.hasNext(); ) {
            TransRight tr = transRight.next();
            if (StringUtils.isBlank(tr.getConnChannel()) || StringUtils.isBlank(tr.getTransCode())) {
                transRight.remove();
            }
        }
        log.info("商户权限菜单入参，去掉父节点后:{}", transRightList);
        return transRightList;
    }
}
