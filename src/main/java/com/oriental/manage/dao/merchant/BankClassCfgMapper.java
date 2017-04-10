package com.oriental.manage.dao.merchant;

import com.oriental.manage.pojo.merchant.BankClassCfg;

import java.util.List;

/**
 * Created by wangjun on 2016/5/23.
 */
public interface BankClassCfgMapper {

    List<BankClassCfg > queryPage(BankClassCfg bankClassCfg);

    int insertBankClassCfg(BankClassCfg bankClassCfg);

    int updateBankClassCfg(BankClassCfg bankClassCfg);

    int deleteBankClassCfg(BankClassCfg bankClassCfg);
}
