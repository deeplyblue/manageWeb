package com.oriental.manage.controller.business;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.MailInfo;
import com.oriental.manage.service.merchant.IMailInfoSeivice;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/5/16.
 * 业务机构邮寄信息
 */
@Slf4j
@Controller
@RequestMapping("business/businessMail")
public class BusinessMailInfoController {

    @Autowired
    private IMailInfoSeivice mailInfoSeivice;

    @RequestMapping("init")
    public String init(){

        return "business/searchMailInfo";
    }

    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<MailInfo>> queryPage(Pagination<MailInfo> pagination, MailInfo mailInfo){
         ResponseDTO<Pagination<MailInfo>> responseDTO=new ResponseDTO<Pagination<MailInfo>>();

        try{
            mailInfo.setCompanyType(CompanyType.BUSINESS.getCode());
            mailInfoSeivice.queryPage(pagination,mailInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);

        }
        catch(Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @RequestMapping("toAdd")
    public String toAdd(){
        return "business/addMailInfo";
    }

    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(MailInfo mailInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            mailInfo.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            mailInfo.setCompanyType(CompanyType.BUSINESS.getCode());


          mailInfoSeivice.addMailInfo(responseDTO,mailInfo);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("toUpdate")
    public String toUpdate(){
        return "business/updateMailInfo";
    }

    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update(MailInfo mailInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
       try {
        mailInfoSeivice.updateMailInfo(responseDTO,mailInfo);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(MailInfo mailInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
          mailInfoSeivice.deleteMailInfo(responseDTO,mailInfo);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
