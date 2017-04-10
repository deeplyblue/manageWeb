package com.oriental.manage.service.base;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.JobResponsibility;

import java.util.List;

/**
 * Created by wangjun on 2017/1/17.
 */
public interface IJobResponsibilityService {

   void queryPage(Pagination<JobResponsibility> pagination, JobResponsibility jobResponsibility);

    void addJobResponsibility(ResponseDTO<String> responseDTO, JobResponsibility jobResponsibility);

    void updateJobResponsibility(ResponseDTO<String> responseDTO, JobResponsibility jobResponsibility);

    boolean deleteJobResponsibility( JobResponsibility jobResponsibility);

    List<JobResponsibility> queryByName(JobResponsibility jobResponsibility);


}
