package com.oriental.manage.dao.base;

import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.pojo.base.JobResponsibility;

import java.util.List;

/**
 * Created by wangjun on 2017/1/17.
 */
public interface JobResponsibilityMapper {

    /**
     * 查询岗位职责
     * @param jobResponsibility
     * @return
     */
    List<JobResponsibility> queryPage(JobResponsibility jobResponsibility);

    int insert(JobResponsibility jobResponsibility);

    int update(JobResponsibility jobResponsibility);

    int delete(JobResponsibility jobResponsibility);

    List<JobResponsibility> queryByName(JobResponsibility jobResponsibility);
}
