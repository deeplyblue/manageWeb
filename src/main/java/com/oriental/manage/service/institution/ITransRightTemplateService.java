package com.oriental.manage.service.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.institution.TransRightTemplate;

/**
 * Created by wangjun on 2016/5/10.
 */
public interface ITransRightTemplateService {

   void queryPage ( Pagination<TransRightTemplate> pagination,TransRightTemplate transRightTemplate);
}
