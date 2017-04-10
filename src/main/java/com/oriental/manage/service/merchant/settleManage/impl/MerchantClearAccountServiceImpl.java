package com.oriental.manage.service.merchant.settleManage.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.merchant.settleManage.MerchantClearAccountMapper;
import com.oriental.manage.pojo.merchant.settleManage.ClearAccount;
import com.oriental.manage.service.merchant.settleManage.IMerchantClearAccountService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/31
 * Time: 16:37
 * Desc：商户账户配置
 */
@Service
@Slf4j
public class MerchantClearAccountServiceImpl implements IMerchantClearAccountService {

    @Autowired
    private MerchantClearAccountMapper merchantClearAccountMapper;

    @Override
    public void searchMerchantClearAccount(Pagination<ClearAccount> pagination, ClearAccount clearAccount) {
        List<ClearAccount> clearAccountList = merchantClearAccountMapper.queryClearAccount(clearAccount);
        if(clearAccountList!=null && clearAccountList.size() > 0){
            pagination.setRowCount(clearAccountList.get(0).getRowCount());
            pagination.setList(clearAccountList);
        }
    }

    @Override
    public void addMerchantClearAccount(ClearAccount clearAccount, ResponseDTO<String> responseDTO) {
        clearAccount.setId(DateUtil.getCurrent().concat(RandomMath.getNum(8)));
        clearAccount.setCreator(SessionUtils.getUserName());
        clearAccount.setModifier(SessionUtils.getUserName());
        int count = merchantClearAccountMapper.addClearAccount(clearAccount);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateMerchantClearAccount(ClearAccount clearAccount, ResponseDTO<String> responseDTO) {
        int count = merchantClearAccountMapper.updateClearAccount(clearAccount);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }
}
