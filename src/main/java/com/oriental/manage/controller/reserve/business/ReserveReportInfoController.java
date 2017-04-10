package com.oriental.manage.controller.reserve.business;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.BusinessWhiteListService;
import com.oriental.reserve.model.ReserveInfo;
import com.oriental.reserve.model.business.BusinessWhiteListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoxin on 2016/12/20.
 *
 */
@Slf4j
@Controller
@RequestMapping("/reserve/bank")
public class ReserveReportInfoController {

    @Autowired
    private BusinessWhiteListService businessWhiteListService;

    @RequestMapping("update/init")
    public String init() {
        return "reserve/searchReportInformation";
    }

    @RequestMapping("update/toDetail")
    public String toDetail() {
        return "reserve/queryReportInformationDetail";
    }

    @RequestMapping("update/search")
    @ResponseBody
    public ResponseDTO<Pagination<BusinessWhiteListDto>> search(Pagination<BusinessWhiteListDto> pagination, BusinessWhiteListDto businessWhiteListDto) {
        ResponseDTO<Pagination<BusinessWhiteListDto>> responseDTO = new ResponseDTO<Pagination<BusinessWhiteListDto>>();
        try {
            businessWhiteListService.searchBusinessWhiteList(pagination, businessWhiteListDto, responseDTO);
            log.info("商户白名单查询信息:{},{}", businessWhiteListDto, pagination);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("商户信息查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("商户信息查询失败");
        }
        return responseDTO;
    }
}
