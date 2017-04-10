package com.oriental.manage.controller.check;

import com.oriental.check.commons.util.DateUtil;
import com.oriental.check.service.facade.manager.CountBankChkCompleteFacade;
import com.oriental.check.service.facade.manager.RollBackChkDataFacade;
import com.oriental.check.service.facade.redo.StartCheckFacade;
import com.oriental.check.service.facade.redo.StartChkFileCreateFacade;
import com.oriental.check.service.facade.redo.StartDownloadFacade;
import com.oriental.check.service.facade.redo.StartForceCheckFacade;
import com.oriental.check.service.facade.redo.StartInnerCheckFacade;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.paycenter.commons.mode.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <ul>
 * <li>重跑对账控制器</li>
 * <li>User:蒯越 Date:2016/5/10 Time:16:05</li>
 * </ul>
 */
@Slf4j
@Controller
public class RedoCheckController {

    @Autowired
    private RollBackChkDataFacade rollBackChkDataFacade;

    @Autowired
    private StartDownloadFacade startDownloadFacade;

    @Autowired
    private StartCheckFacade startCheckFacade;

    @Autowired
    private StartForceCheckFacade startForceCheckFacade;

    @Autowired
    private StartInnerCheckFacade startInnerCheckFacade;

    @Autowired
    private StartChkFileCreateFacade startChkFileCreateFacade;

    @Autowired
    private CountBankChkCompleteFacade countBankChkCompleteFacade;

    @RequestMapping("/check/redoCheck/init")
    public String init() {
        return "check/redoCheck";
    }

    @ResponseBody
    @RequestMapping("/check/redoCheck/rollBackDownloadAndChkData")
    public ResponseDTO<String> rollBackDownloadAndChkData(String orgCode, String settleDate) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = rollBackChkDataFacade.rollBackDownloadAndChkData(orgCode, DateUtil.parse(settleDate), SessionUtils.getUserName());
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("回滚成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("回滚失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/check/redoCheck/rollBackChkData")
    public ResponseDTO<String> rollBack(String orgCode, String settleDate) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = rollBackChkDataFacade.rollBackChkData(orgCode, DateUtil.parse(settleDate), SessionUtils.getUserName());
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("回滚成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("回滚失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/check/redoCheck/startDownload")
    public ResponseDTO<String> startDownload(String orgCode, String settleDate) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = startDownloadFacade.startDownload(orgCode, DateUtil.parse(settleDate));
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("启动下载成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("启动下载失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/check/redoCheck/startCheck")
    public ResponseDTO<String> startCheck(String orgCode, String settleDate) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = startCheckFacade.startCheck(orgCode, DateUtil.parse(settleDate));
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("对账成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("启动对账失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/check/redoCheck/startForceCheck")
    public ResponseDTO<String> startForceCheck(String orgCode, String settleDate) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = startForceCheckFacade.startForceCheck(orgCode, DateUtil.parse(settleDate));
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("强制对账成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("启动强制对账失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/check/redoCheck/startInnerCheck")
    public ResponseDTO<String> startInnerCheck(String orgCode, String settleDate) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = startInnerCheckFacade.startInnerCheck(orgCode, DateUtil.parse(settleDate));
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("内部勾兑成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("启动内部勾兑失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/check/redoCheck/startChkFileCreate")
    public ResponseDTO<String> startChkFileCreate(String orgCode, String settleDate) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = startChkFileCreateFacade.startChkFileCreate(orgCode, DateUtil.parse(settleDate));
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("对账文件生成成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("启动对账文件生成失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/check/redoCheck/countBankChkComplete")
    public ResponseDTO<String> countBankChkComplete(String settleDate) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            Response<String> response = countBankChkCompleteFacade.countBankChkComplete(
                    DateUtil.parse(settleDate), SessionUtils.getUserName());
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("统计支付机构对账情况成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("统计支付机构对账情况失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }
}
