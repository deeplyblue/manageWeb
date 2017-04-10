package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.api.config.AccountTransTypeInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.config.AccountTransTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/10/20
 * Time: 13:58
 * Desc 交易类型
 */
@Service
@Slf4j
public class AccountTransTypeService {

    @Autowired
    private AccountTransTypeInterface accountTransTypeInterface;

    public void searchTransType(Pagination<AccountTransTypeDto> pagination,AccountTransTypeDto accountTransTypeDto, ResponseDTO<Pagination<AccountTransTypeDto>> responseDTO){
        ResponseModel<List<AccountTransTypeDto>> responseModel = accountTransTypeInterface.queryAccountTransTypePage (accountTransTypeDto);
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,accountTransTypeDto,"查询成功");
    }

    public void addTransType(AccountTransTypeDto accountTransTypeDto,ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = accountTransTypeInterface.createAccountTransType(accountTransTypeDto);
        ReserveUtil.responseHandle(responseModel,responseDTO,"新增成功");
    }

    public void udpateTransType(AccountTransTypeDto accountTransTypeDto,ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = accountTransTypeInterface.updateAccountTransType(accountTransTypeDto);
        ReserveUtil.responseHandle(responseModel,responseDTO,"修改成功");
    }

    public void deleteTransType(AccountTransTypeDto accountTransTypeDto,ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = accountTransTypeInterface.deleteAccountTransType(accountTransTypeDto);
        ReserveUtil.responseHandle(responseModel,responseDTO,"删除成功");
    }
}
