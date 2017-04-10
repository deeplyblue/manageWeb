package com.oriental.manage.service.business.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.dao.business.MchntBankMapper;
import com.oriental.manage.pojo.business.MchntBank;
import com.oriental.manage.service.business.IMchntBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/24.
 */
@Service
public class MchntBankImpl implements IMchntBankService {

    @Autowired
    private MchntBankMapper mchntBankMapper;

    @Override
    public void queryPage (Pagination<MchntBank> pagination, MchntBank mchntBank){
        List<MchntBank> list=mchntBankMapper.queryPage(mchntBank);
        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }
    }
}
