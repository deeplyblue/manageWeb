package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.base.JobResponsibilityMapper;
import com.oriental.manage.pojo.base.JobResponsibility;
import com.oriental.manage.service.base.IJobResponsibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2017/1/17.
 */
@Service
public class JobResponsibilityImpl implements IJobResponsibilityService {
    @Autowired(required = false)
    private JobResponsibilityMapper jobResponsibilityMapper;

    @Override
    public void queryPage(Pagination<JobResponsibility> pagination, JobResponsibility jobResponsibility) {

        List<JobResponsibility> list=jobResponsibilityMapper.queryPage(jobResponsibility);
        if (list != null && list.size() > 0) {
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public void addJobResponsibility(ResponseDTO<String> responseDTO, JobResponsibility jobResponsibility) {
        if(jobResponsibilityMapper.insert(jobResponsibility)>0){
            responseDTO.setSuccess(true);
        }else{

            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateJobResponsibility(ResponseDTO<String> responseDTO, JobResponsibility jobResponsibility) {
        if(jobResponsibilityMapper.update(jobResponsibility)>0){
            responseDTO.setSuccess(true);
        }else{

            responseDTO.setSuccess(false);
        }
    }

    @Override
    public boolean deleteJobResponsibility(JobResponsibility jobResponsibility) {
        if(jobResponsibilityMapper.delete(jobResponsibility)>0){
            return true;
        }else{
        return false;}
    }

    @Override
    public List<JobResponsibility> queryByName(JobResponsibility jobResponsibility) {
        return jobResponsibilityMapper.queryByName(jobResponsibility);
    }
}
