package com.oriental.manage.controller.merchant;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.merchant.MailInfo;
import com.oriental.manage.service.merchant.IMailInfoSeivice;
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
 * Created by wangjun on 2016/5/16.
 */
@Slf4j
@Controller
@RequestMapping("merchant/mailInfo")
public class MailInfoController {

    @Autowired
    private IMailInfoSeivice mailInfoSeivice;

    @RequestMapping("init")
    public String init(){

        return "merchant/searchMailInfo";
    }

    @OperateLogger(content = "查询邮寄信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("org-mailInfo_select")
    @ResponseBody
    public ResponseDTO<Pagination<MailInfo>> queryPage(Pagination<MailInfo> pagination, MailInfo mailInfo){
         ResponseDTO<Pagination<MailInfo>> responseDTO=new ResponseDTO<Pagination<MailInfo>>();

        try{
            mailInfo.setCompanyType(CompanyType.PAY.getCode());
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
    @RequiresPermissions("org-mailInfo_add")
    public String toAdd(){
        return "merchant/addMailInfo";
    }

    @OperateLogger(content = "新增邮寄信息",operationType = OperateLogger.OperationType.C,tables = "T_MAIL_INFO")
    @RequestMapping("add")
    @RequiresPermissions("org-mailInfo_add")
    @ResponseBody
    public ResponseDTO<String> add(MailInfo mailInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            if(mailInfo.getAreaCode()!=null){
                String are=mailInfo.getAreaCode().substring(0,2);
                String city=mailInfo.getAreaCode().substring(2,4);
                mailInfo.setAreaCode(are);
                mailInfo.setCityCode(city);
            }
            mailInfo.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            mailInfo.setCompanyType(CompanyType.PAY.getCode());
            mailInfoSeivice.addMailInfo(responseDTO,mailInfo);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("toUpdate")
    @RequiresPermissions("org-mailInfo_update")
    public String toUpdate(){
        return "merchant/updateMailInfo";
    }

    @OperateLogger(content = "修改邮寄信息",operationType = OperateLogger.OperationType.U,tables = "T_MAIL_INFO")
    @RequestMapping("update")
    @RequiresPermissions("org-mailInfo_update")
    @ResponseBody
    public ResponseDTO<String> update(MailInfo mailInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
       try {
           if(mailInfo.getAreaCode()!=null){
               String are=mailInfo.getAreaCode().substring(0,2);
               String city=mailInfo.getAreaCode().substring(2,4);
               mailInfo.setAreaCode(are);
               mailInfo.setCityCode(city);
           }
        mailInfoSeivice.updateMailInfo(responseDTO,mailInfo);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "删除邮寄信息",operationType = OperateLogger.OperationType.D,tables = "T_MAIL_INFO")
    @RequestMapping("delete")
    @RequiresPermissions("org-mailInfo_delete")
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
