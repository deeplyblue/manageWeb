package com.oriental.manage.controller.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.institution.PayChanStrategyCfg;
import com.oriental.manage.service.institution.IPayChanStrategyCfgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangjun on 2016/6/14.
 * 支付策略
 */
@Slf4j
@Controller
@RequestMapping("institution/payChanStrategyCfg")
public class PayChanStrategyCfgController {

    @Autowired
    private IPayChanStrategyCfgService payChanStrategyCfgService;

    @RequestMapping("init")
    public String init() {
        return "institution/searchPayChanStrategyCfg";
    }

    @OperateLogger(content = "查询支付策略", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("payChanStrategyCfg_select")
    public
    @ResponseBody
    ResponseDTO<Pagination<PayChanStrategyCfg>> queryPage(Pagination<PayChanStrategyCfg> pagination, PayChanStrategyCfg payChanStrategyCfg) {
        ResponseDTO<Pagination<PayChanStrategyCfg>> responseDTO = new ResponseDTO<Pagination<PayChanStrategyCfg>>();
        try {
            payChanStrategyCfgService.queryPage(pagination, payChanStrategyCfg);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "编辑支付策略", operationType = OperateLogger.OperationType.U, tables = "T_PAY_CHAN_STRATEGY_CFG")
    @RequestMapping("edit")
    @ResponseBody
    @RequiresPermissions(value = {"pay-strategy_add", "pay-strategy_update"}, logical = Logical.OR)
    public ResponseDTO editPayChanStrategyCfg(@RequestBody List<PayChanStrategyCfg> list) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            payChanStrategyCfgService.PayChanStrategyCfg(responseDTO, list);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("编辑支付策略失败", e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }
}
