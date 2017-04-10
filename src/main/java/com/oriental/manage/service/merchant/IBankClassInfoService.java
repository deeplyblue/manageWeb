package com.oriental.manage.service.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.BankClassCfg;


/**
 * Created by wangjun on 2016/5/23.
 */
public interface IBankClassInfoService {

    void queryPage(Pagination<BankClassCfg> pagination, BankClassCfg bankClassCfg);

    void addBankClassCfg(ResponseDTO<String> responseDTO, BankClassCfg bankClassCfg);

    void upadteBankClassCfg(ResponseDTO<String> responseDTO, BankClassCfg bankClassCfg);

    void deleteBankClassCfg(ResponseDTO<String> responseDTO, BankClassCfg bankClassCfg);

}
