package com.oriental.manage.controller.check;

import com.oriental.check.service.facade.manager.ChkFileDataCfgFacade;
import com.oriental.check.service.facade.manager.model.ChkFileDataCfgDTO;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.paycenter.commons.mode.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <ul>
 * <li>对账文件数据配置控制器</li>
 * <li>User:蒯越 Date:2016/5/17 Time:10:46</li>
 * </ul>
 */
@Slf4j
@Controller
public class ChkFileDataCfgController {

    @Autowired
    private ChkFileDataCfgFacade chkFileDataCfgFacade;

    @RequestMapping("/check/chkFileDataCfg/init")
    public String init() {
        return "check/searchChkFileDataCfg";
    }

    @OperateLogger(content = "对账文件模板配置查询",operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequestMapping("/check/chkFileDataCfg/search")
    @RequiresPermissions("merchant-chkFileDataCfg_search")
    public ResponseDTO<Pagination<ChkFileDataCfgDTO>> queryPage(Pagination<ChkFileDataCfgDTO> pagination, ChkFileDataCfgDTO query) {
        ResponseDTO<Pagination<ChkFileDataCfgDTO>> responseDTO = new ResponseDTO<Pagination<ChkFileDataCfgDTO>>();
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            Response<com.oriental.check.service.facade.model.Pagination<ChkFileDataCfgDTO>> response
                    = chkFileDataCfgFacade.pageQuery(query, SessionUtils.getUserName());
            if (response.isSuccess()) {
                pagination.setRowCount(response.getResult().getTotalCount());
                pagination.setList(response.getResult().getPageList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("/check/chkFileDataCfg/toAdd")
    public String toAdd() {
        return "check/addChkFileDataCfg";
    }

    @OperateLogger(content = "新增对账文件模板",operationType = OperateLogger.OperationType.C)
    @ResponseBody
    @RequestMapping("/check/chkFileDataCfg/add")
    @RequiresPermissions("merchant-chkFileDataCfg_add")
    public ResponseDTO<String> add(@RequestBody ChkFileDataCfgDTO chkFileDataCfgDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            Response<String> response = chkFileDataCfgFacade.insert(chkFileDataCfgDTO, SessionUtils.getUserName());
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("新增成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("/check/chkFileDataCfg/toUpdate")
    public String toUpdate() {
        return "check/updateChkFileDataCfg";
    }

    @OperateLogger(content = "修改对账文件模板",operationType = OperateLogger.OperationType.U)
    @ResponseBody
    @RequestMapping("/check/chkFileDataCfg/update")
    @RequiresPermissions("merchant-chkFileDataCfg_update")
    public ResponseDTO<String> update(@RequestBody ChkFileDataCfgDTO chkFileDataCfgDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            Response<String> response = chkFileDataCfgFacade.update(chkFileDataCfgDTO, SessionUtils.getUserName());
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("修改成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }
}
