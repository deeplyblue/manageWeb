package com.oriental.manage.service.base;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.base.OperateRecord;

import java.util.List;

/**
 * Created by luoxin on 2016/10/20.
 */
public interface IOperateRecordService {
    void selectByOperateRecord(Pagination<OperateRecord> pagination, OperateRecord record);
}
