package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.base.RoleJobResponsibilityMapper;
import com.oriental.manage.pojo.base.RoleJobResponsibility;
import com.oriental.manage.service.base.IRoleJobResponsibilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by jinxin on 2017/2/20.
 */
@Service
@Slf4j
public class RoleJobResponsibilityServiceImpl implements IRoleJobResponsibilityService {

    @Autowired
    RoleJobResponsibilityMapper roleJobResponsibilityMapper;

    @Override
    public void allocateResource(RoleJobResponsibility roleJobResponsibility, String[] ids, ResponseDTO<String> responseDTO) {
        try{
            roleJobResponsibilityMapper.delete(roleJobResponsibility);
            for (String jobId:
                 ids) {
                roleJobResponsibility.setId(UUID.randomUUID().toString());
                roleJobResponsibility.setJobId(jobId);
                roleJobResponsibilityMapper.insert(roleJobResponsibility);
            }
            responseDTO.setSuccess(true);
        }catch (Exception e){
            log.error("分配岗位拟职责失败",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("分配岗位拟职责失败");
        }
    }

    @Override
    public List<RoleJobResponsibility> queryChecked(RoleJobResponsibility roleJobResponsibility) {
       return roleJobResponsibilityMapper.selectRoleJobResponsibility(roleJobResponsibility);
    }
}
