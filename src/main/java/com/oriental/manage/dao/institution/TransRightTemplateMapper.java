package com.oriental.manage.dao.institution;

import com.oriental.manage.pojo.institution.TransRightTemplate;

import java.util.List;

/**
 * Created by wangjun on 2016/5/10.
 */
public interface TransRightTemplateMapper {

    int checkConfig (TransRightTemplate transRightTemplate);

    int searchByKey (TransRightTemplate transRightTemplate);

    List<TransRightTemplate> queryPage(TransRightTemplate transRightTemplate);

    int queryCount(TransRightTemplate transRightTemplate);

    List<TransRightTemplate> getAllTemplate(TransRightTemplate transRightTemplate);

    int insertTransRightTemplate(TransRightTemplate transRightTemplate);

    int deleteByTemplateId (TransRightTemplate transRightTemplate);

    int getAllTemplateByUserFlag (TransRightTemplate transRightTemplate);
}
