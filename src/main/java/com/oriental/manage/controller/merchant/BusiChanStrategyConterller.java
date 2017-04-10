package com.oriental.manage.controller.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.business.BusiChanStrategyCfg;
import com.oriental.manage.service.business.IBusiChanStrategyService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangjun on 2016/5/25.
 * 业务机构路由配置信息
 */
@Slf4j
@Controller
@RequestMapping("business/busiChanStrategy")
public class BusiChanStrategyConterller {

    @Autowired
    private IBusiChanStrategyService busiChanStrategyService;

    @RequestMapping("init")
    public String init() {
        return "business/searchBusiChanStrategy";
    }

    @OperateLogger(content = "查询业务机构路由配置信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<BusiChanStrategyCfg>> queryPage(BusiChanStrategyCfg busiChanStrategyCfg, Pagination<BusiChanStrategyCfg> pagination) {
        ResponseDTO<Pagination<BusiChanStrategyCfg>> responseDTO = new ResponseDTO<Pagination<BusiChanStrategyCfg>>();
        try {
            busiChanStrategyService.queryPage(pagination, busiChanStrategyCfg);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("toAdd")
    public String toAdd() {
        return "business/addBusiChanStrategy";
    }

    @OperateLogger(content = "新增业务机构路由配置信息",operationType = OperateLogger.OperationType.C,tables = "T_BUSI_CHAN_STRATEGY_CFG")
    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(BusiChanStrategyCfg busiChanStrategyCfg) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            busiChanStrategyCfg.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            busiChanStrategyCfg.setTransCode("0011000");
            busiChanStrategyCfg.setModifier(SessionUtils.getUserName());
            busiChanStrategyCfg.setCreator(SessionUtils.getUserName());
            busiChanStrategyService.addBusiChanStrategyService(responseDTO, busiChanStrategyCfg);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("toUpdate")
    public String toUpdate(){
        return  "business/updateBusiChanStrategy";
    }

    @OperateLogger(content = "修改业务机构路由配置信息",operationType = OperateLogger.OperationType.U,tables = "T_BUSI_CHAN_STRATEGY_CFG")
    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update(BusiChanStrategyCfg busiChanStrategyCfg) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            busiChanStrategyCfg.setModifier(SessionUtils.getUserName());
            busiChanStrategyService.updateBusiChanStrategy(responseDTO, busiChanStrategyCfg);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "删除业务机构路由配置信息",operationType = OperateLogger.OperationType.D,tables = "T_BUSI_CHAN_STRATEGY_CFG")
    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(BusiChanStrategyCfg busiChanStrategyCfg) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            busiChanStrategyCfg.setModifier(SessionUtils.getUserName());
            busiChanStrategyService.deleteBusiChanStrategy(responseDTO, busiChanStrategyCfg);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
