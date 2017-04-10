package com.oriental.manage.controller.business;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.TransRight;
import com.oriental.manage.service.institution.ITransRightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/5/10.
 * 业务交易权限信息
 */
@Slf4j
@Controller
@RequestMapping("business/transRight")
public class TransRightBusinessController {

    @Autowired
    private ITransRightService transRightService;

   @RequestMapping("init")
    public String init(){

        return "business/searchTransRightBusiness";
    }

    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<TransRight>> queryPage(Pagination<TransRight> pagination, TransRight transRight){

        ResponseDTO<Pagination<TransRight>> responseDTO =new ResponseDTO<Pagination<TransRight>>();
        try{
            transRight.setCompanyType(CompanyType.BUSINESS.getCode());
            transRightService.queryPage(pagination,transRight);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }   catch (Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

}
