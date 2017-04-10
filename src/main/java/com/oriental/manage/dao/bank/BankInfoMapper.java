package com.oriental.manage.dao.bank;

import com.oriental.manage.pojo.bank.BankInfo;

import java.util.List;

/**
 * Desc:
 * Date: 2016/6/2
 * User: ZhouBin
 * See :
 */
public interface BankInfoMapper {

    List<BankInfo> queryAllBankInfo();

    int insertBankInfo(BankInfo bankInfo);

    int deleteBankInfo(BankInfo bankInfo);

    List<BankInfo> getOrgBankInfo();

    BankInfo searchByParams(BankInfo bean);

    int updateBankInfo(BankInfo bankInfo);

    List<BankInfo> queryPage (BankInfo bankInfo);

}
