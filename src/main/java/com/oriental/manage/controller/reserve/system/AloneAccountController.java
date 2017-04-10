package com.oriental.manage.controller.reserve.system;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.reserve.AloneAccount;
import com.oriental.reserve.api.sys.AloneAccountInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.sys.AloneAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yutao on 2016/12/21.
 */
@Controller
@Slf4j
@RequestMapping("reserve/aloneAccount/apply")
public class AloneAccountController {

    @Autowired
    private AloneAccountInterface aloneAccountInterface;

    @RequestMapping("init")
    public String init(){return "reserve/system/searchAloneAccount";}


    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<AloneAccount>> query(@RequestBody Pagination<AloneAccount> pagination){

        ResponseDTO<Pagination<AloneAccount>> responseDTO = new ResponseDTO<Pagination<AloneAccount>>();
        try{
            log.info("查询单独对账申请参数信息:{}", pagination);
            AloneAccountDto aloneAccountDto = BeanMapperUtil.objConvert(pagination.getQueryBean(), AloneAccountDto.class);
            ResponseModel<List<AloneAccountDto>> responseModel = aloneAccountInterface.queryPage(aloneAccountDto);
            if(responseModel.isSuccess()){
                List<AloneAccount> aloneAccounts = BeanMapperUtil.mapList(responseModel.getResult(), AloneAccount.class);
                pagination.setList(aloneAccounts);
                if(aloneAccounts !=null && aloneAccounts.size()>0){
                    pagination.setRowCount(aloneAccounts.get(0).getRowCount());
                }
                responseDTO.setObject(pagination);
                responseDTO.setSuccess(true);
            }else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }
        }catch (Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;

    }

    @RequestMapping("toAdd")
    public String toAdd() {return "reserve/system/addAloneAccount";}

    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> addAloneAccount(@RequestBody AloneAccount aloneAccount){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            log.info("新增单独对账申请参数信息:{}",aloneAccount);
            AloneAccountDto aloneAccountDto = BeanMapperUtil.objConvert(aloneAccount, AloneAccountDto.class);
            aloneAccountDto.setCreateBy(SessionUtils.getUserName());
            ResponseModel<String> responseModel = aloneAccountInterface.aloneAccountApply(aloneAccountDto);
            if(responseModel.isSuccess()){
                responseDTO.setSuccess(true);
                responseDTO.setMsg("新增成功");
            }else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("新增失败");
            }
        }catch (Exception e){
            log.error("新增失败:{}",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }

        return responseDTO;
    }



    @RequestMapping("toUpdate")
    public String toUpdate(){return "reserve/system/updateAloneAccount";}


    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update (@RequestBody AloneAccount aloneAccount){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            log.info("修改单独对账申请参数信息:{}",aloneAccount);
            AloneAccountDto aloneAccountDto = BeanMapperUtil.objConvert(aloneAccount, AloneAccountDto.class);
            ResponseModel<String> responseModel = aloneAccountInterface.updateAloneAccount(aloneAccountDto);
            if(responseModel.isSuccess()){
                responseDTO.setSuccess(true);
                responseDTO.setMsg(responseModel.getResult());
            }else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("修改失败");
            }
        }catch (Exception e){
            log.error("修改失败:{}",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }

        return responseDTO;
    }



}
