package com.oriental.manage.controller.mchntplatform.busitrans;

import com.oriental.check.commons.enums.Status;
import com.oriental.check.commons.util.DateUtil;
import com.oriental.check.service.facade.manager.ChkFileInfoFacade;
import com.oriental.check.service.facade.manager.model.ChkFileInfoDTO;
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
 * Created by wangjun on 2016/8/11.
 */
@Slf4j
@Controller
@RequestMapping("mchntplatform/chkFileInfo")
public class MchntChkFileInfoController {

    @Autowired
    private ChkFileInfoFacade chkFileInfoFacade;

    @RequestMapping("init")
    @RequiresPermissions("mchntplatform-chkFileInfo_search")
    public String init(){return "mchntplatform/searchMchntChkFileInfo";}

    @OperateLogger(content = "商户对账文件信息查询",operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequestMapping("search")
    @RequiresPermissions("mchntplatform-chkFileInfo_search")
    public ResponseDTO<Pagination<ChkFileInfoDTO>> queryPage(Pagination<ChkFileInfoDTO> pagination,
                                                             ChkFileInfoDTO query,
                                                             String sSettleDateStart,
                                                             String sSettleDateEnd) {
        ResponseDTO<Pagination<ChkFileInfoDTO>> responseDTO = new ResponseDTO<Pagination<ChkFileInfoDTO>>();
        try {
            query.setCompanyCode(SessionUtils.getMchntCode());
            query.setStatus(Status.CHK_FILE_INFO_MCHNT_CREATE_SUCCESS.getCode());
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sSettleDateStart));
            query.setDateEnd(DateUtil.parse(sSettleDateEnd));
            Response<com.oriental.check.service.facade.model.Pagination<ChkFileInfoDTO>> response
                    = chkFileInfoFacade.pageQuery(query, SessionUtils.getUserName());
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
}
