package com.oriental.manage.controller.institution;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.institution.ContactInfo;
import com.oriental.manage.service.institution.IContractInfoService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangjun on 2016/5/5.
 * 查询支付机构联系人
 */

@Slf4j
@Controller
@RequestMapping("institution/contact")
public class ContactInfoConterller {
    @Autowired
    private IContractInfoService contactInfoService;

    @RequestMapping("init")
    public String init(){

        return "institution/searchContact";
    }

    @OperateLogger(content = "支付机构联系人查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("institution-contact_select")
    public @ResponseBody
    ResponseDTO<Pagination<ContactInfo>> queryPage(Pagination<ContactInfo> pagination,ContactInfo contactInfo){
        ResponseDTO<Pagination<ContactInfo>> responseDTO = new ResponseDTO<Pagination<ContactInfo>>();
        try {
            contactInfo.setCompanyType(CompanyType.PAY.getCode());
             contactInfoService.queryPage(pagination,contactInfo);
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
    @RequiresPermissions("institution-contact_add")
    public String toAdd() {

        return "institution/addContact";
    }
    @OperateLogger(content = "新增支付机构联系人",operationType = OperateLogger.OperationType.C,tables = "T_CONTACT_INFO")
    @RequestMapping("add")
    @RequiresPermissions("institution-contact_add")
    @ResponseBody
    public ResponseDTO<String> add(ContactInfo contactInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contactInfo.setCompanyType(CompanyType.PAY.getCode());
            contactInfo.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            contactInfo.setCttStatus("0");

            contactInfoService.addContactInfo(contactInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("toUpdate")
    @RequiresPermissions("institution-contact_update")
    public String toUpdate() {
        return "institution/updateContact";
    }

    @OperateLogger(content = "修改支付机构联系人",operationType = OperateLogger.OperationType.U,tables = "T_CONTACT_INFO")
    @RequestMapping("update")
    @RequiresPermissions("institution-contact_update")
    @ResponseBody
    public ResponseDTO<String> update(ContactInfo contactInfo){
           ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            contactInfoService.updateContactInfo(contactInfo,responseDTO);
        }catch(Exception e){
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return  responseDTO;
    }
    @OperateLogger(content = "删除支付机构联系人",operationType = OperateLogger.OperationType.D,tables = "T_CONTACT_INFO")
    @RequestMapping("delete")
    @RequiresPermissions("institution-contact_delete")
    @ResponseBody
    public ResponseDTO<String> delete(ContactInfo contactInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contactInfoService.deleteContactInfo(contactInfo,responseDTO);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
