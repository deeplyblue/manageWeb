package com.oriental.manage.controller.check;

import com.oriental.check.commons.enums.CompanyType;
import com.oriental.check.service.facade.manager.ChkCfgFacade;
import com.oriental.check.service.facade.manager.model.ChkCfgDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <ul>
 * <li>支付机构对账配置控制器</li>
 * <li>User:蒯越 Date:2016/5/6 Time:16:48</li>
 * </ul>
 */
@Slf4j
@Controller
public class PayOrgChkCfgController {

    @Autowired
    private ChkCfgFacade chkCfgFacade;

    @RequestMapping("/check/payOrgChkCfg/init")
    public String init() {
        return "check/searchPayOrgChkCfg";
    }

    @OperateLogger(content = "支付机构对账配置查询",operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequestMapping("/check/payOrgChkCfg/search")
    @RequiresPermissions("payOrgChkCfg_select")
    public ResponseDTO<Pagination<ChkCfgDTO>> queryPage(Pagination<ChkCfgDTO> pagination, ChkCfgDTO query) {
        ResponseDTO<Pagination<ChkCfgDTO>> responseDTO = new ResponseDTO<Pagination<ChkCfgDTO>>();
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setCompanyType(CompanyType.PAY_ORG.getCode());
            Response<com.oriental.check.service.facade.model.Pagination<ChkCfgDTO>> response
                    = chkCfgFacade.pageQuery(query, SessionUtils.getUserName());
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

    /**
     * 跳转至新增页面
     *
     * @return String
     */
    @RequestMapping("/check/payOrgChkCfg/toAdd")
    @RequiresPermissions("payOrgChkCfg_add")
    public String toAdd() {
        return "check/addPayOrgChkCfg";
    }

    @OperateLogger(content = "新增对账配置",operationType = OperateLogger.OperationType.C)
    @ResponseBody
    @RequestMapping("/check/payOrgChkCfg/add")
    @RequiresPermissions("payOrgChkCfg_add")
    public ResponseDTO<String> add(ChkCfgDTO chkCfgDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            // TODO 需要校验支付机构是否存在

            chkCfgDTO.setCompanyType(CompanyType.PAY_ORG.getCode());
            Response<String> response = chkCfgFacade.insert(chkCfgDTO, SessionUtils.getUserName());
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

    @RequestMapping("/check/payOrgChkCfg/toUpdate")
    @RequiresPermissions("payOrgChkCfg_update")
    public String toUpdate() {
        return "check/updatePayOrgChkCfg";
    }

    @OperateLogger(content = "修改对账配置",operationType = OperateLogger.OperationType.U)
    @ResponseBody
    @RequestMapping("/check/payOrgChkCfg/update")
    @RequiresPermissions("payOrgChkCfg_update")
    public ResponseDTO<String> update(ChkCfgDTO chkCfgDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            chkCfgDTO.setCompanyType(CompanyType.PAY_ORG.getCode());
            Response<String> response = chkCfgFacade.update(chkCfgDTO, SessionUtils.getUserName());
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

    @OperateLogger(content = "修改状态开通",operationType = OperateLogger.OperationType.U)
    @ResponseBody
    @RequestMapping("/check/payOrgChkCfg/open")
    @RequiresPermissions("payOrgChkCfg_open")
    public ResponseDTO<String> open(String id) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = chkCfgFacade.open(id, SessionUtils.getUserName());
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("开通成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("开通失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("开通失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改状态关闭",operationType = OperateLogger.OperationType.U)
    @ResponseBody
    @RequestMapping("/check/payOrgChkCfg/close")
    @RequiresPermissions("payOrgChkCfg_close")
    public ResponseDTO<String> close(String id) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = chkCfgFacade.close(id, SessionUtils.getUserName());
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("关闭成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("关闭失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("关闭失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }
}
