package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.dao.institution.OrgBankMapper;

import com.oriental.manage.pojo.institution.OrgBank;
import com.oriental.manage.service.institution.IOrBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/9.
 */
@Service
 public class OrgBankImpl implements IOrBankService{
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OrgBankMapper orgBankMapper;

    @Override
    public void queryPage (Pagination<OrgBank> pagination,OrgBank orgBank){
        List<OrgBank> list=orgBankMapper.getAll(orgBank);
             if(list!=null&& list.size()>0){
                 pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }
    }

    @Override
    public void queryPageByStrategy(Pagination<OrgBank> pagination, OrgBank orgBank) {
        List<OrgBank> list=orgBankMapper.queryOrgBankInfoForPayChanExtendPage(orgBank);
        if(list!=null&& list.size()>0){
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }

    }

    ;

}
