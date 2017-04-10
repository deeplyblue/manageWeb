package com.oriental.manage.controller.reserve.reserveInfoManagerment;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.reserve.InfoManagementApplyPojo;
import com.oriental.reserve.api.infoManagement.MessageApplyInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.infoManagement.MessageApplyDto;
import com.oriental.reserve.model.message.MessageBatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 类注释
 *
 * @author: hz
 * @date: 2016/12/20
 * @time: 14:16
 * @see: 链接到其他资源
 * @since: 1.0
 * 信息管理类数据下发申请
 */
@Slf4j
@Controller
@RequestMapping("/reserve/msg/applyInfo")
public class InfoManagementApplyController {
    @Autowired
    MessageApplyInterface messageApplyInterface;

    @RequestMapping("/init")
    public String init() {
        return "reserve/infoManagement/msgApplyInfo";
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<InfoManagementApplyPojo>> search(Pagination<InfoManagementApplyPojo> pagination, InfoManagementApplyPojo baseModel) {
        ResponseDTO<Pagination<InfoManagementApplyPojo>> responseDTO = new ResponseDTO<>();
        log.info("查询参数：pagination:{},baseModel:{}", pagination, baseModel);
        try {
            String errorMsg = "";
            MessageApplyDto messageApplyDto = BeanMapperUtil.objConvert(baseModel, MessageApplyDto.class);
            ResponseModel<List<MessageApplyDto>> responseModel = messageApplyInterface.queryMsgApply(messageApplyDto);
            errorMsg = responseModel.getErrorMsg();
            List<InfoManagementApplyPojo> pojoList = BeanMapperUtil.mapList(responseModel.getResult(), InfoManagementApplyPojo.class);
            if (responseModel.isSuccess()) {
                if(responseModel.getResult() != null && responseModel.getResult().size()>0){
                    pagination.setRowCount(responseModel.getResult().get(0).getRowCount());
                }
                pagination.setList(pojoList);
                responseDTO.setObject(pagination);
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setMsg(errorMsg);
                responseDTO.setMsg("查询失败,无数据");
            }


        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setMsg("查询失败");
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/apply")
    public ResponseDTO<Pagination<InfoManagementApplyPojo>> apply(InfoManagementApplyPojo baseModel) {
        ResponseDTO<Pagination<InfoManagementApplyPojo>> responseDTO = new ResponseDTO<>();
        String errorMsg = "";
        try {
            log.info("输入参数：{}", baseModel);
            baseModel.setCreatMan(SessionUtils.getUserName());
            MessageApplyDto messageApplyDto = BeanMapperUtil.objConvert(baseModel, MessageApplyDto.class);
            messageApplyDto.setCreateTime(new Date());
            ResponseModel<MessageBatch> responseModel = messageApplyInterface.ApplyMsg(messageApplyDto);
            errorMsg = responseModel.getErrorMsg();
            if (responseModel.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setMsg("申请成功！");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(errorMsg);
            }

        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setMsg(errorMsg);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }
}
