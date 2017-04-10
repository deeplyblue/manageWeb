package com.oriental.manage.service.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.ContractInfo;

/**
 * Created by wangjun on 2016/5/16.
 */
public interface IContractService {

    void queryPage(Pagination<ContractInfo> pagination, ContractInfo contractInfo);

    void addContract(ResponseDTO<String> responseDTO,ContractInfo contractInfo);

    void updateContract(ResponseDTO<String> responseDTO,ContractInfo contractInfo);

    void deleteContract(ResponseDTO<String> responseDTO,ContractInfo contractInfo);

    ContractInfo queryById(String id);

    boolean check(ContractInfo contractInfo);
}
