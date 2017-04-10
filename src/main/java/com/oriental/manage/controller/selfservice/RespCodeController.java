package com.oriental.manage.controller.selfservice;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.selfservice.RespCode;
import com.oriental.manage.service.selfservice.IRespCodeService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/7/20
 * 询错误码信息操作.
 */
@Controller
@RequestMapping("selfService/respCode")
@Slf4j
public class RespCodeController {

    @Autowired
    private IRespCodeService respCodeService;

    @RequestMapping("init")
    @RequiresPermissions("respCode_search")
    public String init() {
        return "selfservice/searchRespCode";
    }

    @RequiresPermissions("respCode_add")
    @RequestMapping("toAdd")
    public String toAdd() {
        return "selfservice/addRespCode";
    }

    @RequiresPermissions("respCode_update")
    @RequestMapping("toUpdate")
    public String toUpdate() {
        return "selfservice/updateRespCode";
    }

    @OperateLogger(content = "查询错误码信息", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("respCode_search")
    @ResponseBody
    public ResponseDTO<Pagination<RespCode>> queryPage(Pagination<RespCode> pagination, RespCode respCode) {
        ResponseDTO<Pagination<RespCode>> responseDTO = new ResponseDTO<Pagination<RespCode>>();
        try {
            respCodeService.queryPage(pagination, respCode);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);

        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @RequiresPermissions({"respCode_add"})
    @OperateLogger(content = "新增错误码信息", operationType = OperateLogger.OperationType.C, tables = "T_RESP_CODE")
    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(RespCode respCode) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            respCode.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            respCodeService.addRespCode(responseDTO, respCode);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequiresPermissions({"respCode_update"})
    @OperateLogger(content = "修改错误码信息", operationType = OperateLogger.OperationType.U, tables = "T_RESP_CODE")
    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update(RespCode respCode) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            respCodeService.updateRespCode(responseDTO, respCode);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @RequiresPermissions("respCode_delete")
    @OperateLogger(content = "删除错误码信息", operationType = OperateLogger.OperationType.U, tables = "T_RESP_CODE")
    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(RespCode respCode) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            respCodeService.deleteRespCode(responseDTO, respCode);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

}
