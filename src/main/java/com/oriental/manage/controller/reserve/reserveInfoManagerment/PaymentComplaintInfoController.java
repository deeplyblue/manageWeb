package com.oriental.manage.controller.reserve.reserveInfoManagerment;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.reserve.PaymentComplaintInfoPojo;
import com.oriental.reserve.api.infoManagement.PaymentComplaintInfoInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.infoManagement.PaymentComplaintInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 类注释
 *
 * @author: hz
 * @date: 2016/12/22
 * @time: 14:33
 * @see: 链接到其他资源
 * @since: 1.0
 * 投诉信息处理
 */
@Slf4j
@Controller
@RequestMapping("/reserve/msg/paymentComplaintInfo")
public class PaymentComplaintInfoController {
    @Autowired
    PaymentComplaintInfoInterface paymentComplaintInfoInterface;

    /**
     * 初始化页面
     * @return
     */
    @RequestMapping("/init")
    public String init() {
        return "reserve/infoManagement/paymentComplaintInfo";
    }

    /**
     *处理页面
     * @return
     */
    @RequestMapping("/toHandle")
    public String toHandle() {
        return "reserve/infoManagement/paymentComplaintInfoToHandle";
    }

    /**
     * 查看投诉数据处理详情
     * @return
     */
    @RequestMapping("/toDetail")
    public String toDetail() {
        return "reserve/infoManagement/paymentComplaintInfoToDetail";
    }

    /**
     * 查询投诉数据
     * @param pagination
     * @param baseModel
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<PaymentComplaintInfoPojo>> search(Pagination<PaymentComplaintInfoPojo> pagination, PaymentComplaintInfoPojo baseModel) {
        ResponseDTO<Pagination<PaymentComplaintInfoPojo>> responseDTO = new ResponseDTO<>();
        log.info("查询参数：pagination:{},PaymentComplaintInfoPojo:{}", pagination, baseModel);
        try {
            String errorMsg ="";
            PaymentComplaintInfoDto paymentComplaintInfoDto = BeanMapperUtil.objConvert(baseModel, PaymentComplaintInfoDto.class);
            ResponseModel<List<PaymentComplaintInfoDto>> responseModel = paymentComplaintInfoInterface.queryPaymentComplaitInfo(paymentComplaintInfoDto);
            errorMsg = responseModel.getErrorMsg();
            if (responseModel.isSuccess()) {
                List<PaymentComplaintInfoPojo> paymentComplaintInfoPojoList =BeanMapperUtil.mapList(responseModel.getResult(),PaymentComplaintInfoPojo.class);
                if(responseModel.getResult() != null && responseModel.getResult().size()>0){
                    pagination.setRowCount(responseModel.getResult().get(0).getRowCount());
                }
                pagination.setList(paymentComplaintInfoPojoList);
                responseDTO.setObject(pagination);
                responseDTO.setSuccess(true);
            }else {
                responseDTO.setMsg(errorMsg);
                responseDTO.setSuccess(false);
            }

        } catch (Exception e) {
            log.error("查询失败",e);
            responseDTO.setMsg("查询失败");
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    /**
     * 处理投诉数据
     * @param baseModel
     * @return
     */
    @RequestMapping("/handle")
    @ResponseBody
    public ResponseDTO<Pagination<PaymentComplaintInfoPojo>> update(PaymentComplaintInfoPojo baseModel) {
        ResponseDTO<Pagination<PaymentComplaintInfoPojo>> responseDTO = new ResponseDTO<>();
        log.info("投诉信息处理入参:{}", baseModel);
        /**/
        PaymentComplaintInfoDto complaintInfoDto = BeanMapperUtil.objConvert(baseModel, PaymentComplaintInfoDto.class);
        try {
            complaintInfoDto.setMessageMk(complaintInfoDto.getMsgId());//原报文msgId
            complaintInfoDto.setHandle(SessionUtils.getUserName());//处理人取登陆人
            complaintInfoDto.setHandleTime(new Date());
            complaintInfoDto.setHandleRes(complaintInfoDto.getHandleRes());//处理结果
            complaintInfoDto.setHandleTel(SessionUtils.getUserTel());
            paymentComplaintInfoInterface.handPayPaymentComplaitInfo(complaintInfoDto);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("查询失败");
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }
}
