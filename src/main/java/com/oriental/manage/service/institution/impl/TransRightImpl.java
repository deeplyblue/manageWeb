package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.dao.institution.TransRightMapper;
import com.oriental.manage.pojo.institution.TransRight;
import com.oriental.manage.service.institution.ITransRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/11.
 */
@Service
public class TransRightImpl implements ITransRightService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TransRightMapper transRightMapper;

   @Override
    public void queryPage (Pagination<TransRight> pagination, TransRight transRight){
        List<TransRight> list= transRightMapper.getAll(transRight);
        if(list!=null && list.size()>0){
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());

        }

    }

    @Override
    public TransRight queryTransRight(TransRight transRight) {
        return transRightMapper.queryOne(transRight);
    }
}
