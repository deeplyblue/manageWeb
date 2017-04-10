package com.oriental.manage.controller.check;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <ul>
 * <li>对账文件信息控制器</li>
 * <li>User:蒯越 Date:2016/5/11 Time:10:29</li>
 * </ul>
 */
@Slf4j
@Controller
public class ChkFileInfoController {

    @Autowired
    private ChkFileInfoFacade chkFileInfoFacade;

    @RequestMapping("/check/chkFileInfo/init")
    public String init() {
        return "check/searchChkFileInfo";
    }

    @OperateLogger(content = "对账文件信息查询",operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequestMapping("/check/chkFileInfo/search")
    public ResponseDTO<Pagination<ChkFileInfoDTO>> queryPage(Pagination<ChkFileInfoDTO> pagination,
                                                             ChkFileInfoDTO query,
                                                             String sSettleDateStart,
                                                             String sSettleDateEnd) {
        ResponseDTO<Pagination<ChkFileInfoDTO>> responseDTO = new ResponseDTO<Pagination<ChkFileInfoDTO>>();
        try {
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
