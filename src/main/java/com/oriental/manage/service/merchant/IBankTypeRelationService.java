package com.oriental.manage.service.merchant;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.bank.BankTypeRelation;
import com.oriental.manage.pojo.redis.Response;

import java.util.List;


/**
 * �������ͺ����д���Ĺ�ϵ
 *
 * @author �ƾ�
 *         <p>
 *         2013-9-8 ����4:34:53
 */
public interface IBankTypeRelationService {
    /**
     * �õ����еĹ�ϵ
     *
     * @return
     * @throws Exception
     */
    public List<BankTypeRelation> getAll() throws Exception;

    void queryPage(Pagination<BankTypeRelation> pagination, BankTypeRelation bankTypeRelation);

    /**
     * @Title: getAllBankCode
     * @Description: (获取所有的银行代码)
     * @return
     */
    List <String> getAllBankCode();

    void addBankTypeRelation(ResponseDTO<String> responseDTO, BankTypeRelation bankTypeRelation);

    void deleteBankTypeRelation(ResponseDTO<String> responseDTO, BankTypeRelation bankTypeRelation);

    int checkBankType(BankTypeRelation bankTypeRelation);

}
