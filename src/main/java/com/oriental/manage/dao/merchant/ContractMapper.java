package com.oriental.manage.dao.merchant;

import com.oriental.manage.pojo.merchant.ContractInfo;
import com.oriental.manage.pojo.merchant.MailInfo;

import java.util.List;

/**
 * Created by wangjun on 2016/5/16.
 */
public interface ContractMapper {

    int insertContractInfo(ContractInfo contractInfo);

    int updateContractInfo(ContractInfo contractInfo);

    int synContractInfoByFeeCfg(ContractInfo contractInfo);

    List<ContractInfo> queryPage(ContractInfo contractInfo);

    int deleteContract(ContractInfo contractInfo);

}
