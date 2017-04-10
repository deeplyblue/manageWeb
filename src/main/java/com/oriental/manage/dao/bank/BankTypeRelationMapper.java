package com.oriental.manage.dao.bank;

import com.oriental.manage.pojo.bank.BankTypeRelation;

import java.util.List;

/**
 * Desc:
 * Date: 2016/6/3
 * User: ZhouBin
 * See :
 */
public interface BankTypeRelationMapper {

    List<BankTypeRelation> searchAll();

	int insertBankTypeRelation(BankTypeRelation bankTypeRelation);

    int deleteBankTypeRelation(BankTypeRelation bankTypeRelation);

    int updateBankTypeRelation(BankTypeRelation bankTypeRelation);

    List<BankTypeRelation> queryPage(BankTypeRelation bankTypeRelation);

    /***
     * 得到所有银行代码
     * @return
     */
    List<String> getBankCode();

    int checkBankType(BankTypeRelation bankTypeRelation);
}
