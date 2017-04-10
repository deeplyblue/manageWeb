package com.oriental.manage.controller.system;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.base.OperateRecord;
import com.oriental.manage.service.base.IOperateRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoxin on 2016/10/20.
 */
@Slf4j
@Controller
@RequestMapping("system/operationQuery")
public class OperationQueryController {

    @Autowired
    IOperateRecordService operateRecordService;

    @RequestMapping("init")
    public String init() {
        return "system/operationQuery";
    }

    @OperateLogger(content = "查询操作", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<OperateRecord>> queryPage(Pagination<OperateRecord> pagination, OperateRecord operateRecord) {
        ResponseDTO<Pagination<OperateRecord>> responseDTO = new ResponseDTO<Pagination<OperateRecord>>();
        try {
            operateRecordService.selectByOperateRecord(pagination, operateRecord);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
