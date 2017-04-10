package com.oriental.manage.controller.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.institution.OrgCertificateInfo;
import com.oriental.manage.service.institution.IOrgCertificateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Created by wangjun on 2016/10/31.
 */
@Slf4j
@Controller
@RequestMapping("institution/orgCertificate")
public class OrgCertificateInfoController {

    @Autowired
    private IOrgCertificateInfoService iOrgCertificateInfoService;

    @RequestMapping("init")
    @RequiresPermissions("orgCertificate_search")
    public String init(){return  "institution/searchOrgCertificateInfo";}

    @RequestMapping("toAdd")
    @RequiresPermissions("orgCertificate_add")
    public String toAdd(){return "institution/addOrgCertificateInfo";}

    @OperateLogger(content = "查询机构证书",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("orgCertificate_search")
    public @ResponseBody
    ResponseDTO<Pagination<OrgCertificateInfo>> queryPage(Pagination<OrgCertificateInfo> pagination, OrgCertificateInfo orgCertificateInfo, String orgCode){
        ResponseDTO<Pagination<OrgCertificateInfo>> responseDTO = new ResponseDTO<Pagination<OrgCertificateInfo>>();
        try {

            iOrgCertificateInfoService.queryPage(pagination,orgCertificateInfo);
            responseDTO.setObject(pagination);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "新增机构证书",operationType = OperateLogger.OperationType.C,tables = "t_org_certificate_info")
    @RequestMapping("add")
    @ResponseBody
    @RequiresPermissions("orgCertificate_add")
    public ResponseDTO<String> add(OrgCertificateInfo orgCertificateInfo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            iOrgCertificateInfoService.add(orgCertificateInfo,responseDTO);
        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return  responseDTO;
    }

    @OperateLogger(content = "修改机构证书状态",operationType = OperateLogger.OperationType.U,tables = "t_org_certificate_info")
    @RequestMapping("update")
    @RequiresPermissions(value = {"orgCertificate_toOppen","orgCertificate_abandoned","orgCertificate_open","orgCertificate_cancelOpen",
    "orgCertificate_toClose","orgCertificate_close","orgCertificate_cancelClose"}, logical = Logical.OR)
    @ResponseBody
    public ResponseDTO<String> update(OrgCertificateInfo orgCertificateInfo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            iOrgCertificateInfoService.updateStatus(orgCertificateInfo,responseDTO);
        }catch (Exception e){
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return  responseDTO;
    }
}
