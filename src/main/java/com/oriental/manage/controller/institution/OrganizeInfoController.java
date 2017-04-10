package com.oriental.manage.controller.institution;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.institution.OrganizeInfo;
import com.oriental.manage.service.institution.IOrganizeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by wangjun on 2016/5/4.
 */
@Slf4j
@Controller
@RequestMapping("institution/payment")
public class OrganizeInfoController {
    @Autowired
    private IOrganizeInfoService organizeInfoService;

    @RequestMapping("init")
    public String init() {

        return "institution/searchInstitution";
    }

    @OperateLogger(content = "查询支付机构", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("organize-info_select")
    public
    @ResponseBody
    ResponseDTO<Pagination<OrganizeInfo>> queryPage(Pagination<OrganizeInfo> pagination, OrganizeInfo organizeInfo, String orgCode) {
        ResponseDTO<Pagination<OrganizeInfo>> responseDTO = new ResponseDTO<Pagination<OrganizeInfo>>();
        try {
            organizeInfo.setOrgCategory(CompanyType.PAY.getCode());

            organizeInfoService.queryPage(pagination, organizeInfo);
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
    @RequiresPermissions("organize-info_add")
    public String toAdd() {

        return "institution/addInstitution";
    }

    @OperateLogger(content = "新增支付机构", operationType = OperateLogger.OperationType.C, tables = "T_ORGANIZE_INFO", noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("add")
    @RequiresPermissions("organize-info_add")
    @ResponseBody
    public ResponseDTO<String> add(OrganizeInfo organizeInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            organizeInfo.setOrgCategory(CompanyType.PAY.getCode());

            organizeInfo.setOrgStatus("01");
            organizeInfo.setHandler(SessionUtils.getUserName());
            organizeInfoService.addOrganizeInfo(organizeInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("toUpdate")
    @RequiresPermissions("organize-info_update")
    public String toUpdate() {

        return "institution/updateInstitution";
    }

    @OperateLogger(content = "修改支付机构", operationType = OperateLogger.OperationType.U, tables = "T_ORGANIZE_INFO")
    @RequestMapping("update")
    @RequiresPermissions(value = {"organize-info_update", "organize-info_disable", "organize-info_cancel",
            "organize-info_locking", "organize-info_openSuccess", "organize-info_openErro", "organize-info_open"}, logical = Logical.OR)
    @ResponseBody
    public ResponseDTO<String> update(OrganizeInfo organizeInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            organizeInfo.setHandler(SessionUtils.getUserName());
            organizeInfo.setLastUpdTime(new Date());
            organizeInfoService.updateOrganizeInfo(organizeInfo, responseDTO);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "删除支付机构", operationType = OperateLogger.OperationType.D, tables = "T_ORGANIZE_INFO")
    @RequestMapping("delete")
    @RequiresPermissions("organize-info_delete")
    @ResponseBody
    public ResponseDTO<String> delete(OrganizeInfo organizeInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            organizeInfoService.deleteOrganizeInfo(organizeInfo, responseDTO);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("checkOrgCode")
    @ResponseBody
    public boolean checkOrgCode(String orgCode,String id){
        OrganizeInfo organizeInfo=new OrganizeInfo();
        organizeInfo.setOrgCode(orgCode);
        return organizeInfoService.check(organizeInfo,id);
    }
    @RequestMapping("checkName")
    @ResponseBody
    public boolean checkName(String orgName,String code){
        OrganizeInfo organizeInfo=new OrganizeInfo();
        organizeInfo.setOrgName(orgName);
        return organizeInfoService.check(organizeInfo,code);
    }

    @RequestMapping("checkAbbrName")
    @ResponseBody
    public boolean checkAbbrName(String orgAbbrName,String code){
        OrganizeInfo organizeInfo=new OrganizeInfo();
        organizeInfo.setOrgAbbrName(orgAbbrName);
        return organizeInfoService.check(organizeInfo,code);
    }
}
