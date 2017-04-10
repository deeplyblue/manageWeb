package com.oriental.manage.controller.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.institution.TransRightTemplate;
import com.oriental.manage.service.institution.ITransRightTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/5/10.
 */
@Slf4j
@Controller
@RequestMapping("institution/transRightTemplate")
public class TransRightTemplateConterller {

    @Autowired
    private ITransRightTemplateService transRightTemplateService;

    @RequestMapping("init")
    public String init(){
        return "institution/searchTransRightTemplate";
    }

    @OperateLogger(content = "查询交易权限模板信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("transRightTemplate_select")
    @ResponseBody
    public ResponseDTO<Pagination<TransRightTemplate>> queryPage(Pagination<TransRightTemplate> pagination,TransRightTemplate transRightTemplate){
        ResponseDTO<Pagination<TransRightTemplate>> responseDTO =new ResponseDTO<Pagination<TransRightTemplate>>();

        try{
            transRightTemplate.setCompanyType("02");
           transRightTemplateService.queryPage(pagination,transRightTemplate);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);

        }catch (Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

}
