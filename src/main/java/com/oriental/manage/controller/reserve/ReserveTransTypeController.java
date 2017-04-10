package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.service.reserve.AccountTransTypeService;
import com.oriental.reserve.model.config.AccountTransTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Author: Yang xp
 * Date: 2016/10/20
 * Time: 11:25
 * Desc 交易类型配置
 */
@RequestMapping("/reserve/trans/type")
@Controller
@Slf4j
public class ReserveTransTypeController  {

    @Autowired
    private AccountTransTypeService accountTransTypeService;

    @RequestMapping("/init")
    public String init(){
        return "reserve/searchAccountTransType";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("reserve-transType_add")
    public String toAdd(){
        return "reserve/addAccountTransType";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("reserve-transType_update")
    public String toUpdate(){
        return "reserve/updateAccountTransType";
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseDTO<String> add( AccountTransTypeDto baseModel) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("新增信息:{}",baseModel);
        try {
            baseModel.setCreateBy(SessionUtils.getUserName());
            baseModel.setUpdateBy(SessionUtils.getUserName());
            accountTransTypeService.addTransType(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseDTO<String>update( @RequestBody AccountTransTypeDto baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("修改信息:{}",baseModel);
        try {
            baseModel.setUpdateBy(SessionUtils.getUserName());
            accountTransTypeService.udpateTransType(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

    @RequestMapping("/search")
    @ResponseBody
    @RequiresPermissions("reserve-transType_search")
    public ResponseDTO<Pagination<AccountTransTypeDto>>search(Pagination<AccountTransTypeDto> pagination, AccountTransTypeDto baseModel) {
        ResponseDTO<Pagination<AccountTransTypeDto>> responseDTO = new ResponseDTO<>();
        try{
            accountTransTypeService.searchTransType(pagination,baseModel,responseDTO);
            log.info("查询信息:{},{}",baseModel,pagination);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

    @RequestMapping("/delete")
    @RequiresPermissions("reserve-transType_delete")
    @ResponseBody
    public ResponseDTO<String> delete(@RequestBody AccountTransTypeDto baseModel){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("删除信息:{}",baseModel);
        try {
            accountTransTypeService.deleteTransType(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("删除失败");
        }
        return responseDTO;
    }
}
