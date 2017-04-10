package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.api.config.AccountOrgCodeInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.config.AccountOrgCodeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/11/16
 * Time: 9:01
 * Desc：
 */
@Slf4j
@Service
public class AccountOrgCodeService {

    @Autowired
    private AccountOrgCodeInterface accountOrgCodeInterface;

    public void query(Pagination<AccountOrgCodeDto> pagination, AccountOrgCodeDto accountOrgCodeDto, ResponseDTO<Pagination<AccountOrgCodeDto>> responseDTO){
        ResponseModel<List<AccountOrgCodeDto>> responseModel = accountOrgCodeInterface.queryAccountOrgCode(accountOrgCodeDto);
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,accountOrgCodeDto,"查询失败");
    }

    public void add(AccountOrgCodeDto accountOrgCodeDto, ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = accountOrgCodeInterface.createAccountOrgCode(accountOrgCodeDto);
        ReserveUtil.responseHandle(responseModel,responseDTO,"新增成功");
    }

    public void update(AccountOrgCodeDto accountOrgCodeDto,ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = accountOrgCodeInterface.updateAccountOrgCode(accountOrgCodeDto);
        ReserveUtil.responseHandle(responseModel,responseDTO,"修改成功");
    }

    public void delete(AccountOrgCodeDto accountOrgCodeDto,ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = accountOrgCodeInterface.deleteAccountOrgCode(accountOrgCodeDto);
        ReserveUtil.responseHandle(responseModel,responseDTO,"删除成功");
    }
}
