package com.oriental.manage.service.reserve;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.service.base.IUserInfoService;
import com.oriental.reserve.api.message.MessageBatchInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.message.MessageBatch;
import com.oriental.reserve.model.message.MessageDetailBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/12/28
 * Time: 16:09
 * Desc：
 */
@Service
@Slf4j
public class MessageBatchInfoService {

    @Autowired
    private MessageBatchInterface messageBatchInterface;
    
    @Autowired
    private IUserInfoService userInfoService;

    public void query(Pagination<MessageBatch> pagination, MessageBatch messageBatch, ResponseDTO<Pagination<MessageBatch>> responseDTO){
        log.info("查询批次信息请求:{}",messageBatch);
        ResponseModel<List<MessageBatch>> responseModel = messageBatchInterface.queryMessageBatch(messageBatch);
        log.info("响应信息:{}",responseModel);
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,messageBatch,"查询失败");
    }


    public void queryBatchDetail(ResponseDTO<Pagination<? extends MessageDetailBase>> responseDTO,MessageBatch messageBatch){
        log.info("查询批次详细信息请求参数:{}",messageBatch);
        ResponseModel<List<? extends MessageDetailBase>> responseModel = messageBatchInterface.queryMessageDetail(messageBatch);
        log.info("查询批次详细信息响应信息:{}",responseModel);
        Pagination pagination = new Pagination<>();
        if (responseModel != null && responseModel.isSuccess()){
            pagination.setList(responseModel.getResult());
            responseDTO.setObject(pagination);
            responseDTO.setSuccess(true);
        }else if (null != responseModel){
            responseDTO.setSuccess(false);
            responseDTO.setMsg(responseModel.getErrorMsg());
        }else {
            throw new BusiException("查询失败");
        }
    }

    public void auditMessage(ResponseDTO<String> responseDTO,MessageBatch messageBatch){
        UserInfo userInfo = userInfoService.getUserById(SessionUtils.getUserId());
        messageBatch.setUpdateBy(userInfo.getUserFullName());
        log.info("审核批次请求信息:{}",messageBatch);
        ResponseModel<String> responseModel = messageBatchInterface.auditMessage(messageBatch);
        log.info("审核批次响应信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"审核成功");
    }

    public void regenerateBatch(ResponseDTO<String> responseDTO,MessageBatch messageBatch){
        log.info("重新生成批次请求信息:{}",messageBatch);
        messageBatch.setUpdateBy(SessionUtils.getUserName());
        ResponseModel<MessageBatch> responseModel = messageBatchInterface.regenerateBatch(messageBatch);
        log.info("重新生成响应信息:{}",responseModel);
        ReserveUtil.responseHandle(responseModel,responseDTO,"审核成功");
    }
}
