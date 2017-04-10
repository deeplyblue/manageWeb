package com.oriental.manage.controller.business;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.ContactInfo;
import com.oriental.manage.service.institution.IContractInfoService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/6/3.
 * 业务机构联系人
 */

@Slf4j
@Controller
@RequestMapping("institution/businessContact")
public class BusinessContactInfoConterller {
    @Autowired
    private IContractInfoService contactInfoService;

    @RequestMapping("init")
    public String init(){

        return "business/searchBusinessContact";
    }

    @RequestMapping("search")
    public @ResponseBody
    ResponseDTO<Pagination<ContactInfo>> queryPage(Pagination<ContactInfo> pagination, ContactInfo contactInfo){
        ResponseDTO<Pagination<ContactInfo>> responseDTO = new ResponseDTO<Pagination<ContactInfo>>();
        try {
            contactInfo.setCompanyType(CompanyType.BUSINESS.getCode());
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
    public String toAdd() {

        return "business/addBusinessContact";
    }
    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(ContactInfo contactInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contactInfo.setCompanyType(CompanyType.BUSINESS.getCode());
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
    public String toUpdate() {
        return "business/updateBusinessContact";
    }

    @RequestMapping("update")
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
    @RequestMapping("delete")
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
