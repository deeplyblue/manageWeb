package com.oriental.manage.controller.settlementfront;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.paycenter.commons.mode.Response;
import com.oriental.settlementfront.service.facade.manager.BankTransDetailFacade;
import com.oriental.settlementfront.service.facade.manager.MchntTransDetailFacade;
import com.oriental.settlementfront.service.facade.manager.model.BankTransDetailDTO;
import com.oriental.settlementfront.service.facade.manager.model.MchntTransDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>单笔交易明细控制器</li>
 * <li>User:蒯越 Date:2016/6/23 Time:10:52</li>
 * </ul>
 */
@Slf4j
@Controller
public class TransDetailOneController {

    @Autowired
    private MchntTransDetailFacade mchntTransDetailFacade;

    @Autowired
    private BankTransDetailFacade bankTransDetailFacade;

    @RequestMapping("/settlementFront/transDetailOne/toSearchDetail")
    public String toSearchDetail() {
        return "settlementfront/searchTransDetailOne";
    }

    @OperateLogger(content = "商户交易订单明细查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/settlementFront/transDetailOne/searchDetail")
    @ResponseBody
    public ResponseDTO<Map<String, Object>> queryPayTransDetail(String platformCode, String innerPayTransNo) {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        try {
            log.info("商户交易订单明细查询参数：platformCode："+platformCode+",innerPayTransNo:"+innerPayTransNo);
            Response<List<MchntTransDetailDTO>> mchntResponse = mchntTransDetailFacade.selectByInnerPayTransNo(
                    platformCode, innerPayTransNo, SessionUtils.getUserName());
            log.info("商户交易订单明细查询结果："+mchntResponse);
            if (!mchntResponse.isSuccess()) {
                throw new BusiException(mchntResponse.getErrorMsg());
            }
            Response<List<BankTransDetailDTO>> bankResponse = bankTransDetailFacade.selectByInnerPayTransNo(
                    platformCode, innerPayTransNo, SessionUtils.getUserName());
            if (!bankResponse.isSuccess()) {
                throw new BusiException(bankResponse.getErrorMsg());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("mchntTransDetailDTOList", mchntResponse.getResult());
            map.put("bankTransDetailDTOList", bankResponse.getResult());
            responseDTO.setSuccess(true);
            responseDTO.setObject(map);
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
