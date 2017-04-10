package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.dao.institution.TransRightTemplateMapper;
import com.oriental.manage.pojo.institution.TransRightTemplate;
import com.oriental.manage.service.institution.ITransRightTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/10.
 */
@Service
public class TransRightTemplateImpl implements ITransRightTemplateService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TransRightTemplateMapper transRightTemplateMapper;

    @Override
    public void queryPage(Pagination<TransRightTemplate> pagination, TransRightTemplate transRightTemplate) {
        List<TransRightTemplate> list = transRightTemplateMapper.queryPage(transRightTemplate);


        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());

            pagination.setList(list);


        }
    }
}
