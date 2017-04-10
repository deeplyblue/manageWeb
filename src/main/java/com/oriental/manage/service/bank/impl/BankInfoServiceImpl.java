package com.oriental.manage.service.bank.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.GenerateIdUtil;
import com.oriental.manage.core.utils.ObjectUtils;
import com.oriental.manage.dao.bank.BankInfoMapper;
import com.oriental.manage.pojo.bank.BankInfo;
import com.oriental.manage.service.bank.IBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bankInfoService")
public class BankInfoServiceImpl implements IBankInfoService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BankInfoMapper bankInfoMapper;

    @Override
    public List<BankInfo> getAllBankInfo() throws Exception {
        return bankInfoMapper.queryAllBankInfo();
    }

    @Override
    public boolean addBankInfo(BankInfo bean) throws Exception {
        String bankCardType = bean.getBankCardType();
        bankCardType = bankCardType == null ? "" : bankCardType.trim();
        if (bean.getId() != null && !"".equals(bean.getId())) {
            bean.setId(bean.getId());
        } else {
            bean.setId(GenerateIdUtil.generateId());
        }
        bean.setBankCardType(bankCardType);
        ObjectUtils.printObjectValue(bean);
        return bankInfoMapper.insertBankInfo(bean) == 1;
    }

    @Override
    public boolean updateBankInfo(BankInfo bean) throws Exception {
        bankInfoMapper.deleteBankInfo(bean);
        return bankInfoMapper.insertBankInfo(bean) == 1;
    }

    @Override
    public List<BankInfo> getOrgBankInfo() throws Exception {
        return bankInfoMapper.getOrgBankInfo();
    }

    /**
     * 获取银行信息
     *
     * @param bankCode
     * @return
     * @throws Exception
     */
    @Override
    public BankInfo getModels(String bankCode) throws Exception {
        BankInfo queryBank = new BankInfo();
        queryBank.setBankCode(bankCode);
        return getByParams(queryBank);
    }

    /**
     * 获取银行信息
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public BankInfo getByParams(BankInfo bean) throws Exception {
        return bankInfoMapper.searchByParams(bean);
    }

    @Override
    public void queryPage(Pagination<BankInfo> pagination, BankInfo bankInfo) {

        List<BankInfo> list = bankInfoMapper.queryPage(bankInfo);
        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }
    }

    @Override
    public void addBnakInfo(ResponseDTO<String> responseDTO, BankInfo bankInfo) {
        if (bankInfoMapper.insertBankInfo(bankInfo) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }

    }

    @Override
    public void updateBankInfo(ResponseDTO<String> responseDTO, BankInfo bankInfo) {
        if (bankInfoMapper.updateBankInfo(bankInfo) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void deleteBankInfo(ResponseDTO<String> responseDTO, BankInfo bankInfo) {
        if (bankInfoMapper.deleteBankInfo(bankInfo) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }

    }
}
