package com.oriental.manage.controller.system;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.JobResponsibility;
import com.oriental.manage.service.base.IJobResponsibilityService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 2017/1/17.
 */
@Slf4j
@Controller
@RequestMapping("system/jobResponsibility")
public class JobResponsibilityController {

    @Autowired
    private IJobResponsibilityService jobResponsibilityService;
    @RequestMapping("init")
    public String init(){ return "system/searchJobResponsibility";}
    @RequestMapping("toAdd")
    public String toAdd(){ return "system/addJobResponsibility";}
    @RequestMapping("toUpdate")
    public String toUpdate(){ return  "system/updateJobResponsibility";}

    @OperateLogger(content = "查询岗位职责", operationType = OperateLogger.OperationType.R,tables = "t_job_responsibility")
    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<JobResponsibility>> queryPage(Pagination<JobResponsibility> pagination, JobResponsibility jobResponsibility){
        ResponseDTO<Pagination<JobResponsibility>> responseDTO = new ResponseDTO<Pagination<JobResponsibility>>();

    try {
        jobResponsibilityService.queryPage(pagination,jobResponsibility);
        responseDTO.setSuccess(true);
        responseDTO.setObject(pagination);


    }catch (Exception e){
        log.error("查询失败", e);
        responseDTO.setSuccess(false);
        responseDTO.setMsg(e.getMessage());
    }
        return responseDTO;
    }

    @OperateLogger(content = "新增岗位职责",operationType = OperateLogger.OperationType.C,tables = "t_job_responsibility" )
    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(@RequestBody JobResponsibility[] jobResponsibilities){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        String name=jobResponsibilities[0].getJobName();
        String priority=jobResponsibilities[0].getPriority();
        try{
            for(JobResponsibility temp : jobResponsibilities){
                temp.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
                temp.setCreator(SessionUtils.getUserName());
                temp.setCreateTime(new Date());
                temp.setJobName(name);
                temp.setPriority(priority);

                jobResponsibilityService.addJobResponsibility(responseDTO, temp);
            }
        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @OperateLogger(content = "修改岗位职责",operationType = OperateLogger.OperationType.U,tables = "t_job_responsibility" )
    @RequestMapping("update")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public ResponseDTO<String> update(@RequestBody JobResponsibility[] jobResponsibilities){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        String name=jobResponsibilities[0].getJobName();
        String priority=jobResponsibilities[0].getPriority();
        Date openDate=new Date();
        String creatorJob="管理员";
        try {
            boolean temp=false;
         List<JobResponsibility> list=  jobResponsibilityService.queryByName(jobResponsibilities[0]);
            if(list!=null &&list.size()>0){
                openDate=list.get(0).getCreateTime();
                creatorJob=list.get(0).getCreator();
              temp= jobResponsibilityService.deleteJobResponsibility(jobResponsibilities[0]);
            }
            if(temp){
                for(JobResponsibility job : jobResponsibilities){
                    job.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
                    job.setModifier(SessionUtils.getUserName());
                    job.setLastUpdTime(new Date());
                    job.setCreateTime(openDate);
                    job.setCreator(creatorJob);
                    job.setJobName(name);
                    job.setPriority(priority);
                    jobResponsibilityService.addJobResponsibility(responseDTO, job);
                }
            }

        }catch (Exception e){
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("queryByName")
    @ResponseBody
    public List<JobResponsibility> queryByName(JobResponsibility jobResponsibility){
        List<JobResponsibility> list=jobResponsibilityService.queryByName(jobResponsibility);
        return  list;
    }
    @RequestMapping("checkName")
    @ResponseBody
    public boolean checkName(String name,String id){
        JobResponsibility job=new JobResponsibility();
        List<JobResponsibility> list=new ArrayList<>();
        if(name!=null && !name.equals("")){
        job.setJobName(name);
            list=jobResponsibilityService.queryByName(job);
        }
        boolean flag=false;
        if(id==null&& list!=null && list.size()>0){
            if(name.equals(list.get(0).getJobName())){
                return true;
            }
        }else if (id !=null&& list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                if(id.equals(list.get(i).getId())){
                    flag=true;
                }
            }
            if(flag){
                if(name.equals(list.get(0).getJobName())){
                    return false;
                }
            }else{
                return true;
            }
        }
        return false;
    }
}
