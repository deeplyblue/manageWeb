package com.oriental.manage.controller.reserve.business;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.BusinessWhiteListService;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.business.BusinessWhiteListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by luoxin on 2017/1/11.
 */
@Slf4j
@Controller
@RequestMapping("/reserve/bank/busi")
public class ReserveBusinessUpdateController {
    @Autowired
    private BusinessWhiteListService businessWhiteListService;

    @RequestMapping("/init")
    public String init() {
        return "reserve/busi/updateBusiInformation";
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<BusinessWhiteListDto>> search( Pagination<BusinessWhiteListDto> pagination, BusinessWhiteListDto businessWhiteListDto) {
        ResponseDTO<Pagination<BusinessWhiteListDto>> responseDTO = new ResponseDTO<Pagination<BusinessWhiteListDto>>();
        ResponseModel responseModel=new ResponseModel();
        try {
            String merchantNO=businessWhiteListDto.getMerchantNO();//商户编号
            String operateType=businessWhiteListDto.getOperateType();//操作类型
            log.info("更新商户白名单查询信息:{},{}", merchantNO, operateType);
            if((merchantNO==null||"".equals(merchantNO))&&(operateType==null||"".equals(operateType))||(merchantNO!=null||!"".equals(merchantNO))&&(operateType!=null||!"".equals(operateType))){
                responseModel= businessWhiteListService.updateBusinessWhiteList(pagination, businessWhiteListDto, responseDTO);
                   log.info(responseModel.getErrorMsg());
                   responseDTO.setSuccess(responseModel.isSuccess());
                   responseDTO.setMsg(responseModel.getErrorMsg());
            }else{
                log.error("输入参数有误，请重新输入");
                responseDTO.setSuccess(false);
                responseDTO.setMsg("输入参数有误，请重新输入");
            }
        } catch (Exception e) {
            log.error("更新商户信息查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("更新商户信息查询失败");
        }
        return responseDTO;
    }
}
