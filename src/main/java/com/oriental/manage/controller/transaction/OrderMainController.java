package com.oriental.manage.controller.transaction;

import com.oriental.manage.core.bigDataDownloadThread.DownloadOrderMainThread;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.transaction.OrderMain;
import com.oriental.manage.service.transaction.IOrderMainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * User: hj
 * Date: 2016/6/13 20:15
 * Desc:
 */
@Slf4j
@Controller
@RequestMapping("orderMain")
public class OrderMainController {

    @Autowired
    private IOrderMainService orderMainService;

    @RequestMapping("init")
    public String init() {
        return "transaction/queryOrderMainList";
    }

    @OperateLogger(content = "主订单信息查询", operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryOrderMainList")
    @RequiresPermissions("orderMain_search")
    @ResponseBody
    public ResponseDTO<Pagination<OrderMain>> queryPage(Pagination<OrderMain> pagination, OrderMain orderMain) {
        ResponseDTO<Pagination<OrderMain>> responseDTO = new ResponseDTO<Pagination<OrderMain>>();
        try {
            orderMainService.queryPage(pagination, orderMain);
            Map map = orderMainService.summaryOrderMain(orderMain);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
            responseDTO.setSumObject(map);
        } catch (Exception e) {
            log.error("查询主单信息失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @RequestMapping("toQueryDetail")
    public String toQueryDetail() {
        return "transaction/queryOrderMainDetail";
    }

    @OperateLogger(content = "主订单明细查询", operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryOrderMainDetail")
    @ResponseBody
    public ResponseDTO<OrderMain> queryOrderMainDetail(OrderMain orderMain) {
        ResponseDTO<OrderMain> responseDTO = new ResponseDTO<OrderMain>();
        try {
            OrderMain resultOrderMain = orderMainService.queryOrderMainDetail(orderMain);
            responseDTO.setSuccess(true);
            responseDTO.setObject(resultOrderMain);
        } catch (Exception e) {
            log.error("查询主单信息失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @RequiresPermissions("orderMain_download")
    @RequestMapping("download")
    @ResponseBody
    public ResponseDTO<OrderMain> download(OrderMain orderMain) {
        ResponseDTO<OrderMain> responseDTO = new ResponseDTO<OrderMain>();

        try {

            Constants.THREAD_POOL.execute(new DownloadOrderMainThread(orderMain));
            responseDTO.setSuccess(true);

        } catch (Exception e) {
            log.error("下载失败", e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }
}