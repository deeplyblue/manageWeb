package com.oriental.manage.controller.check;

import com.oriental.check.commons.enums.BusiPlatformOperateImpls;
import com.oriental.check.commons.enums.CompanyType;
import com.oriental.check.commons.enums.RefundCheckFlag;
import com.oriental.check.service.facade.manager.ChkCfgFacade;
import com.oriental.check.service.facade.manager.model.ChkCfgDTO;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
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
 * <li>商户对账配置控制器</li>
 * <li>User:蒯越 Date:2016/5/17 Time:10:29</li>
 * </ul>
 */
@Slf4j
@Controller
public class MchntChkCfgController {

    @Autowired
    private ChkCfgFacade chkCfgFacade;

    @RequestMapping("/check/mchntChkCfg/init")
    public String init() {
        return "check/searchMchntChkCfg";
    }

    @OperateLogger(content = "商户对账配置查询",operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequestMapping("/check/mchntChkCfg/search")
    @RequiresPermissions("merchant-mchntChkCfg_search")
    public ResponseDTO<Pagination<ChkCfgDTO>> queryPage(Pagination<ChkCfgDTO> pagination, ChkCfgDTO query) {
        ResponseDTO<Pagination<ChkCfgDTO>> responseDTO = new ResponseDTO<Pagination<ChkCfgDTO>>();
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setCompanyType(CompanyType.MCHNT.getCode());
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
    @RequestMapping("/check/mchntChkCfg/toAdd")
    @RequiresPermissions("merchant-mchntChkCfg_add")
    public String toAdd() {
        return "check/addMchntChkCfg";
    }

    @OperateLogger(content = "新增对账配置",operationType = OperateLogger.OperationType.C)
    @ResponseBody
    @RequestMapping("/check/mchntChkCfg/add")
    @RequiresPermissions("merchant-mchntChkCfg_add")
    public ResponseDTO<String> add(ChkCfgDTO chkCfgDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            if (chkCfgDTO.getCompanyCode() == null) {
                throw new BusiException("亲~请填写商户");
            }
            // 校验商户是否存在
            if (Constants.getMerchantMap().get(chkCfgDTO.getCompanyCode()) == null) {
                throw new BusiException("商户不存在-" + chkCfgDTO.getCompanyCode());
            }
            chkCfgDTO.setCompanyType(CompanyType.MCHNT.getCode());
            chkCfgDTO.setRefundCheckFlag(RefundCheckFlag.PARTICIPATE_CHECK.getCode());
            chkCfgDTO.setOperateImplClass(BusiPlatformOperateImpls.ACQUIRING_PLATFORM_OPERATE_INTERFACE.getCode());
            Response<String> response = chkCfgFacade.insert(chkCfgDTO, SessionUtils.getUserName());
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("新增成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("新增失败:{}", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("/check/mchntChkCfg/toUpdate")
    @RequiresPermissions("merchant-mchntChkCfg_update")
    public String toUpdate() {
        return "check/updateMchntChkCfg";
    }

    @OperateLogger(content = "修改对账配置",operationType = OperateLogger.OperationType.U)
    @ResponseBody
    @RequestMapping("/check/mchntChkCfg/update")
    @RequiresPermissions("merchant-mchntChkCfg_update")
    public ResponseDTO<String> update(ChkCfgDTO chkCfgDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            chkCfgDTO.setCompanyType(CompanyType.MCHNT.getCode());
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
    @RequestMapping("/check/mchntChkCfg/open")
    @RequiresPermissions("merchant-mchntChkCfg_open")
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
    @RequestMapping("/check/mchntChkCfg/close")
    @RequiresPermissions("merchant-mchntChkCfg_close")
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
