package com.oriental.manage.controller.business;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.OrgBusi;
import com.oriental.manage.service.institution.IOrgBusiService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/6/2.
 * 业务机构信息
 */
@Slf4j
@Controller
@RequestMapping("institution/orgbusi")
public class OrgBusiController {

    @Autowired
    private IOrgBusiService orgBusiService;

    @RequestMapping("init")
    public String init(){
        return "institution/searchOrgBusi";
    }
    @RequestMapping("search")
    public @ResponseBody
    ResponseDTO<Pagination<OrgBusi>> queryPage(Pagination<OrgBusi> pagination, OrgBusi orgBusi){
        ResponseDTO<Pagination<OrgBusi>> responseDTO = new ResponseDTO<Pagination<OrgBusi>>();
        try {
            orgBusiService.queryPage(pagination,orgBusi);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("toAdd")
    public String toAdd(){
        return "institution/addOrgBusi";
    }

    @RequestMapping("add")
    public @ResponseBody
    ResponseDTO<String> add(OrgBusi orgBusi){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            orgBusi.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            orgBusiService.addOrgBusi(responseDTO,orgBusi);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("toUpdate")
    public String toUpdate(){
        return "institution/updateOrgBusi";
    }

    @RequestMapping("update")
    public @ResponseBody
    ResponseDTO<String> update(OrgBusi orgBusi){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            orgBusiService.updateOrgBusi(responseDTO,orgBusi);
        } catch (Exception e) {
            log.error("跟新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(OrgBusi orgBusi) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {

            orgBusiService.deleteOrgBusi(responseDTO,orgBusi);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
