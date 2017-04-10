package com.oriental.manage.controller.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.institution.BusitypeOrgRelationship;
import com.oriental.manage.service.institution.IBusitypeOrgRelationshipService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by wangjun on 2016/6/24.
 * 业务资金源支付机构银行强绑关系
 */
@Slf4j
@Controller
@RequestMapping("institution/busitypeOrgRelationship")
public class BusitypeOrgRelationshipController {

    @Autowired
    private IBusitypeOrgRelationshipService busitypeOrgRelationshipService;

    @RequestMapping("init")
    public String init(){  return "institution/searchBusitypeOrgRelationship";}

    @RequestMapping("toAdd")
    @RequiresPermissions("busitypeOrgRelationship_add")
    public String toAdd(){ return "institution/addBusitypeOrgRelationship";}

    @RequestMapping("toUpdate")
    @RequiresPermissions("busitypeOrgRelationship_update")
    public String toUpdate(){ return "institution/updateBusitypeOrgRelationship";}

    @OperateLogger(content = "查询业务资金源支付机构银行强绑关系",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("busitypeOrgRelationship_select")
    public @ResponseBody
    ResponseDTO<Pagination<BusitypeOrgRelationship>> queryPage(Pagination<BusitypeOrgRelationship> pagination, BusitypeOrgRelationship busitypeOrgRelationship){
        ResponseDTO<Pagination<BusitypeOrgRelationship>> responseDTO = new ResponseDTO<Pagination<BusitypeOrgRelationship>>();
        try {
            busitypeOrgRelationshipService.queryPage(pagination,busitypeOrgRelationship);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "新增业务资金源支付机构银行强绑关系",operationType = OperateLogger.OperationType.C,tables = "T_BUSITYPE_ORG_RELATIONSHIP")
    @RequestMapping("add")
    @RequiresPermissions("busitypeOrgRelationship_add")
    @ResponseBody
    public ResponseDTO<String> add(BusitypeOrgRelationship busitypeOrgRelationship){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            if(busitypeOrgRelationshipService.check(busitypeOrgRelationship)>0){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("已经配置过该强绑关系");
            }else{
                busitypeOrgRelationship.setCreateTime(new Date());
                busitypeOrgRelationship.setLastUpdTime(new Date());
                busitypeOrgRelationshipService.addBusiTypeOrgRelationship(responseDTO,busitypeOrgRelationship);
            }
        }catch(Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改业务资金源支付机构银行强绑关系",operationType = OperateLogger.OperationType.U,tables = "T_BUSITYPE_ORG_RELATIONSHIP")
    @RequestMapping("update")
    @RequiresPermissions("busitypeOrgRelationship_update")
    @ResponseBody
    public ResponseDTO<String> update(BusitypeOrgRelationship busitypeOrgRelationship){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            busitypeOrgRelationshipService.updateBusiTypeOrgRelationship(responseDTO,busitypeOrgRelationship);
        }catch(Exception e){
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return  responseDTO;
    }
    @OperateLogger(content = "删除业务资金源支付机构银行强绑关系",operationType = OperateLogger.OperationType.D,tables = "T_BUSITYPE_ORG_RELATIONSHIP")
    @RequestMapping("delete")
    @RequiresPermissions("busitypeOrgRelationship_delete")
    @ResponseBody
    public ResponseDTO<String> delete(BusitypeOrgRelationship busitypeOrgRelationship){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            busitypeOrgRelationshipService.deleteBusiTypeOrgRelationship(responseDTO,busitypeOrgRelationship);
        }catch(Exception e){
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return  responseDTO;
    }



}
