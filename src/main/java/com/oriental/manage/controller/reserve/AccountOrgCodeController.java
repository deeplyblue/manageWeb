package com.oriental.manage.controller.reserve;

import com.oriental.clearDubbo.model.base.ResponseModel;
import com.oriental.manage.core.enums.ErrorCodeManage;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.service.reserve.AccountOrgCodeService;
import com.oriental.reserve.model.config.AccountOrgCodeDto;
import lombok.extern.slf4j.Slf4j;
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
 * Date: 2016/11/14
 * Time: 9:59
 * Desc：
 */
@Controller
@Slf4j
@RequestMapping("/reserve/account/orgCode")
public class AccountOrgCodeController {


    @Autowired
    private AccountOrgCodeService accountOrgCodeService;

    @RequestMapping("/init")
    public String init() {
        return "reserve/searchAccountOrgCode";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "reserve/addAccountOrgCode";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate() {
        return "reserve/updateAccountOrgCode";
    }

    protected void add(ResponseDTO<String> responseDTO, AccountOrgCodeDto baseModel) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        baseModel.setCreateBy(SessionUtils.getUserName());
        baseModel.setUpdateBy(SessionUtils.getUserName());
        accountOrgCodeService.add(baseModel, responseDTO);
    }

    protected void update(ResponseDTO<String> responseDTO, AccountOrgCodeDto baseModel) {
        baseModel.setUpdateBy(SessionUtils.getUserName());
        accountOrgCodeService.update(baseModel, responseDTO);
    }

    protected void search(Pagination<AccountOrgCodeDto> pagination, AccountOrgCodeDto baseModel) {
        ResponseDTO<Pagination<AccountOrgCodeDto>> responseDTO = new ResponseDTO<>();
        accountOrgCodeService.query(pagination, baseModel, responseDTO);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseDTO<String> delete(@RequestBody AccountOrgCodeDto accountOrgCodeDto) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            accountOrgCodeDto.setUpdateBy(SessionUtils.getUserName());
            accountOrgCodeService.delete(accountOrgCodeDto, responseDTO);
        } catch (Exception e) {
            log.error("账户机构删除异常:", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(ErrorCodeManage.SYSTEM_EXCEPTION.getDesc());
            responseDTO.setCode(ErrorCodeManage.SYSTEM_EXCEPTION.getCode());
        }
        return responseDTO;
    }


    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<AccountOrgCodeDto>> queryPage(Pagination<AccountOrgCodeDto> pagination, AccountOrgCodeDto baseModel) {
        ResponseDTO<Pagination<AccountOrgCodeDto>> responseDTO = new ResponseDTO();
        log.info("查询信息:{},{}", baseModel, pagination);
        try {
            search(pagination, baseModel);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseDTO<String> addBase(AccountOrgCodeDto baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        log.info("新增信息:{}", baseModel);
        try {
            add(responseDTO, baseModel);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseDTO<String> updateBase(@RequestBody AccountOrgCodeDto baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("修改信息:{}", baseModel);
        try {
            update(responseDTO, baseModel);
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }


    public void handResponse(ResponseModel<String> response, ResponseDTO<String> responseDTO, String message) {
        if (response.getResponseResult()) {
            responseDTO.setSuccess(true);
            responseDTO.setObject(message);
        } else {
            responseDTO.setSuccess(false);
            responseDTO.setMsg(response.getResponseMessage());
        }
    }
}
