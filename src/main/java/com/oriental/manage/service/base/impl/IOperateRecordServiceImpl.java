package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.dao.base.OperateRecordMapper;
import com.oriental.manage.pojo.base.OperateRecord;
import com.oriental.manage.service.base.IOperateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luoxin on 2016/10/20.
 */
@Service
public class IOperateRecordServiceImpl implements IOperateRecordService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OperateRecordMapper operateRecordMapper;

    @Override
    public void selectByOperateRecord(Pagination<OperateRecord> pagination, OperateRecord record) {
        List<OperateRecord> list = operateRecordMapper.selectByOperateRecord(record);
        if (list != null && list.size() > 0) {

            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());

        }

    }
}