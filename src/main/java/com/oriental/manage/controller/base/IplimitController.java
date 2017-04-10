package com.oriental.manage.controller.base;

import com.oriental.manage.core.system.log.OperateLogger;

import com.oriental.manage.pojo.base.IplimitCfg;
import com.oriental.manage.service.base.IpLimitCfgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;




/**
 * Created by luoxin on 2016/11/1.
 */
@Slf4j
@Controller
@RequestMapping("system/userIp")
public class IplimitController {
    @Autowired
    private IpLimitCfgService ipLimitCfgService;

    @RequestMapping("/init")
    @RequiresPermissions("userIp_select")
    public String init(){
        return "system/searchIplimit";
    }


    @OperateLogger(content = "查询用户IP白名单",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("userIp_select")
    public ResponseDTO<Pagination<IplimitCfg>> queryPage(IplimitCfg iplimitCfg , Pagination<IplimitCfg> pagination){
        ResponseDTO<Pagination<IplimitCfg>> responseDTO=new ResponseDTO<Pagination<IplimitCfg>>();
        try{
            ipLimitCfgService.queryPage(pagination,iplimitCfg);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);

        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }
    @OperateLogger(content = "删除用户IP白名单",operationType = OperateLogger.OperationType.D,tables = "t_iplimit_cfg")
    @RequestMapping("delete")
    @RequiresPermissions("userIp_delete")
    @ResponseBody
    public ResponseDTO<String> delete(IplimitCfg iplimitCfg) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            ipLimitCfgService.deleteIplimitCfg(iplimitCfg, responseDTO);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("userIp_add")
    public String toAdd() {
        return "system/addIplimit";
    }

    @OperateLogger(content = "新增用户",operationType = OperateLogger.OperationType.C,tables = "t_iplimit_cfg")
    @RequestMapping("/add")
    @RequiresPermissions("userIp_add")
    @ResponseBody
    public ResponseDTO<String> add(IplimitCfg iplimitCfg ){

        ResponseDTO<String> responseDTO=new ResponseDTO<>();
        try{
            boolean flag = ipLimitCfgService.selectByUser(iplimitCfg);
            if(flag){
                log.error("存在启用的记录");
                responseDTO.setSuccess(false);
                responseDTO.setMsg("该用户已存在可用记录");
            }else {
                ipLimitCfgService.addNewIplimit(iplimitCfg, responseDTO);
                responseDTO.setSuccess(true);
            }
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }
}
