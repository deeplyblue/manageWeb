package com.oriental.manage.controller.reserve.reserveInfoManagerment;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.reserve.PaymentContactInfoPojo;
import com.oriental.reserve.api.infoManagement.PaymentContactInfoInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.infoManagement.PaymentContactInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 类注释
 *
 * @author: hz
 * @date: 2016/12/21
 * @time: 10:02
 * @see: 链接到其他资源
 * @since: 1.0
 * 支付机构联系人信息
 */
@Slf4j
@Controller
@RequestMapping("/reserve/msg/paymentContactInfo")
public class PaymentContactInfoController {

    @Autowired
    PaymentContactInfoInterface paymentContactInfoInterface;

    @RequestMapping("/init")
    public String init() {
        return "reserve/infoManagement/paymentContactInfo";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        log.info("新增");
        return "reserve/infoManagement/addPaymentContactInfo";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate() {
        return "reserve/infoManagement/updatePaymentContactInfo";
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<PaymentContactInfoPojo>> search(Pagination<PaymentContactInfoPojo> pagination, PaymentContactInfoPojo baseModel) {
        ResponseDTO<Pagination<PaymentContactInfoPojo>> responseDTO = new ResponseDTO<>();
        log.info("查询参数：pagination:{},baseModel:{}", pagination, baseModel);
        /**/
        PaymentContactInfoDto dto = BeanMapperUtil.objConvert(baseModel, PaymentContactInfoDto.class);
        try {
            String errorMsg = "";
            List<PaymentContactInfoPojo> paymentContactInfoPojoList = new ArrayList<>();
            ResponseModel<List<PaymentContactInfoDto>> responseModel = paymentContactInfoInterface.queryPaymentContactInfo(dto);
            errorMsg = responseModel.getErrorMsg();
            if (responseModel.isSuccess()) {
                paymentContactInfoPojoList = BeanMapperUtil.mapList(responseModel.getResult(), PaymentContactInfoPojo.class);
                if(responseModel.getResult() != null && responseModel.getResult().size()>0){
                    pagination.setRowCount(responseModel.getResult().get(0).getRowCount());
                }
                pagination.setList(paymentContactInfoPojoList);
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
            } else {
                responseDTO.setMsg(errorMsg);
                responseDTO.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ResponseDTO<String> delete(PaymentContactInfoPojo paymentContactInfoPojo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        log.info("删除信息：{}", paymentContactInfoPojo);
        PaymentContactInfoDto dto = BeanMapperUtil.objConvert(paymentContactInfoPojo, PaymentContactInfoDto.class);
        String errorMsg = "";
        try {
            ResponseModel<PaymentContactInfoDto> responseModel = paymentContactInfoInterface.deletePaymentContactInfo(dto);
            errorMsg = responseModel.getErrorMsg();
            if (responseModel.isSuccess()) {
                responseDTO.setMsg("删除成功，等待审核通过");
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(errorMsg);
            }
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseDTO<PaymentContactInfoDto> add(PaymentContactInfoPojo pojo) {
        ResponseDTO<PaymentContactInfoDto> responseDTO = new ResponseDTO<>();
        log.info("增加联系人信息入参：{}", pojo);
        pojo.setOperator(SessionUtils.getUserName());//操作人
        String errorMsg = "";
        try {
            PaymentContactInfoDto dto = BeanMapperUtil.objConvert(pojo, PaymentContactInfoDto.class);
            ResponseModel<PaymentContactInfoDto> responseModel = paymentContactInfoInterface.insertPaymentContactInfo(dto);
            errorMsg = responseModel.getErrorMsg();
            if (responseModel.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject(responseModel.getResult());
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(errorMsg);
            }
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseDTO<PaymentContactInfoDto> update(PaymentContactInfoPojo pojo) {
        ResponseDTO<PaymentContactInfoDto> responseDTO = new ResponseDTO<>();
        log.info("查询信息:PaymentContactInfoPojo--->{}", pojo);
        pojo.setOperator(SessionUtils.getUserName());//操作人
        PaymentContactInfoDto dto = BeanMapperUtil.objConvert(pojo, PaymentContactInfoDto.class);
        try {
            String errorMsg = "";
            ResponseModel<PaymentContactInfoDto> responseModel = paymentContactInfoInterface.editPaymentContactInfo(dto);
            errorMsg = responseModel.getErrorMsg();
            if (responseModel.isSuccess()) {
                responseDTO.setObject(responseModel.getResult());
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setMsg(errorMsg);
                responseDTO.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("查询失败");
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }
}
